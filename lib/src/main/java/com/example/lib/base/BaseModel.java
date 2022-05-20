package com.example.lib.base;


import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * 标准MVVM模式中的M (Model)层基类
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class BaseModel implements LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(){

    };

}
