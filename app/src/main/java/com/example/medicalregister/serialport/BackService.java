package com.example.medicalregister.serialport;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;



import org.greenrobot.eventbus.EventBus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        try {
            String readMessage = bytesToHexString(readBuf);
            Log.e("接收bbbbbb",readMessage);
            String str= testRegexp(readMessage);
            Log.e("接收AAAAa",str);
            String weight=hexToAscii(str.toString());
            Double d=Double.parseDouble(weight);
            Log.e("TAG", String.valueOf(d));
            EventBus.getDefault().postSticky(String.valueOf(d));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  String hexToAscii(String hexStr) {
        StringBuffer output = new StringBuffer("");
        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));

        }
        return output.reverse().toString();
    }
    /**
     * 截取字符串
     * */
    public  String testRegexp(String s) {
        if (s != null && !"".equals(s)) {
            String regex = "[0-9a-fA-F]{14}3d";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(s);
            while (matcher.find()) {
                String matcherString = matcher.group();
                return matcherString.substring(0,14);
            }
        }
        return s;
    }
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
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
