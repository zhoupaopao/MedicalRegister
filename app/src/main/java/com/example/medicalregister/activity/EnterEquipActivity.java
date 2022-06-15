package com.example.medicalregister.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;

import com.example.lib.base.BaseActivity;
import com.example.lib.utils.PhoneInfoUtil;
import com.example.lib.utils.ScanGunKeyEventHelper;
import com.example.lib.utils.SharedPrefUtil;
import com.example.lib.utils.TimeUtil;
import com.example.lib.utils.Tips;
import com.example.lib.utils.Utils;
import com.example.medicalregister.BR;
import com.example.medicalregister.R;
import com.example.medicalregister.bean.MqttBean;
import com.example.medicalregister.databinding.ActivityEnterEquipBinding;
import com.example.medicalregister.update.DownloadManager;
import com.example.medicalregister.utils.MQTTHelper;
import com.example.medicalregister.utils.StringUtil;
import com.example.medicalregister.utils.WordUtils;
import com.example.medicalregister.viewmodel.EnterEquipViewModel;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static com.example.lib.base.ViewManager.finishAllActivity;
import static com.example.lib.utils.Utils.getContext;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * 录入设备页面
 */
public class EnterEquipActivity extends BaseActivity<ActivityEnterEquipBinding, EnterEquipViewModel> implements ScanGunKeyEventHelper.OnScanSuccessListener {
    Handler handler = new Handler();
    private Dialog dialog = null;

    MQTTHelper mqttHelper;
    private Long updateIds = Long.valueOf(0);

    @Override
    protected void initListener() {
        viewModel.update();
//        showAskPop();
        viewDataBinding.tvEnter.setBackgroundResource(R.mipmap.icon_enter_button);
//        viewDataBinding.tvTitle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                startActivity(new Intent(EnterEquipActivity.this, HomeMainActivity.class));
////                finish();
//                //手动休眠
//                PowerManager pm=(PowerManager)getSystemService(Context.POWER_SERVICE);
////                pm.goToSleep(SystemClock.uptimeMillis());
//                try {
//                    pm.getClass().getMethod("goToSleep", new Class[]{long.class}).invoke(pm, SystemClock.uptimeMillis());
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
        viewDataBinding.tvEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(EnterEquipActivity.this.getCurrentFocus()
                                .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                try {
                    PhoneInfoUtil.writeKey(mContext, viewDataBinding.etEquip.getText().toString().trim());
                    viewModel.setDeviceNumber(viewDataBinding.etEquip.getText().toString().trim());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                viewModel.getNowStep().setValue(1);
            }
        });
        viewModel.getNowStep().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 0) {
                    //需要进行卡验证
                    viewDataBinding.tvTitle.setText("录入设备");
                    viewDataBinding.llEnter.setVisibility(View.VISIBLE);
                    viewDataBinding.llScan.setVisibility(View.GONE);
                    viewDataBinding.llWarning.setVisibility(View.GONE);
                } else if (integer == 1) {
                    viewDataBinding.tvTitle.setText("扫描登录");
                    viewDataBinding.llEnter.setVisibility(View.GONE);
                    viewDataBinding.llScan.setVisibility(View.VISIBLE);
                    viewDataBinding.llWarning.setVisibility(View.GONE);
                    initMqtt();
                } else if (integer == 2) {
                    viewDataBinding.tvSourceName.setText(viewModel.getNowDeviceUnit().getDepartmentName());
                    viewDataBinding.tvTitle.setText("扫描登录");
                    viewDataBinding.llEnter.setVisibility(View.GONE);
                    viewDataBinding.llScan.setVisibility(View.GONE);
                    viewDataBinding.llWarning.setVisibility(View.VISIBLE);
                    viewDataBinding.llWarningErrorSource.setVisibility(View.VISIBLE);
                    viewDataBinding.llWarningErrorDState.setVisibility(View.GONE);
                    //但是状态2只能停留2s，且不能扫描
                    handler.postDelayed(runnable, 2000);
                }else if (integer == 3) {
                    viewDataBinding.tvTitle.setText("系统提示");
                    viewDataBinding.llEnter.setVisibility(View.GONE);
                    viewDataBinding.llScan.setVisibility(View.GONE);
                    viewDataBinding.llWarning.setVisibility(View.VISIBLE);
                    viewDataBinding.llWarningErrorSource.setVisibility(View.GONE);
                    viewDataBinding.llWarningErrorDState.setVisibility(View.VISIBLE);
                    if(mqttHelper==null){
                        initMqtt();
                    }else{
                        if(!mqttHelper.getmClient().isConnected()){
                            initMqtt();
                        }
                    }
                }
            }
        });

        viewModel.getMessageEvent().observe(this, (Observer<? super String>) message -> {
            if (message.equals("success")) {
//                Tips.show(message);
//                adapter.notifyDataSetChanged();
                hideLoadingDialog();
            } else if (message.equals("loading")) {
                showLoadingDialog("加载中");
            } else if (message.equals("fail")) {
//                Tips.show(message);
                hideLoadingDialog();
            } else if (message.equals("toHomeMain")) {
                Intent intent = new Intent(this, HomeMainActivity.class);
                startActivity(intent);
                finish();
            } else if (message.equals("showAskPop")) {
                showAskPop();
            }

        });
    }

    private void initMqtt() {
        mqttHelper = new MQTTHelper(this, WordUtils.topicGet+viewModel.getDeviceNumber(), new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
//                setTextInfo("connectionLostException: " + cause.getMessage());
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.i("messageArrived: ", new String(message.getPayload()));
                MqttBean mqttBean = new Gson().fromJson(new String(message.getPayload()), MqttBean.class);
                if (mqttBean.getMethod().equals("medice_waste_Set")) {
                    if (mqttBean.getParam().containsKey("Interval_State")) {
                        //设置发送间隔
                        SharedPrefUtil.putInterval_State(mqttBean.getParam().get("Interval_State"));
                    }
                    if (mqttBean.getParam().containsKey("D_state")) {
                        //设置启用和禁用
                        SharedPrefUtil.putD_state(mqttBean.getParam().get("D_state"));
                        if(SharedPrefUtil.getD_state().equals("0")){
                            //禁用
                            //G关闭所有页面，重新打开这个页面
                            if(isFinishing()){
                                finishAllActivity();
                                Intent intent=new Intent(EnterEquipActivity.this,EnterEquipActivity.class);
                                startActivity(intent);
                            }else{
                                //没有关闭，直接设置
                                viewModel.getNowStep().setValue(3);
                            }


                        }else{
                            //启用
                            viewModel.getNowStep().setValue(1);
                        }
                    }
                            //设备休眠 0，唤醒 1
                    if(mqttBean.getParam().containsKey("D_wakeup")){
                        if(mqttBean.getParam().get("D_wakeup").equals("0")){

                            PowerManager pm=(PowerManager)getSystemService(Context.POWER_SERVICE);
                            if(pm.isScreenOn()){
                                try {
                                    pm.getClass().getMethod("goToSleep", new Class[]{long.class}).invoke(pm, SystemClock.uptimeMillis());
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                } catch (NoSuchMethodException e) {
                                    e.printStackTrace();
                                }
                                Log.d("sleep","屏幕状态1="+pm.isScreenOn());
                            }
//                pm.goToSleep(SystemClock.uptimeMillis());

                        }else if(mqttBean.getParam().get("D_wakeup").equals("1")){
                            //唤醒
                            PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
//                           @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "TEST");
//                            mWakeLock.acquire();
                            if(!pm.isScreenOn()){
                                handler.postDelayed(new Runnable(){

                                    public void run(){

                                        Log.d("sleep", "sleep--");

                                        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "TAG");

                                        wakeLock.acquire();

                                        Log.d("sleep","屏幕状态2="+pm.isScreenOn());

                                        wakeLock.release();

                                    }

                                }, 1*1000);
                            }
                        }
                    }
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
//                setTextInfo("deliveryComplete");
                Log.i("TAG", "deliveryComplete: ");
            }
        });
        mqttHelper.doConnect();
       if(!WordUtils.isRunnable){
           WordUtils.isRunnable=true;
           handler.postDelayed(runnableTopic, 1000);
       }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //mqtt心跳
    Runnable runnableTopic = new Runnable() {
        @Override
        public void run() {
            //获取时间
            Log.i("TAG", "topic心跳");
            MqttBean mqttBean = new MqttBean();
            mqttBean.setID(updateIds++);
            mqttBean.setMethod("medice_waste_Up");
            mqttBean.setDeviceCode(viewModel.getDeviceNumber());
            mqttBean.setProductKey("LuRuZhongDuan");
            Map<String, String> map = new HashMap<String, String>();
            map.put("Print_state", SharedPrefUtil.getPrint_state());
            map.put("BlueWeight_State", SharedPrefUtil.getBlueWeight_State());
            map.put("Interval_State", SharedPrefUtil.getInterval_State());
            map.put("D_state", SharedPrefUtil.getD_state());
            map.put("TIME", TimeUtil.getNowTimeNYRSFM());
            PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
            if(pm.isScreenOn()){
                map.put("D_wakeup","1");
            }else{
                //0是休眠
                map.put("D_wakeup","0");
            }

            map.put("Version", "V" + Utils.getAppVersionName(getContext()));
            mqttBean.setParam(map);
            mqttHelper.publish(WordUtils.topicUpdate, new Gson().toJson(mqttBean));
            handler.postDelayed(this, Long.parseLong(SharedPrefUtil.getInterval_State()) * 1000);
        }
    };
    Runnable runnableInstall = new Runnable() {
        @Override
        public void run() {
            WordUtils.isDownload = false;
            installApp(new File(fileUrl));
        }
    };
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.i("run: ", "run: ");
            viewModel.getNowStep().setValue(1);
        }
    };

    @Override
    protected void initData() {
        try {

            String deviceId = PhoneInfoUtil.readKey(this);
            viewModel.setDeviceNumber(deviceId);
            Log.i("DeviceId", deviceId);
            if (StringUtil.isEmpty(deviceId)) {
                viewModel.getNowStep().setValue(0);
            } else {
                viewModel.getNowStep().setValue(1);
            }
            if(SharedPrefUtil.getD_state().equals("0")){
                //设备是禁用状态
                viewModel.getNowStep().setValue(3);
            }
//            Tips.show(PhoneInfoUtil.readKey(this));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //回到首页
            return super.onKeyDown(keyCode, event);
        } else if (mScanGunKeyEventHelper.isScanGunEvent(event)) {
            mScanGunKeyEventHelper.analysisKeyEvent(event);
            Log.i("performScanSuccess", event.getKeyCode() + "");
            return true;
        }

//        return false;
        return super.onKeyDown(keyCode, event);
    }

    protected ScanGunKeyEventHelper mScanGunKeyEventHelper = new ScanGunKeyEventHelper(this);

    @Override
    protected EnterEquipViewModel getViewModel() {
        return new EnterEquipViewModel();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_enter_equip;
    }

    @Override
    public void onScanSuccess(String barcode) {
        Log.i("onScanSuccess: ", barcode);
        if (viewModel.getNowStep().getValue() == 1) {
            viewModel.token(barcode);
//            viewModel.getNowStep().setValue(2);

        }


    }

    public void showAskPop() {
        if (WordUtils.isDownload) {
            Tips.show("正在下载中");
            return;
        }
        File file=new File("/storage/emulated/0/Download/dengji.apk");
        if(file.exists()){
            file.delete();
        }
        startUpdate(viewModel.getDownLoadUrl());
//        int layoutId = R.layout.layout_pop_ask_update;   // 布局ID
//        View contentView = LayoutInflater.from(getContext()).inflate(layoutId, null);
//        TextView tv_submit = contentView.findViewById(R.id.tv_submit);
//        tv_submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //执行下载
//                if (WordUtils.isDownload) {
//                    Tips.show("正在下载中");
//                    return;
//                }
//                startUpdate("http://cachefly.cachefly.net/100mb.test", tv_submit);
////                    if (!bean.isMustUpdate()) {
////                        dialog.dismiss();
////                    }
//            }
//        });
//        if (dialog != null) {
//            dialog = null;
//        }
//        dialog = DialogUIUtils.showCustomAlert(this, contentView, Gravity.CENTER, true, true).show();
////            dialog.getWindow().setBackgroundDrawableResource(R.color.color_00FFFFFF);
//        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_white_corner10);
//        WindowManager windowManager = getWindowManager();
//        Display display = windowManager.getDefaultDisplay();
//        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
//        lp.width = (int) (display.getWidth() - 500); //设置宽度
//        lp.height = (int) (display.getHeight() - 200);//设置高度
//        dialog.getWindow().setAttributes(lp);
//        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//            @Override
//            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
//                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
//                    //这边判断,如果是back的按键被点击了   就自己拦截实现掉
//                    if (i == KeyEvent.KEYCODE_BACK) {
//                        //回到首页
//                        return false;//表示处理了
//                    } else if (mScanGunKeyEventHelper.isScanGunEvent(keyEvent)) {
//                        mScanGunKeyEventHelper.analysisKeyEvent(keyEvent);
//                        return true;
//                    }
//                }
//                return false;
//            }
//        });
    }

    NotificationCompat.Builder mBuilder;
    private NotificationManager mNotifyManager;
    private Notification mNotification;
    public static final String PRIMARY_CHANNEL = "default";

    public void startUpdate(String url) {
        mNotifyManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//适配8.0,自行查看8.0的通知，主要是NotificationChannel
            NotificationChannel chan1 = new NotificationChannel(PRIMARY_CHANNEL,
                    "Primary Channel", NotificationManager.IMPORTANCE_DEFAULT);
            chan1.setLightColor(Color.GREEN);
            chan1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            mNotifyManager.createNotificationChannel(chan1);
            mBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL);
        } else {
            mBuilder = new NotificationCompat.Builder(this, null);
        }
        mBuilder.setContentText("医废登记")
//                .setContentTitle(mDownloadCname)
                .setTicker("正在下载")
                .setPriority(Notification.PRIORITY_DEFAULT)
//                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setOnlyAlertOnce(true)
//                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.logonew);
        mBuilder.setProgress(100, 0, false);
        mNotification = mBuilder.build();
        mNotifyManager.notify(100, mNotification);

        DownloadManager downloadManager = DownloadManager.getInstance();
        downloadManager.setProgressListener(new DownloadManager.ProgressListener() {
            @Override
            public void progressChanged(int progress) {
                Log.e("retrofitdownload", "progress = " + progress);
                mBuilder.setContentText("医废登记");
                mBuilder.setProgress(100, progress, false);//更新进度
                mNotification = mBuilder.build();
                mNotifyManager.notify(100, mNotification);
//                if (tvUpdate != null) {
//                    tvUpdate.setText("下载中...");
//                }
            }

            @Override
            public void progressCompleted(String fileAbsolutePath) {
                Log.e("retrofitdownload", "progressCompleted fileAbsolutePath=" + fileAbsolutePath);
                mNotifyManager.cancel(100);
                fileUrl=fileAbsolutePath;
                handler.postDelayed(runnableInstall,5000);
//                installApp(new File(fileAbsolutePath));

//                if (tvUpdate != null) {
//                    tvUpdate.setText("已下载");
//                }
            }

            @Override
            public void progressError(Exception e) {
                mNotifyManager.cancel(100);
                WordUtils.isDownload = false;
                Tips.show("下载失败");
//                if (tvUpdate != null) {
//                    tvUpdate.setText("重新下载");
//                }
            }
        });
        downloadManager.start(url, "/storage/emulated/0/Download", "dengji.apk");
//        if (tvUpdate != null) {
//            tvUpdate.setText("下载中...");
//        }
    }
    private String fileUrl="";

    /**
     * apk下载完成，进行安装
     *
     * @param file
     */
    private void installApp(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
//判断是否是AndroidN以及更高的版本
        String authority = getPackageName() + ".provider";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri contentUri = FileProvider.getUriForFile(this, authority, file);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        startActivity(intent);
    }

}
