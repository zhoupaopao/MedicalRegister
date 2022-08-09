package com.example.medicalregister.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.Observer;

import com.example.lib.base.BaseActivity;
import com.example.lib.utils.ScanGunKeyEventHelper;
import com.example.lib.utils.SharedPrefUtil;
import com.example.medicalregister.AppAplication;
import com.example.medicalregister.BR;
import com.example.medicalregister.R;
import com.example.medicalregister.base.BasePrintActivity;
import com.example.medicalregister.bean.EmployeesBean;
import com.example.medicalregister.databinding.ActivityRegisterScanBinding;
import com.example.medicalregister.utils.BlueWeight;
import com.example.medicalregister.viewmodel.RegisterScanViewModel;

public class RegisterScanActivity extends BasePrintActivity<ActivityRegisterScanBinding, RegisterScanViewModel> implements ScanGunKeyEventHelper.OnScanSuccessListener{
    Handler handler=new Handler();
    //在这边预先请求一次地磅和打印机
    BlueWeight blueWeight = new BlueWeight(this);
    BluetoothAdapter mBluetoothAdapter;
    boolean isFirstIn=true;
    //tijiao试试


    private void requestOpenBluetooth() {
        Intent it = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        //请求打开蓝牙
        it.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        //设置蓝牙可见性
        startActivityForResult(it, REQUESTCODE_BLUETOOTH_ENABLE);
    }

    public static final int REQUESTCODE_BLUETOOTH_ENABLE = 1;
    private void initBlue() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter != null) {
            if (!mBluetoothAdapter.isEnabled()) {
                Toast.makeText(mContext, "请打开蓝牙", Toast.LENGTH_SHORT).show();
                requestOpenBluetooth();
            } else {
                if (BlueWeight.isRead && !BlueWeight.tempWeight.equals("0")) {
                    //连接的地磅
//                    ll_blue.setVisibility(View.GONE);
                } else {
                    try {
                        blueWeight.connectBluetooth(RegisterScanActivity.this, SharedPrefUtil.getString("blue_weight"), mBluetoothAdapter);
                    } catch (Exception e) {
                        //蓝牙异常
//                        Toast.makeText(CollectWeasteAddListActivity.this,"蓝牙地磅连接异常",Toast.LENGTH_SHORT).show();
//                        ll_blue.setVisibility(View.VISIBLE);
                    }
                }

            }
        } else {
            Toast.makeText(mContext, "蓝牙模块初始化失败", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void initListener() {
        SharedPrefUtil.putBoolean("needShowMsg",false);
        //扫描正确后就不重新扫，页面9返回
        viewDataBinding.tvChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterScanActivity.this,ChooseWasteActivity.class).putExtra("departmentBean",viewModel.getNowDepartmentBean()));
                finish();
            }
        });
        viewDataBinding.tvChoose.setBackgroundResource(R.mipmap.icon_enter_button1);
        viewModel.getNowStep().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer==0){
                    //代表扫描前
                    AppAplication.getSound().playShortResource("请扫描护士工牌");
                    viewDataBinding.llWarning.setVisibility(View.GONE);
                    viewDataBinding.llUnScan.setVisibility(View.VISIBLE);
                    viewDataBinding.llHasScanSure.setVisibility(View.GONE);
                }else if(integer==1){
                    //代表扫描错误
                    viewDataBinding.llUnScan.setVisibility(View.GONE);
                    viewDataBinding.llWarning.setVisibility(View.VISIBLE);
                    viewDataBinding.llHasScanSure.setVisibility(View.GONE);
                    EmployeesBean employeesBean=SharedPrefUtil.getObjectT(SharedPrefUtil.EMPLOYEE_Bean);
                    viewDataBinding.tvWarningSource.setText(employeesBean.getDepartment().getName());
                    handler.postDelayed(runnable,2000);
                }else if(integer==2){
                    //扫描正确(0-2)
                    AppAplication.getSound().playShortResource("请确认身份信息");
                    viewDataBinding.llUnScan.setVisibility(View.GONE);
//                    viewDataBinding.llHasScan.setVisibility(View.VISIBLE);
                    viewDataBinding.llHasScanSure.setVisibility(View.VISIBLE);
                    EmployeesBean employeesBean=SharedPrefUtil.getObjectT(SharedPrefUtil.EMPLOYEE_Bean);
                    viewDataBinding.tvSourceName.setText(employeesBean.getDepartment().getName());
                    viewDataBinding.tvRegisterName.setText(employeesBean.getName());
                }
            }
        });
    }
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            Log.i("run: ", "run: ");
            viewModel.getNowStep().setValue(0);
        }
    };

    @Override
    protected void initData() {

    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    protected void onResume() {
        super.onResume();
//        if(isFirstIn){
//            initBlue();
//        }

    }

    @Override
    protected void onStop() {
        super.onStop();
//        if(isFirstIn){
//            Log.i("isFirstIn", "onStop: ");
//            blueWeight.DestoryThread();
//            isFirstIn=false;
//        }

    }

    @Override
    protected RegisterScanViewModel getViewModel() {
        return new RegisterScanViewModel();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register_scan;
    }
    protected ScanGunKeyEventHelper mScanGunKeyEventHelper = new ScanGunKeyEventHelper(this);
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

    @Override
    public void onScanSuccess(String barcode) {
        //扫描到了值
        if(viewModel.getNowStep().getValue()==0) {
            viewModel.checkLabel(barcode);
//            viewModel.getNowStep().setValue(2);
        }
    }
}
