package com.example.dongwei.intface;

import android.view.View;

import java.util.Calendar;

/**
 * 防止多次点击，时间设定为3s
 */
public abstract class UnDoubleClickListener implements View.OnClickListener {
    public static final int MIN_CLICK_DELAY_TIME = 3000;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

    protected abstract void onNoDoubleClick(View v);
}
