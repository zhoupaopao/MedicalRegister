package com.example.medicalregister.base;

import androidx.databinding.ViewDataBinding;

import com.example.lib.base.BaseActivity;
import com.example.lib.base.MvvmBaseViewModel;

public class BasePActivity<V extends ViewDataBinding, VM extends MvvmBaseViewModel>extends BaseActivity {
    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    protected MvvmBaseViewModel getViewModel() {
        return null;
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return 0;
    }
}
