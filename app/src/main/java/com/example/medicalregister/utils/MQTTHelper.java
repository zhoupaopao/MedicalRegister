package com.example.medicalregister.utils;

import android.content.Context;
import android.text.TextUtils;

import com.example.lib.utils.PhoneInfoUtil;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQTTHelper {
//    private static final String SERVER_URI = "tcp://120.76.53.41:1884";
private static final String SERVER_URI = "tcp://47.115.62.165:1884";
    private static final String CLIENT_ID = PhoneInfoUtil.getUUID();

    private static final String USER_NAME = "admin";
    private static final String PASSWORD = "xchk666";

    private String mTopic;
    private boolean isConnected;
    private MqttAndroidClient mClient;

    public MQTTHelper(Context context, String topic, MqttCallback mqttCallback) {
        this.mTopic = topic;
        this.mClient = new MqttAndroidClient(context.getApplicationContext(), SERVER_URI, CLIENT_ID+ System.currentTimeMillis());
        if (mqttCallback != null) {
            this.mClient.setCallback(mqttCallback);
        }
    }

    /**
     * 触发连接
     */
    public void doConnect() {
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setUserName(USER_NAME);
        connectOptions.setPassword(PASSWORD.toCharArray());
        connectOptions.setAutomaticReconnect(true);
        //设置是否清除session，清除后服务器不会保留记忆
        connectOptions.setCleanSession(false);
        //设置超时时间，默认30s
        connectOptions.setConnectionTimeout(45);
        //设置心跳时间，默认60s
        connectOptions.setKeepAliveInterval(30);
        try {
            mClient.connect(connectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    isConnected = true;
                    DisconnectedBufferOptions disconnectedBufferOptions =
                            new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mClient.setBufferOpts(disconnectedBufferOptions);
                    subscribeToTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    isConnected = false;
                }
            });

        } catch (MqttException ex) {
            ex.printStackTrace();
        }
    }

    private void subscribeToTopic() {
        try {
            mClient.subscribe(mTopic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                }
            });

        } catch (MqttException ex) {
            ex.printStackTrace();
        }
    }
    public void subscribeToTopic(String topic) {
        try {
            mClient.subscribe(topic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                }
            });

        } catch (MqttException ex) {
            ex.printStackTrace();
        }
    }

    public void publish(String topic, String text) {
        if (TextUtils.isEmpty(text)) {
            text = "Hello MQTT ";
        }
        //此处消息体需要传入byte数组
        MqttMessage message = new MqttMessage(text.getBytes());
        //设置质量级别
        message.setQos(0);
        if(mClient!=null && isConnected) {
            try {
                mClient.publish(topic,message);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    public MqttAndroidClient getmClient() {
        return mClient;
    }

    public void disconnect() {
        try {
            if (mClient != null & isConnected) {
                mClient.disconnect();
                this.isConnected = false;
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
