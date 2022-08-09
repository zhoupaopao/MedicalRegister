package com.example.medicalregister.activity;

import android.content.Intent;

import androidx.lifecycle.Observer;

import com.example.lib.base.BaseActivity;
import com.example.lib.utils.SharedPrefUtil;
import com.example.medicalregister.AppAplication;
import com.example.medicalregister.BR;
import com.example.medicalregister.R;
import com.example.medicalregister.bean.EmployeesBean;
import com.example.medicalregister.databinding.ActivityChooseWasteBinding;
import com.example.medicalregister.viewmodel.ChooseWasteViewModel;

public class ChooseWasteActivity extends BaseActivity<ActivityChooseWasteBinding, ChooseWasteViewModel> {
    @Override
    protected void initListener() {
         AppAplication.getSound().playShortResource("请选择医废类型");
        viewModel.getWasteProduceRecord();
        //添加registerMessageEvent后使用方式,推荐
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
            }else if(message.equals("toWeight")){
                Intent intent=new Intent(ChooseWasteActivity.this, WeightRegisterActivity.class);
                intent.putExtra("collectWasteBean",viewModel.getNowWasteInventoryBean());
                startActivity(intent);
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
    protected ChooseWasteViewModel getViewModel() {
        return new ChooseWasteViewModel();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_choose_waste;
    }
}
