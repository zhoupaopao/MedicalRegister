package com.example.medicalregister.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;


import com.example.medicalregister.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 播放声音工具类
 * creator: ZZF
 * careate date: 2018/5/25  10:36.
 */

public class SoundUtils {

    private SoundPool pool;
    private List<Object> listPool = new ArrayList<Object>();
    private boolean isOpen;//是否播放声音

    public void initPool(Context context) {
        setOpen(true);

        pool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
//这里添加自己的需要的音频文件
        listPool.add(pool.load(context, R.raw.login_success, 0));
        listPool.add(pool.load(context, R.raw.register_finish_labeling, 0));
    }

    private static SoundUtils instance = null;

    private SoundUtils() {
    }

    public static SoundUtils getInstance() {
        synchronized (SoundUtils.class) {
            if (instance == null) {
                instance = new SoundUtils();
            }
        }

        return instance;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    /**
     * list 坐标位置
     *
     * @param flag
     */
    public void playVoice(int flag) {
        boolean b = isOpen();
        if (b) {
            // 消费成功 提示声音
            if (null != pool) {
                int index = (Integer) listPool.get(flag);
                pool.play(index, 1, 1, 0, 0, 1);
            }
        }
//        else if (flag == 0 && !b) {
//            flag = (listPool.size() - 1);
//            if (null != pool) {
//                int index = (Integer) listPool.get(flag);
//                pool.video_play(index, 1, 1, 0, 0, 1);
//            }
//        }
        else {
            Log.d("hwhw", "===========语音提示关闭");
        }
    }
}

