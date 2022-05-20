package com.example.lib.base;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lib.livedata.MessageEvent;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;


/**
 * @author Administrator
 */
public class MvvmBaseViewModel<V> extends BaseViewModel implements IMvvmBaseViewModel<V> {
    private Reference<V> mUIRef;
    /**
     * 消息事件
     */
    private MessageEvent mMessageEvent = new MessageEvent();
//    protected M model;
public MutableLiveData<Boolean> loadingEvent=new MutableLiveData<>();//控制加载显示

    @Override
    public void attachUI(V ui) {
        mUIRef = new WeakReference<>(ui);
    }

    @Override
    @Nullable
    public V getPageView() {
        if (mUIRef == null) {
            return null;
        }
        return mUIRef.get();
    }

    @Override
    public boolean isUIAttached() {
        return mUIRef != null && mUIRef.get() != null;
    }

    @Override
    public void detachUI() {
        if (mUIRef != null) {
            mUIRef.clear();
            mUIRef = null;

        }
    }

    @Override
    public void showLoading(Boolean isShow) {
        loadingEvent.postValue(isShow);
    }

    @Override
    public void onDestroy() {
//        if(mModel != null){
//            mModel.onDestroy();
//            mModel = null;
//        }
        mMessageEvent.call();
    }
    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
    }
//    /**
//     * {@link BaseModel}
//     * @return {@link #mModel}
//     */
//    public BaseModel getModel(){
//        return this.mModel;
//    }

    /**
     * @return {@link #mMessageEvent}
     */
    @Override
    public MessageEvent getMessageEvent(){
        return mMessageEvent;
    }



    /**
     * 也可通过观察{@link #getMessageEvent()}接收消息事件
     * @param message 消息内容
     */
    @Override
    public void sendMessage(String message){
        mMessageEvent.setValue(message);
    }


}
