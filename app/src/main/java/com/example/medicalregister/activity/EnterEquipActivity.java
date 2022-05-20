package com.example.medicalregister.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.lifecycle.Observer;

import com.dou361.dialogui.DialogUIUtils;
import com.example.lib.base.BaseActivity;
import com.example.lib.utils.DeviceIdUtils;
import com.example.lib.utils.PhoneInfoUtil;
import com.example.lib.utils.ScanGunKeyEventHelper;
import com.example.lib.utils.Tips;
import com.example.medicalregister.BR;
import com.example.medicalregister.R;
import com.example.medicalregister.databinding.ActivityEnterEquipBinding;
import com.example.medicalregister.utils.StringUtil;
import com.example.medicalregister.viewmodel.EnterEquipViewModel;

import java.io.IOException;

import static com.example.lib.utils.Utils.getContext;

/**
 * 录入设备页面
 */
public class EnterEquipActivity extends BaseActivity<ActivityEnterEquipBinding, EnterEquipViewModel>implements  ScanGunKeyEventHelper.OnScanSuccessListener {
    Handler handler=new Handler();
    private Dialog dialog = null;

    @Override
    protected void initListener() {
//        viewModel.update();
//        showAskPop();
//        int layoutId = R.layout.layout_pop_ask_update;   // 布局ID
//        View contentView = LayoutInflater.from(getContext()).inflate(layoutId, null);
//        DialogUIUtils.showCustomAlert(this,contentView).show();
        viewDataBinding.tvEnter.setBackgroundResource(R.mipmap.icon_enter_button);
        viewDataBinding.tvEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(EnterEquipActivity.this.getCurrentFocus()
                                .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                try {
                    PhoneInfoUtil.writeKey(mContext,viewDataBinding.etEquip.getText().toString().trim());
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
                if(integer==0){
                    //需要进行卡验证
                    viewDataBinding.tvTitle.setText("录入设备");
                    viewDataBinding.llEnter.setVisibility(View.VISIBLE);
                    viewDataBinding.llScan.setVisibility(View.GONE);
                    viewDataBinding.llWarning.setVisibility(View.GONE);
                }else if(integer==1){
                    viewDataBinding.tvTitle.setText("扫描登录");
                    viewDataBinding.llEnter.setVisibility(View.GONE);
                    viewDataBinding.llScan.setVisibility(View.VISIBLE);
                    viewDataBinding.llWarning.setVisibility(View.GONE);
                }else if(integer==2){
                    viewDataBinding.tvSourceName.setText(viewModel.getNowDeviceUnit().getDepartmentName());
                    viewDataBinding.tvTitle.setText("扫描登录");
                    viewDataBinding.llEnter.setVisibility(View.GONE);
                    viewDataBinding.llScan.setVisibility(View.GONE);
                    viewDataBinding.llWarning.setVisibility(View.VISIBLE);
                    //但是状态2只能停留2s，且不能扫描
                    handler.postDelayed(runnable,2000);

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
            }else if(message.equals("toHomeMain")){
                            Intent intent=new Intent(this,HomeMainActivity.class);
            startActivity(intent);
            finish();
            }else if(message.equals("showAskPop")){
                showAskPop();
            }

        });
    }

    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            Log.i("run: ", "run: ");
          viewModel.getNowStep().setValue(1);
        }
    };

    @Override
    protected void initData() {
        try {

            String deviceId=PhoneInfoUtil.readKey(this);
            viewModel.setDeviceNumber(deviceId);
            Log.i("DeviceId", deviceId);
            if(StringUtil.isEmpty(deviceId)){
                viewModel.getNowStep().setValue(0);
            }else{
                viewModel.getNowStep().setValue(1);
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
            Log.i("performScanSuccess", event.getKeyCode()+"");
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
        if(viewModel.getNowStep().getValue()==1){
            viewModel.token(barcode);
//            viewModel.getNowStep().setValue(2);

        }

    }
    public void showAskPop() {
//        if (!(dialog != null && dialog.isShowing())) {
            //dialog有内容，同时正在展示
            int layoutId = R.layout.layout_pop_ask_update;   // 布局ID
            View contentView = LayoutInflater.from(getContext()).inflate(layoutId, null);
            TextView tv_submit=contentView.findViewById(R.id.tv_submit);
            tv_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            if (dialog != null) {
                dialog = null;
            }
             dialog = DialogUIUtils.showCustomAlert(this, contentView, Gravity.CENTER, true, true).show();
            dialog.getWindow().setBackgroundDrawableResource(R.color.color_00FFFFFF);
//            WindowManager windowManager = getWindowManager();
//            Display display = windowManager.getDefaultDisplay();
//            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
//            lp.width = (int) (display.getWidth() - 500); //设置宽度
//            lp.height = (int) (display.getHeight() - 200);//设置高度
//            dialog.getWindow().setAttributes(lp);
//            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//                @Override
//                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
//                    if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
//                        //这边判断,如果是back的按键被点击了   就自己拦截实现掉
//                        if (i == KeyEvent.KEYCODE_BACK) {
//                            //回到首页
//                            return false;//表示处理了
//                        } else if (mScanGunKeyEventHelper.isScanGunEvent(keyEvent)) {
//                            mScanGunKeyEventHelper.analysisKeyEvent(keyEvent);
//                            return true;
//                        }
//                    }
//                    return false;
//                }
//            });
        }

}
