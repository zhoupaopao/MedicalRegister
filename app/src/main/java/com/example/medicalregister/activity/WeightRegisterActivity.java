package com.example.medicalregister.activity;

import static com.example.medicalregister.utils.BlueUtil.MESSAGE_DEVICE_NAME;
import static com.example.medicalregister.utils.BlueUtil.MESSAGE_READ;
import static com.example.medicalregister.utils.BlueUtil.MESSAGE_SEND_WEIGHT;
import static com.example.medicalregister.utils.BlueUtil.MESSAGE_STATE_CHANGE;
import static com.example.medicalregister.utils.BlueUtil.MESSAGE_TOAST;
import static com.example.medicalregister.utils.BlueUtil.MESSAGE_WRITE;
import static com.example.medicalregister.utils.BlueUtil.TOAST;

import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.lifecycle.Observer;

import com.arch.demo.network_api.errorhandler.ExceptionHandle;
import com.arch.demo.network_api.observer.BaseObserver;
import com.example.lib.base.BaseActivity;
import com.example.lib.utils.SharedPrefUtil;
import com.example.lib.utils.Tips;
import com.example.medicalregister.AppAplication;
import com.example.medicalregister.BR;
import com.example.medicalregister.R;
import com.example.medicalregister.base.BasePrintActivity;
import com.example.medicalregister.bean.DeviceUnit;
import com.example.medicalregister.bean.EmployeesBean;
import com.example.medicalregister.bean.LabelBean;
import com.example.medicalregister.bean.WasteInventoryBean;
import com.example.medicalregister.bluetooth.BluetoothChatService;
import com.example.medicalregister.databinding.ActivityWeightRegisterBinding;
import com.example.medicalregister.http.Api;
import com.example.medicalregister.intface.UnDoubleClickListener;
import com.example.medicalregister.serialport.BackService;
import com.example.medicalregister.serialport.LinkStatusListener;
import com.example.medicalregister.utils.BlueUtil;
import com.example.medicalregister.utils.BlueWeight;
import com.example.medicalregister.utils.Tools;
import com.example.medicalregister.viewmodel.WeightRegisterViewModel;
import com.sun.jna.Pointer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeightRegisterActivity extends BasePrintActivity<ActivityWeightRegisterBinding, WeightRegisterViewModel> {
//    BlueWeight blueWeight = new BlueWeight(this);
    Boolean isFirstSound=true;
    Integer weightType=1;//1.无线称重，2.有线称重
    @Override
    protected void initListener() {
        SharedPrefUtil.putBoolean("needShowMsg",true);
        //无线初始化
        if(weightType.equals(1)){
            initBlue();
        }else{
            //有线称
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
            Intent intent = new Intent(this, BackService.class);
            bindService(intent, connection, Context.BIND_AUTO_CREATE);
        }

        AppAplication.getSound().playShortResource("请进行称重");
        viewModel.setNowWasteInventoryBean((WasteInventoryBean) getIntent().getSerializableExtra("collectWasteBean"));
        viewDataBinding.tvRegisterMobile.setText(viewModel.getNowEmployeesBean().getMobile());
        viewDataBinding.tvRegisterName.setText(viewModel.getNowEmployeesBean().getName());
        viewDataBinding.tvSourceName.setText(viewModel.getNowEmployeesBean().getDepartment().getName());
        viewDataBinding.tvWasteName.setText(viewModel.getNowWasteInventoryBean().getWaste().getName());
        viewDataBinding.tvSubmit.setBackgroundResource(R.mipmap.icon_enter_button1);

//        viewDataBinding.tvSubmit.setOnClickListener(new UnDoubleClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
        viewDataBinding.tvSubmit.setOnClickListener(new UnDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                //先获取标签，然后提交登记，最后打印
                viewModel.achieveLabel(viewDataBinding.tvWeight.getText().toString().trim());
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
            } else if (message.equals("toPrint")) {
                //执行打印
                showLoadingDialog("打印中");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            TestFunction fun = new TestFunction();
                            fun.labelBean = viewModel.getPrintLabelBean();
                            fun.ctx = WeightRegisterActivity.this;
                            Method m = TestFunction.class.getDeclaredMethod("Test_Label_DrawLine", Pointer.class);
//                            Method m = TestFunction.class.getDeclaredMethod("Test_Label_DrawText", Pointer.class);

                            m.invoke(fun, h);
                        } catch (Throwable tr) {
                            tr.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                RefreshUI();
                                finish();
                            }
                        });
                    }
                }).start();

            }

        });
    }

    BackService backService;
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
//            myService = null;
//            Tips.show("Service连接失败");
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
//            myService = ((MyService.MyBinder) service).getService();
//            Tips.show("Service连接成功");
            Log.i("TAG", "onServiceConnected: ");
//            isBound = true;
            BackService.MyBinder myBinder = (BackService.MyBinder) iBinder;
            backService = myBinder.getService();
            Log.i("DemoLog", "ActivityA onServiceConnected");
            backService.openSerial("/dev/ttyS1", 9600, 8, 1, 0, true, new LinkStatusListener() {
                @Override
                public void success() {
//                    Tips.show("串口连接成功");
                }

                @Override
                public void fail() {
                    Tips.show("串口连接失败");
                }
            });
            // 执行Service内部自己的方法
//            myService.excute();
        }
    };


    @Override
    protected void initData() {
        //重量可以使用暂存终端的方式（读到string后截取字符串，再解析backservice里面的内容）
//        final Timer[] timer = {new Timer()};
//        timer[0].schedule(new TimerTask() {
//            @Override
//            public void run() {
//                String tempWeight = BlueWeight.tempWeight;
//                if(!tempWeight.equals("0")){
//                    if(isFirstSound){
//                        AppAplication.getSound().playShortResource("蓝牙连接成功");
//                        isFirstSound=false;
//                    }
//
//                    SharedPrefUtil.putBlueWeight_State("1");
//                }
//                runOnUiThread(() -> viewDataBinding.tvWeight.setText(tempWeight));
////                Log.i("etweight", tempWeight+"");
//            }
//        }, 500, 1000);
    }

    @Override
    protected void onRetryBtnClick() {

    }

    BluetoothAdapter mBluetoothAdapter;

    private void requestOpenBluetooth() {
        Intent it = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        //请求打开蓝牙
        it.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        //设置蓝牙可见性
        startActivityForResult(it, REQUESTCODE_BLUETOOTH_ENABLE);
    }

    public static final int REQUESTCODE_BLUETOOTH_ENABLE = 1;
    private String mConnectedDeviceName;
    public static final String GET_WEIGHT_MQTT = "05000002010806";
    public static final String GET_DEVICE_NUM = "05000002080F0F";
    private BluetoothChatService mChatService = null;
    private String blueWeight="";
    private void initBlue() {

        mChatService = new BluetoothChatService(mContext, mHandler);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(mContext,"本机没有找到蓝牙硬件或驱动！",Toast.LENGTH_SHORT).show();
            finish();
            return;
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                //直接开启蓝牙
                mBluetoothAdapter.enable();

            }
        }

        connect(SharedPrefUtil.getString("blue_weight"));

//        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//
//        if (mBluetoothAdapter != null) {
//            if (!mBluetoothAdapter.isEnabled()) {
//                Toast.makeText(mContext, "请打开蓝牙", Toast.LENGTH_SHORT).show();
//                requestOpenBluetooth();
//            } else {
//                if (BlueWeight.isRead && !BlueWeight.tempWeight.equals("0")) {
//                    //连接的地磅
////                    ll_blue.setVisibility(View.GONE);
//                } else {
//                    try {
//                        blueWeight.connectBluetooth(WeightRegisterActivity.this, SharedPrefUtil.getString("blue_weight"), mBluetoothAdapter);
//                    } catch (Exception e) {
//                        SharedPrefUtil.putBlueWeight_State("0");
//                        AppAplication.getSound().playShortResource("蓝牙连接失败");
//                        //蓝牙异常
////                        Toast.makeText(CollectWeasteAddListActivity.this,"蓝牙地磅连接异常",Toast.LENGTH_SHORT).show();
////                        ll_blue.setVisibility(View.VISIBLE);
//                    }
//                }
//
//            }
//        } else {
//            Toast.makeText(mContext, "蓝牙模块初始化失败", Toast.LENGTH_SHORT).show();
//            SharedPrefUtil.putBlueWeight_State("0");
//        }

    }

    //扫描后返回
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String label) {
        //获取称重重量
        viewDataBinding.tvWeight.setText(label);
    }


    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothChatService.STATE_CONNECTED:
                            if (!isDestroyed()) {
                                Log.i("TAG", "已连接: " + mConnectedDeviceName);
//                                weightCome = "";
//                                BaseApplication.getSound().playShortResource("蓝牙连接成功");
                                WeightRegisterActivity.this.sendMessage(GET_DEVICE_NUM);
                            }
                            break;
                        case BluetoothChatService.STATE_CONNECTING:
                            if (!isDestroyed()) {
                                Log.i("TAG", "正在连接中...");
                            }
                            break;
                        case BluetoothChatService.STATE_LISTEN:
                        case BluetoothChatService.STATE_NONE:
                            if (!isDestroyed()) {
//                                BaseApplication.getSound().playShortResource("蓝牙连接失败");
                                Log.i("TAG", "没有连接");
                            }
                            break;
                    }
                    break;
                case MESSAGE_WRITE:
                    break;
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    String readMessage = Tools.bytesToHexString(readBuf, msg.arg1);
                    if (readMessage != null && readMessage.length() > 7
                            && (('8' == readMessage.charAt(6)) && ('2' == readMessage.charAt(7)))) {
                        dealMqtt(msg);
                    } else {
                        Log.i("TAG", "老地磅不管");
//                        dealWeightOld(msg);
                        String readMessage1 = bytesToHexString(readBuf);
                        Log.e("接收bbbbbb",readMessage1);
                        String str= testRegexp(readMessage1);
                        Log.e("接收AAAAa",str);
                        String weight=hexToAscii(str.toString());
                        Double d=Double.parseDouble(weight);
                        Log.e("TAG", String.valueOf(d));
                        viewDataBinding.tvWeight.setText(String.valueOf(d));
                    }
                    break;
                case MESSAGE_DEVICE_NAME:
                    // 保存已连接设备的名称
                    if (!isDestroyed()) {
                        mConnectedDeviceName = msg.getData().getString(BlueUtil.DEVICE_NAME);
                        Toast.makeText(getApplicationContext(),
                                        "连接到 " + mConnectedDeviceName, Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;
                case MESSAGE_TOAST:
                    if (!isDestroyed()) {
                        Toast.makeText(getApplicationContext(),
                                        msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;
                case MESSAGE_SEND_WEIGHT:
                    if (!isDestroyed()) {
                        if (mChatService.getState() == BluetoothChatService.STATE_CONNECTED) {
                            mHandler.sendEmptyMessageDelayed(MESSAGE_SEND_WEIGHT, 1000);
                        }
                    }
                    break;
            }
        }
    };

    public  String hexToAscii(String hexStr) {
        StringBuffer output = new StringBuffer("");
        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));

        }
        return output.reverse().toString();
    }
    /**
     * 截取字符串
     * */
    public  String testRegexp(String s) {
        if (s != null && !"".equals(s)) {
            String regex = "[0-9a-fA-F]{14}3d";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(s);
            while (matcher.find()) {
                String matcherString = matcher.group();
                return matcherString.substring(0,14);
            }
        }
        return s;
    }
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    //重写发送函数，参数不同。
    private void sendMessage(String message) {
        // 确保已连接
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
//            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }
        // 检测是否有字符串发送
//        String nowText = message.replaceAll(" ", "");
        // 获取 字符串并告诉BluetoothChatService发送
        byte[] send = Tools.hexStr2Bytes(message);
        mChatService.write(send);//回调service
    }


    public void connect(String address) {
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();

            if (!mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.enable();
            }

            if (mBluetoothAdapter.isEnabled()) {
                mChatService.connect(address);
            }
        }
    }
    private void dealMqtt(Message message) {
        //目前就读取重量和设备编号
        try {
            byte[] readBuf = (byte[]) message.obj;

            String readMessage = " " + Tools.bytesToHexString(readBuf, message.arg1);

            if (('8' == readMessage.charAt(7)) && ('2' == readMessage.charAt(8)) && ('0' == readMessage.charAt(9)) && ('8' == readMessage.charAt(10))) {
                String newMessage = readMessage.substring(11);

                String deviceNum = "";
                if (newMessage.indexOf("00") > 0) {
                    if (newMessage.indexOf("00") % 2 == 0) {
                        deviceNum = Tools.hex2String(newMessage.substring(0, newMessage.indexOf("00")));
                    } else {
                        deviceNum = Tools.hex2String(newMessage.substring(0, newMessage.indexOf("00") + 1));
                    }
                } else {
                    deviceNum = Tools.hex2String(newMessage.substring(0, newMessage.length() - 4));
                }
                // TODO: 2022/6/23 暂时注释
//                setNetRequestDeviceNum(deviceNum);

                sendMessage(GET_WEIGHT_MQTT);


            } else {
                try {
                    String newMessage = readMessage.substring(11);
                    //取小数点
                    String substring = newMessage.substring(0, 2);
                    byte[] bytesPoint = Tools.hexString2Bytes(substring);
                    int point = bytesPoint[0] & 0xC0;
                    point = point >> 6;
                    Log.d("hwhw", "point==" + point);

//            String pWeight = newMessage.substring(8, 16); //皮重
                    String jWeight = newMessage.substring(16, 24);//净重
//            String mWeight = newMessage.substring(24, 32); //毛重
                    Log.i("dealMqtt:jWeight ", jWeight);
                    byte[] bytes = Tools.hexString2Bytes(jWeight);

                    int sum = 0;
                    for (int i = 0; i < bytes.length; i++) {
                        int copy = bytes[i] & 0xFF;
                        sum += copy << (8 * i);
                    }

                    String weight = String.valueOf(sum);
                    Log.i("dealMqtt:weight ", weight);
                    setWeightMqtt(Tools.getSmallWeight(weight, point));
//                    if (point == 0) {
//                        setWeightMqtt(weight);
//                    } else {
//                        String substring1 = weight.substring(0, weight.length() - point);
//                        String substring2 = weight.substring(weight.length() - point);
//                        setWeightMqtt(substring1 + "." + substring2);
//                    }

                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //发送获取重量的指令
                            sendMessage(GET_WEIGHT_MQTT);
                        }
                    }, 600);

                } catch (Exception e) {
                    e.printStackTrace();
                    setWeightMqtt("");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    @Override
//    public void foundDev(BluetoothDevice dev) {
//        add(dev);
//    }

    private void setWeightMqtt(String weight) {
        if (blueWeight != null) {
            if (weight!=null&&(weight.indexOf('.')==0||weight.contains("-."))){
                weight=weight.replace(".","0.");
            }
            viewDataBinding.tvWeight.setText(weight);
        }
    }

    @Override
    protected WeightRegisterViewModel getViewModel() {
        return new WeightRegisterViewModel();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_weight_register;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mChatService!=null){
            mChatService.stop();
        }
        if(weightType.equals(2)){
            unbindService(connection);
        }
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        blueWeight.DestoryThread();
    }
}
