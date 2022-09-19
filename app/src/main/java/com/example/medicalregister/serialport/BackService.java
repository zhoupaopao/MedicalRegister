package com.aill.serialportdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.xinchen.allInOne.entity.SerialPortEntrity;
import com.xinchen.allInOne.serialport.LinkStatusListener;
import com.xinchen.allInOne.serialport.SerialInter;
import com.xinchen.allInOne.serialport.SerialManage;
import com.xinchen.allInOne.utils.Tools;

import org.greenrobot.eventbus.EventBus;

public class BackService extends Service implements SerialInter {
    //通过binder实现调用者client与Service之间的通信
    private LinkStatusListener listener;
    private MyBinder binder = new MyBinder();

    @Override
    public void connectMsg(String path, boolean isSucc) {
        String msg = isSucc ? "成功" : "失败";
        Log.e("串口连接回调", "串口 "+ path + " -连接" + msg);
        listener.success();
    }

    @Override
    public void connectFail(String path, boolean isSucc) {
        listener.fail();
    }

    @Override//若在串口开启的方法中 传入false 此处不会返回数据
    public void readData(String path, byte[] readBuf, int size) {
        //ASCToHex(readBuf)
        try {
            String readMessage = Tools.bytesToHexString(readBuf, size);
            //24 24 00 0D 81 02 02 3328E517 532E

            if (readMessage.length()==26){
                SerialPortEntrity.carNo.set(readMessage.substring(14,22));
                Log.e("接收卡号",readMessage.substring(14,22));
                EventBus.getDefault().postSticky(readMessage.substring(14,22));
            }

            //02 47 57 3a 30 30 30 31 38 2e 33 28 6b 67 29 03


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //client 可以通过Binder获取Service实例
    public class MyBinder extends Binder {
        public BackService getService() {
            return BackService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
    public void sendData(){
        SerialManage.getInstance().send("024103");//发送指令
        Log.e("发送","024103");
    }
    public void openSerial(String devicePath, int baudrate, int dataBits, int stopBits, int parity, boolean isRead, LinkStatusListener listener){
       this.listener=listener;
        SerialManage.getInstance().open(devicePath,baudrate,dataBits,stopBits,parity,isRead);//打开串口
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        SerialManage.getInstance().init(this);//串口初始化
       // device=intent.getParcelableExtra("device");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        SerialManage.getInstance().colse();
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }




}
