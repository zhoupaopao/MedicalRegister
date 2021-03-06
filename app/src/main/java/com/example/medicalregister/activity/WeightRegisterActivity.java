package com.example.medicalregister.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.lifecycle.Observer;

import com.arch.demo.network_api.errorhandler.ExceptionHandle;
import com.arch.demo.network_api.observer.BaseObserver;
import com.example.lib.base.BaseActivity;
import com.example.lib.utils.SharedPrefUtil;
import com.example.medicalregister.BR;
import com.example.medicalregister.R;
import com.example.medicalregister.base.BasePrintActivity;
import com.example.medicalregister.bean.DeviceUnit;
import com.example.medicalregister.bean.EmployeesBean;
import com.example.medicalregister.bean.LabelBean;
import com.example.medicalregister.bean.WasteInventoryBean;
import com.example.medicalregister.databinding.ActivityWeightRegisterBinding;
import com.example.medicalregister.http.Api;
import com.example.medicalregister.utils.BlueWeight;
import com.example.medicalregister.viewmodel.WeightRegisterViewModel;
import com.sun.jna.Pointer;

import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

public class WeightRegisterActivity extends BasePrintActivity<ActivityWeightRegisterBinding, WeightRegisterViewModel> {
    BlueWeight blueWeight = new BlueWeight(this);

    @Override
    protected void initListener() {
        SharedPrefUtil.putBoolean("needShowMsg",true);
        viewModel.setNowWasteInventoryBean((WasteInventoryBean) getIntent().getSerializableExtra("collectWasteBean"));
        viewDataBinding.tvRegisterMobile.setText(viewModel.getNowEmployeesBean().getMobile());
        viewDataBinding.tvRegisterName.setText(viewModel.getNowEmployeesBean().getName());
        viewDataBinding.tvSourceName.setText(viewModel.getNowEmployeesBean().getDepartment().getName());
        viewDataBinding.tvWasteName.setText(viewModel.getNowWasteInventoryBean().getWaste().getName());
        viewDataBinding.tvSubmit.setBackgroundResource(R.mipmap.icon_enter_button1);

        viewDataBinding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //???????????????????????????????????????????????????
                viewModel.achieveLabel(viewDataBinding.tvWeight.getText().toString().trim());
            }
        });
        viewModel.getMessageEvent().observe(this, (Observer<? super String>) message -> {
            if (message.equals("success")) {
//                Tips.show(message);
//                adapter.notifyDataSetChanged();
                hideLoadingDialog();
            } else if (message.equals("loading")) {
                showLoadingDialog("?????????");
            } else if (message.equals("fail")) {
//                Tips.show(message);
                hideLoadingDialog();
            } else if (message.equals("toPrint")) {
                //????????????
                showLoadingDialog("?????????");
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


    @Override
    protected void initData() {
        final Timer[] timer = {new Timer()};
        timer[0].schedule(new TimerTask() {
            @Override
            public void run() {
                String tempWeight = BlueWeight.tempWeight;
                runOnUiThread(() -> viewDataBinding.tvWeight.setText(tempWeight));
//                Log.i("etweight", tempWeight+"");
            }
        }, 500, 1000);
    }

    @Override
    protected void onRetryBtnClick() {

    }

    BluetoothAdapter mBluetoothAdapter;

    private void requestOpenBluetooth() {
        Intent it = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        //??????????????????
        it.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        //?????????????????????
        startActivityForResult(it, REQUESTCODE_BLUETOOTH_ENABLE);
    }

    public static final int REQUESTCODE_BLUETOOTH_ENABLE = 1;

    private void initBlue() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter != null) {
            if (!mBluetoothAdapter.isEnabled()) {
                Toast.makeText(mContext, "???????????????", Toast.LENGTH_SHORT).show();
                requestOpenBluetooth();
            } else {
                if (BlueWeight.isRead && !BlueWeight.tempWeight.equals("0")) {
                    //???????????????
//                    ll_blue.setVisibility(View.GONE);
                } else {
                    try {
                        blueWeight.connectBluetooth(WeightRegisterActivity.this, SharedPrefUtil.getString("blue_weight"), mBluetoothAdapter);
                    } catch (Exception e) {
                        //????????????
//                        Toast.makeText(CollectWeasteAddListActivity.this,"????????????????????????",Toast.LENGTH_SHORT).show();
//                        ll_blue.setVisibility(View.VISIBLE);
                    }
                }

            }
        } else {
            Toast.makeText(mContext, "???????????????????????????", Toast.LENGTH_SHORT).show();
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
        initBlue();
    }

    @Override
    protected void onStop() {
        super.onStop();
        blueWeight.DestoryThread();
    }
}
