package com.example.lib.livedata;


import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.atomic.AtomicBoolean;


/**
 * 提供观察单个对象{@link T}事件
 * @see <a href="https://github.com/googlesamples/android-architecture/blob/6419d4c523b67d020120fc400ed5a7372e5615f2/todoapp/app/src/main/java/com/example/android/architecture/blueprints/todoapp/SingleLiveEvent.java">google sample</a>
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class SingleLiveEvent<T> extends MutableLiveData<T> {

    private final AtomicBoolean mPending = new AtomicBoolean(false);
    private final String TAG="SingleLiveEvent";
    @MainThread
    public void observe(LifecycleOwner owner, final Observer<? super T> observer) {

        if (hasActiveObservers()) {
            Log.e(TAG,"Multiple observers registered but only one will be notified of changes.");
        }

        // Observe the internal MutableLiveData
        super.observe(owner, new Observer<T>() {
            @Override
            public void onChanged(@Nullable T t) {
                if (mPending.compareAndSet(true, false)) {
                    observer.onChanged(t);
                }
            }
        });
    }

    @MainThread
    public void setValue(@Nullable T t) {
        mPending.set(true);
        super.setValue(t);
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    public void call() {
        setValue(null);
    }
}