package com.example.medicalregister.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import com.example.medicalregister.R;

import java.util.HashMap;

public class SoundPoolPlayer {
    private SoundPool mShortPlayer= null;

    private HashMap mSounds = new HashMap();

    public SoundPoolPlayer(Context pContext)

    {

// setup Soundpool

        this.mShortPlayer = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        mSounds.put("登录成功", this.mShortPlayer.load(pContext, R.raw.login_success, 1));
        mSounds.put("登记完成，请粘贴标签", this.mShortPlayer.load(pContext, R.raw.register_finish_labeling, 1));
//        mSounds.put("该医废袋已被收集", this.mShortPlayer.load(pContext, R.raw.wav1, 1));
        mSounds.put("检测不到称重设备", this.mShortPlayer.load(pContext, R.raw.wav2, 1));
        mSounds.put("蓝牙连接成功", this.mShortPlayer.load(pContext, R.raw.wav3, 1));
        mSounds.put("蓝牙连接失败", this.mShortPlayer.load(pContext, R.raw.wav4, 1));
        mSounds.put("请进行称重", this.mShortPlayer.load(pContext, R.raw.wav5, 1));
        mSounds.put("请确认身份信息", this.mShortPlayer.load(pContext, R.raw.wav6, 1));//这个没有
        mSounds.put("请扫科室二维码", this.mShortPlayer.load(pContext, R.raw.wav7, 1));
        mSounds.put("请扫描护士工牌", this.mShortPlayer.load(pContext, R.raw.wav8, 1));
        mSounds.put("请选择您要补打的标签", this.mShortPlayer.load(pContext, R.raw.wav9, 1));
        mSounds.put("请选择医废类型", this.mShortPlayer.load(pContext, R.raw.wav10, 1));

//        mSounds.put(R.raw., this.mShortPlayer.load(pContext, R.raw., 1));

    }

    public void playShortResource(String piResource) {

        int iSoundId = (Integer) mSounds.get(piResource);

        this.mShortPlayer.play(iSoundId, 0.99f, 0.99f, 0, 0, 1);

    }


// Cleanup

    public void release() {

// Cleanup

        this.mShortPlayer.release();

        this.mShortPlayer = null;

    }
}
