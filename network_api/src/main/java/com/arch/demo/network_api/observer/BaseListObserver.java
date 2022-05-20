package com.arch.demo.network_api.observer;

import android.util.Log;
import android.widget.Toast;

import com.arch.demo.network_api.beans.BaseListResponse;
import com.arch.demo.network_api.beans.ErrorMessageBean;
import com.arch.demo.network_api.errorhandler.ExceptionHandle;
import com.example.lib.base.BaseApplication;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 *
 * @author pqc
 */
public abstract class BaseListObserver<T> implements Observer<BaseListResponse<T>> {//BaseResponse<T>
    private String TAG = getClass().getSimpleName();
    private static LogoutCallBack logoutCallBack;
    protected Disposable disposable;

    public static void setLogoutCallBack(LogoutCallBack logoutCallBack){
        BaseListObserver.logoutCallBack=logoutCallBack;
    }
    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }
    @Override
    public void onComplete() {
    }
//    @Override
//    public void onNext(BaseResponse<T> value) {
//        if (value.isSuccess()) {
//            if (value.hasData()) {
//                T t = value.getData();
//                onHandleSuccess(t);
//            } else {
//                T t = value.getData();
//                onHandleSuccess(t);
////                onHandleSuccess(value.getData(),value.getMsg());
//            }
//        }else if (value.getErr().equals("10002")){
//            // 退出登录回调
//            if (logoutCallBack==null){
//                ExceptionHandle.ResponeThrowable exception=new ExceptionHandle.ResponeThrowable(new RuntimeException(), ExceptionHandle.ERROR.UNKNOWN);
//                exception.message="退出登录回调未初始化";
//                onError(exception);
//            }else {
//                logoutCallBack.logout(value.getMsg());
//            }
//        }
//    }

    @Override
    public void onNext(BaseListResponse<T> value) {
        T t=value.getList();
        if(t==null){//由于有时候是data有时候是list
            onHandleSuccess(value.getData());
        }else{
            onHandleSuccess(t);
        }

//                onHandleSuccess(value);
//                onHandleSuccess(value.getData(),value.getMsg());
    }
    @Override
    public void onError(Throwable e) {
//        Log.e("err", e.getMessage());
        // todo error somthing
        disposable.dispose();

        if (e instanceof HttpException) {

            HttpException httpException = (HttpException) e;
//            if (httpException.code() == 401) {
//                BaseApplication.getInstance().doReLogin();
//            } else {
                try {

                    String responseString = httpException.response().errorBody().string();
                    ErrorMessageBean errorMessageBean=new Gson().fromJson(responseString,ErrorMessageBean.class);
                    Toast.makeText(BaseApplication.getAppContext(), errorMessageBean.getError().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("onError: ", responseString);
                } catch (Exception e1) {
                    Toast.makeText(BaseApplication.getAppContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
                    e1.printStackTrace();

                }
//            }
        } else {
            Toast.makeText(BaseApplication.getAppContext(), "网络异常", Toast.LENGTH_SHORT).show();
        }
        if(e instanceof ExceptionHandle.ResponeThrowable){
            onError((ExceptionHandle.ResponeThrowable)e);
        } else {
            onError(new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }


    protected abstract void onHandleSuccess(T t);


    protected void onHandleSuccess(T t, String msg) {

    }

    public abstract void onError(ExceptionHandle.ResponeThrowable e);

    public interface LogoutCallBack{
        void logout(String msg);
    }
}
