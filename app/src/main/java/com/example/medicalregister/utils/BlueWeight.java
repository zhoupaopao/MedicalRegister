package com.example.medicalregister.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.lib.base.BaseActivity;
import com.example.lib.utils.SharedPrefUtil;
import com.example.medicalregister.AppAplication;
import com.example.medicalregister.base.BasePrintActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by Lenovo on 2019/9/10.
 */

public class BlueWeight {
    private static BluetoothDevice btDevice;
    //    BluetoothAdapter mBluetoothAdapter;
    private static BasePrintActivity activity;
    public static final int HANDLER_MSG_WHAT_READ_SUCCESS = 403;
    public static final int HANDLER_MSG_WHAT_CONN_FAIL = 404;
    public static final int HANDLER_MSG_WHAT_CONN_WAIT = 406;
    public static final int HANDLER_MSG_WHAT_CONN_OFF = 405;
    public static final int HANDLER_MSG_WHAT_SUCCESS = 400;
    private ConnectThread mConnectThread;
    public ConnectedThread mConnectedThread;
    public static String tempWeight = "0";
    private BluetoothSocket mmSocket;
    private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public static boolean isRead = false;
    private int reConnectNum = 3;//重连次数

    public BlueWeight(BasePrintActivity activity1) {
        activity = activity1;
    }

    public BlueWeight() {
    }

    public synchronized void reConnectBluetooth() {
        mConnectThread = new ConnectThread(btDevice);
        mConnectThread.start();
    }

    public synchronized void connectBluetooth(BasePrintActivity activity1, String address, BluetoothAdapter mBluetoothAdapter) {
        activity = activity1;
        btDevice = mBluetoothAdapter.getRemoteDevice(address);
        reConnectNum=3;
        mConnectThread = new ConnectThread(btDevice);
        mConnectThread.start();
    }

    public void DestoryThread() {
        try {
            if (mmSocket != null) {
                mmSocket.close();
                mmSocket = null;
            }
        } catch (Exception ie) {
        }
        isRead = false;
    }

    private class ConnectThread extends Thread {

        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            mmDevice = device;
            BluetoothSocket tmp = null;

            try {
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (Exception e) {
                Log.e("zzz", "create() failed", e);
            }
            mmSocket = tmp;
        }

        @Override
        public void run() {
            Log.i("zzz", "BEGIN mConnectThread");

            Message msg1 = new Message();
            msg1.what = HANDLER_MSG_WHAT_CONN_WAIT;
            handler.sendMessage(msg1);
            setName("ConnectThread");
            try {
                if (!mmSocket.isConnected()) {
                    mmSocket.connect();
                }
            } catch (Exception ioe) {
//                try {
//                    Method m = mmDevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
//                    mmSocket = (BluetoothSocket) m.invoke(mmDevice, 1);
//                    mmSocket.connect();
//                } catch (Exception e) {
//                    Log.e("BLUE", e.toString());
//                    try {
//                        mmSocket.close();
//                    } catch (IOException ie) {
//                    }
//                }
                Message msg = new Message();
                msg.what = HANDLER_MSG_WHAT_CONN_FAIL;
                handler.sendMessage(msg);
                Log.e("zzz", "unable to connect() socket", ioe);
                try {
                    if (mmSocket != null) {
                        mmSocket.close();
                        mmSocket = null;
                    }
                } catch (Exception e2) {
                    Log.e("zzz", "unable to close() socket during connection failure", e2);
                }
                return;
            }

            mConnectedThread = new ConnectedThread(mmSocket);
            mConnectedThread.start();

        }


    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;

        public ConnectedThread(BluetoothSocket socket) {
            Log.d("zzz", "create ConnectedThread");
            mmSocket = socket;
            InputStream tmpIn = null;
            try {
                tmpIn = socket.getInputStream();
            } catch (Exception e) {
                SharedPrefUtil.putBlueWeight_State("0");
                AppAplication.getSound().playShortResource("蓝牙连接失败");
                Log.e("zzz", "temp sockets not created", e);
            }

            mmInStream = tmpIn;

            isRead = true;
        }

        @Override
        public void run() {
            Log.i("zzz", "BEGIN mConnectedThread");
            byte[] buffer = new byte[1024];
            int count;
            String readMsg;

            while (isRead) {
                try {
                    // Read from the InputStream

                    Message msg1 = new Message();
                    msg1.what = HANDLER_MSG_WHAT_SUCCESS;
                    handler.handleMessage(msg1);

                    //报错
                    count = mmInStream.read(buffer);
                    Message msg = new Message();
                    msg.what = HANDLER_MSG_WHAT_READ_SUCCESS;
                    readMsg = new String(buffer, 0, count);

//                    Log.e("saber",new String(buffer,0,mmInStream.read()));
                    Bundle data = new Bundle();
                    data.putString("BTdata", readMsg);
                    msg.setData(data);
                    handler.handleMessage(msg);
                } catch (Exception e) {
                    Log.e("zzz", "disconnected", e);
                    Message msg = new Message();
                    isRead = false;
                    tempWeight = "0";
                    msg.what = HANDLER_MSG_WHAT_SUCCESS;
                    handler.handleMessage(msg);
                    try {
                        if (!mmSocket.isConnected()) {
                            mmSocket.connect();
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    // 创建handler，因为我们接收是采用线程来接收的，在线程中无法操作UI，所以需要handler

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_MSG_WHAT_READ_SUCCESS:
                    try {
                        Bundle data = msg.getData();
                        String res = data.getString("BTdata");
//                    Log.e("caster", res);

                        String str1 = res;
//                    String str1 = (String) msg.obj;
//                    Log.e("lancer", "(String) msg.obj = " + str1);
                        if (str1.length() > 10) {
                            String str = StringUtil.upsideDown(res);
//                        String str = StringUtil.upsideDown((String) msg.obj);
//                        Log.e("caster", "StringUtil.upsideDown((String) msg.obj) = " + str);
                            if (res.length() > 8) {
                                String finRes = StringUtil.upsideDown(res);
                                if (StringUtil.isEmpty(tempWeight)) {
                                    tempWeight = finRes;
//                                runOnUiThread(() -> etWeight.setText(tempWeight));
//                                Log.e("caster", finRes);
                                } else {

                                    if (!tempWeight.equals(finRes)) {
                                        tempWeight = finRes;
//                                    runOnUiThread(() -> etWeight.setText(tempWeight));
//                                    Log.e("caster", "相同相同相同相同相同相同");
                                    }
                                }
                            }

//                        runOnUiThread(() -> {
//                            String str2 = (String) msg.obj;
//                            if (str2.length() > 8) {
//                                String finalStr = StringUtil.upsideDown((String) msg.obj);
//                                if (StringUtil.isEmpty(tempWeight)) {
//                                    Log.d("lancer", "tempWeight null    finalStr= " + finalStr);
//                                    tempWeight = finalStr;
//                                    etWeight.setText(finalStr);
//                                } else {
//                                    Log.d("lancer", "tempWeight =" + tempWeight + "    finalStr= " + finalStr);
//                                    if (!tempWeight.equals(finalStr)) {
//                                        Log.d("lancer", "相同相同相同相同相同相同");
//                                        tempWeight = finalStr;
//                                        etWeight.setText(finalStr);
//                                    }
//                                }
//                            }
//                        });
                        } else {
                            String finRes = "0";
                            finRes = StringUtil.upsideDown1(res);

//                        Log.e("caster1", finRes);
                            if (StringUtil.isEmpty(tempWeight)) {
                                tempWeight = finRes;
//                                runOnUiThread(() -> etWeight.setText(tempWeight));
//                            Log.e("caster1", finRes);
                            } else {

                                if (!tempWeight.equals(finRes)) {
                                    tempWeight = finRes;
//                                    runOnUiThread(() -> etWeight.setText(tempWeight));
//                                Log.e("caster", "bu相同相同相同相同相同相同");
                                } else {
//                                Log.e("caster", "相同相同相同相同相同相同");
                                }
                            }
                        }
                        break;
                    } catch (Exception e) {

                    }

                case HANDLER_MSG_WHAT_CONN_FAIL:
                    activity.hideLoadingDialog();
//                    Log.d("zzz", "【连接失败：】" + msg.obj);
//                    runOnUiThread(() -> etWeight.setText(tempWeight));
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(SharedPrefUtil.getBoolean("needShowMsg",true)){
                                if(reConnectNum>0){
                                    reConnectBluetooth();
                                    reConnectNum=reConnectNum-1;
                                }else{
                                    Toast.makeText(activity, "蓝牙地磅连接失败", Toast.LENGTH_SHORT).show();
                                    AppAplication.getSound().playShortResource("蓝牙连接失败");
                                }


                            }

                        }
                    });
                    break;
                case HANDLER_MSG_WHAT_CONN_WAIT:
                    if(SharedPrefUtil.getBoolean("needShowMsg",true)){
                        activity.showLoadingDialog("加载中");
                    }

                    break;
                case HANDLER_MSG_WHAT_SUCCESS:
                    activity.hideLoadingDialog();
//                    Looper.prepare();
//                    Toast.makeText(activity,"连接成功",Toast.LENGTH_SHORT).show();
//                    Looper.loop();

                    break;
                default:
                    break;
            }
        }
    };
}
