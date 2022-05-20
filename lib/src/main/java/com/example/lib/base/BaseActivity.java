package com.example.lib.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.example.lib.R;
import com.example.lib.bean.TokenBean;
import com.gyf.immersionbar.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

//import com.arch.demo.core.R;
//import com.arch.demo.core.loadsir.EmptyCallback;
//import com.arch.demo.core.loadsir.ErrorCallback;
//import com.arch.demo.core.loadsir.LoadingCallback;
//import com.arch.demo.core.utils.AppManager;
//import com.arch.demo.core.viewmodel.IMvvmBaseViewModel;
//import com.kingja.loadsir.callback.Callback;
//import com.kingja.loadsir.core.LoadService;
//import com.kingja.loadsir.core.LoadSir;


public abstract class BaseActivity<V extends ViewDataBinding, VM extends MvvmBaseViewModel> extends AppCompatActivity implements IBaseView  {
    protected VM viewModel;
    protected V viewDataBinding;
    private ProgressDialog mProgressDialog;
    protected Context mContext;
    private static final int REQUEST_CODE_SCAN = 0x0000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.bg_00519D).statusBarDarkFont(false, 0.2f).init();
        initParameters();
        initViewModel();
        performDataBinding();
        initInternalObserver();

        mContext = this;
        initListener();
        initData();
        ViewManager.getInstance().addActivity(this);
    }
    /***
     *   初始化参数
     */
    protected void initParameters() {

    }
     /** 初始化数据 */

    protected abstract void initListener();
    protected abstract void initData();
     private void initViewModel() {
        viewModel = getViewModel();
        if(viewModel != null) {
            viewModel.attachUI(this);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewModel != null && viewModel.isUIAttached()) {
            viewModel.detachUI();
        }
        if(mProgressDialog!=null&&mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        ViewManager.getInstance().finishActivity(this);
    }




    @Override
    public void showLoadingDialog(String hint) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        mProgressDialog.setMessage(hint);
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
    }
    @Override
    public void hideLoadingDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }




    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    protected abstract void onRetryBtnClick();

    protected abstract VM getViewModel();

    public abstract int getBindingVariable();

    public abstract
    @LayoutRes
    int getLayoutId();

    private void performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.viewModel = viewModel == null ? getViewModel() : viewModel;
        if(getBindingVariable() > 0) {
            viewDataBinding.setVariable(getBindingVariable(), viewModel);
        }
        viewDataBinding.executePendingBindings();
        viewDataBinding.setLifecycleOwner(this);
    }


    protected  void initInternalObserver(){
        viewModel.loadingEvent.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean o) {
                if(o){
                    showLoadingDialog("加载中");
                }else{
                    hideLoadingDialog();
                }
            }
        });
    }

}
