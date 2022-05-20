package com.example.medicalregister.activity;

import android.content.Intent;
import android.view.View;

import com.example.lib.base.BaseActivity;
import com.example.medicalregister.BR;
import com.example.medicalregister.R;
import com.example.medicalregister.databinding.ActivitySettingBinding;
import com.example.medicalregister.viewmodel.SettingViewModel;

public class SettingActivity extends BaseActivity<ActivitySettingBinding, SettingViewModel> {
    @Override
    protected void initListener() {
        viewDataBinding.llItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //进入蓝牙列表页面
                startActivity(new Intent(SettingActivity.this,BluetoothListActivity.class));
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    protected SettingViewModel getViewModel() {
        return new SettingViewModel();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }
}
