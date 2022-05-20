package com.example.lib.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.example.lib.utils.ScanGunKeyEventHelper;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public abstract class MvvmScanFragment<V extends ViewDataBinding, VM extends IMvvmBaseViewModel> extends Fragment implements IBaseView,View.OnKeyListener, ScanGunKeyEventHelper.OnScanSuccessListener{
    protected VM viewModel;
    protected V viewDataBinding;
    protected String mFragmentTag = "";
    private ProgressDialog mProgressDialog;
    public abstract int getBindingVariable();
    public abstract
    @LayoutRes
    int getLayoutId();
    public abstract VM getViewModel();

    /** 子类Fragment代码入口 */
    public abstract void init();
    /** 初始化数据 */
    protected abstract void initData();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParameters();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return viewDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = getViewModel();
        if (viewModel != null) {
            viewModel.attachUI(this);
        }
        if(getBindingVariable() > 0) {
            viewDataBinding.setVariable(getBindingVariable(), viewModel);
            viewDataBinding.executePendingBindings();
        }
        init();
        initData();
    }
    /***
     *   初始化参数
     */
    protected void initParameters() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    /**
     * 子类根据具体业务实现文字和图片的显示
     * */
    protected void onEmptyViewShow(TextView emptyText, ImageView emptyImage){
    }



    private boolean isShowedContent = false;




    @Override
    public void showLoadingDialog(String hint) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
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
    protected abstract void onRetryBtnClick();



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(getFragmentTag(), this + ": " + "onActivityCreated");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(getContext());
        Log.d(getFragmentTag(), this + ": " + "onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (viewModel != null && viewModel.isUIAttached()) {
            viewModel.detachUI();
        }
        Log.d(getFragmentTag(), this + ": " + "onDetach");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(getFragmentTag(), this + ": " + "onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(getFragmentTag(), this + ": " + "onPause");
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        Log.d(getFragmentTag(), this + ": " + "onResume");
        onBackClick(viewDataBinding.getRoot());
    }

    @Override
    public void onDestroy() {
        Log.d(getFragmentTag(), this + ": " + "onDestroy");
        super.onDestroy();
    }


    
    protected abstract String getFragmentTag();
    /**
     * 设置透明状态栏
     */
    private void setStatusBarTransparent(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

    }

    //扫描枪
    ScanGunKeyEventHelper mScanGunKeyEventHelper = new ScanGunKeyEventHelper(this);

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            //这边判断,如果是back的按键被点击了   就自己拦截实现掉
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                //回到首页
                return false;//表示处理了
            } else if (mScanGunKeyEventHelper.isScanGunEvent(keyEvent)) {
                mScanGunKeyEventHelper.analysisKeyEvent(keyEvent);
                return true;
            }
        }
        return false;
    }
    @Override
    public void onScanSuccess(String barcode) {
        EventBus.getDefault().postSticky(barcode);
    }
    private void onBackClick(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        //拦截返回键
        view.setOnKeyListener(this);
    }
    /*
     * fragment中的返回键
     *
     * 默认返回flase，交给Activity处理
     * 返回true：执行fragment中需要执行的逻辑
     * 返回false：执行activity中的 onBackPressed
     * */
    public boolean onBackPressed() {
        return false;
    }


}
