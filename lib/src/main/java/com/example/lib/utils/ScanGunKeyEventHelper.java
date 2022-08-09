package com.example.lib.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;

import java.util.Iterator;
import java.util.Set;

/**
 * 扫码枪事件解析类
 */
public class ScanGunKeyEventHelper {
    private final static long MESSAGE_DELAY = 500;//延迟500ms，判断扫码是否完成。
    private StringBuffer mStringBufferResult;//扫码内容
    private boolean mCaps;//大小写区分
    private final Handler mHandler;
    private final BluetoothAdapter mBluetoothAdapter;
    private final Runnable mScanningFishedRunnable;
    private OnScanSuccessListener mOnScanSuccessListener;
    private String mDeviceName="HID 0581:0106";

    public ScanGunKeyEventHelper(OnScanSuccessListener onScanSuccessListener) {
        mOnScanSuccessListener = onScanSuccessListener ;
        //获取系统蓝牙适配器管理类
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        BluetoothDevice printerdevice = mBluetoothAdapter.getRemoteDevice("ssss");
//        BluetoothSocket btSocket = printerdevice.createRfcommSocketToServiceRecord("ssss");
        mStringBufferResult = new StringBuffer();
        mHandler = new Handler();
        mScanningFishedRunnable = new Runnable() {
            @Override
            public void run() {
                performScanSuccess();
            }
        };
    }

    /**
     * 返回扫码成功后的结果
     */
    private void performScanSuccess() {
        String barcode = mStringBufferResult.toString();
        if (mOnScanSuccessListener != null)
            mOnScanSuccessListener.onScanSuccess(barcode);
        mStringBufferResult.setLength(0);
    }
    /**
     * 将keyCode转为char
     *
     * @param caps    是不是大写
     * @param keyCode 按键
     * @return 按键对应的char
     */
    private char getInputCode(boolean caps, int keyCode) {
        if (keyCode >= KeyEvent.KEYCODE_A && keyCode <= KeyEvent.KEYCODE_Z) {
            return (char) ((caps ? 'A' : 'a') + keyCode - KeyEvent.KEYCODE_A);
        } else {
//            return keyValue(caps, keyCode);
            return keyValue(false, keyCode);
        }
    }
    /**
     * 按键对应的char表
     */
    private char keyValue(boolean caps, int keyCode) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_0:
                return caps ? ')' : '0';
            case KeyEvent.KEYCODE_1:
                return caps ? '!' : '1';
            case KeyEvent.KEYCODE_2:
                return caps ? '@' : '2';
            case KeyEvent.KEYCODE_3:
                return caps ? '#' : '3';
            case KeyEvent.KEYCODE_4:
                return caps ? '$' : '4';
            case KeyEvent.KEYCODE_5:
                return caps ? '%' : '5';
            case KeyEvent.KEYCODE_6:
                return caps ? '^' : '6';
            case KeyEvent.KEYCODE_7:
                return caps ? '&' : '7';
            case KeyEvent.KEYCODE_8:
                return caps ? '*' : '8';
            case KeyEvent.KEYCODE_9:
                return caps ? '(' : '9';
            case KeyEvent.KEYCODE_NUMPAD_SUBTRACT:
                return '-';
//            case KeyEvent.KEYCODE_MINUS:
//                return '_';
            case KeyEvent.KEYCODE_MINUS:
                return '-';
            case KeyEvent.KEYCODE_EQUALS:
                return '=';
            case KeyEvent.KEYCODE_NUMPAD_ADD:
                return '+';
            case KeyEvent.KEYCODE_GRAVE:
                return caps ? '~' : '`';
            case KeyEvent.KEYCODE_BACKSLASH:
                return caps ? '|' : '\\';
            case KeyEvent.KEYCODE_LEFT_BRACKET:
                return caps ? '{' : '[';
            case KeyEvent.KEYCODE_RIGHT_BRACKET:
                return caps ? '}' : ']';
//            case KeyEvent.KEYCODE_SEMICOLON:
//                return caps ? ':' : ';';
            case KeyEvent.KEYCODE_SEMICOLON:
                return caps ? ';' : ':';
            case KeyEvent.KEYCODE_APOSTROPHE:
                return caps ? '"' : '\'';
            case KeyEvent.KEYCODE_COMMA:
                return caps ? '<' : ',';
            case KeyEvent.KEYCODE_PERIOD:
                return caps ? '>' : '.';
            case KeyEvent.KEYCODE_SLASH:
                return caps ? '?' : '/';
            default:
                return 0;
        }
    }
    /**
     * 扫码枪事件解析
     * @param event
     */
    public void analysisKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        //字母大小写判断
        checkLetterStatus(event);
        if (event.getAction() == KeyEvent.ACTION_DOWN) {

//            char aChar = getInputCode(mCaps, event.getKeyCode());
            char aChar = getInputCode(true, event.getKeyCode());
            if (aChar != 0) {
                mStringBufferResult.append(aChar);
            }
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                //若为回车键，直接返回
                mHandler.removeCallbacks(mScanningFishedRunnable);
                mHandler.post(mScanningFishedRunnable);
            } else {
                //延迟post，若500ms内，有其他事件
                mHandler.removeCallbacks(mScanningFishedRunnable);
                mHandler.postDelayed(mScanningFishedRunnable, MESSAGE_DELAY);
            }

        }
    }

    //检查shift键
    private void checkLetterStatus(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (keyCode == KeyEvent.KEYCODE_SHIFT_RIGHT || keyCode == KeyEvent.KEYCODE_SHIFT_LEFT) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                //按着shift键，表示大写
                Log.i("checkLetterStatus: ", "true");
                mCaps = true;
            } else {
                //松开shift键，表示小写
                Log.i("checkLetterStatus: ", "false");
                mCaps = false;
            }
        }
    }
    //获取扫描内容
    private char getInputCode(KeyEvent event) {
        int keyCode = event.getKeyCode();
        char aChar;
        if (keyCode >= KeyEvent.KEYCODE_A && keyCode <= KeyEvent.KEYCODE_Z) {
            //字母
            aChar = (char) ((mCaps ? 'A' : 'a') + keyCode - KeyEvent.KEYCODE_A);
        } else if (keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9) {
            //数字
            aChar = (char) ('0' + keyCode - KeyEvent.KEYCODE_0);
        } else {
            //其他符号
            switch (keyCode) {
                case KeyEvent.KEYCODE_PERIOD:
                    aChar = '.';
                    break;
                case KeyEvent.KEYCODE_MINUS:
                    aChar = mCaps ? '_' : '-';
                    break;
                case KeyEvent.KEYCODE_SLASH:
                    aChar = '/';
                    break;
                case KeyEvent.KEYCODE_BACKSLASH:
                    aChar = mCaps ? '|' : '\\';
                    break;
                default:
                    aChar = 0;
                    break;
            }
        }
        return aChar;
    }

    public interface OnScanSuccessListener {
        void onScanSuccess(String barcode);
    }

    public void onDestroy() {
        mHandler.removeCallbacks(mScanningFishedRunnable);
        mOnScanSuccessListener = null;
    }
    //部分手机如三星，无法使用该方法
//    private void hasScanGun() {
//        Configuration cfg = getResources().getConfiguration();
//        return cfg.keyboard != Configuration.KEYBOARD_NOKEYS;
//    }
    /**
     * 扫描枪是否连接
     * @return
     */
    public boolean hasScanGun() {
        if (mBluetoothAdapter == null) {
            return false;
        }
        Set<BluetoothDevice> blueDevices = mBluetoothAdapter.getBondedDevices();
        if (blueDevices == null || blueDevices.size() <= 0) {
            return false;
        }
        for (Iterator<BluetoothDevice> iterator = blueDevices.iterator(); iterator.hasNext(); ) {
            BluetoothDevice bluetoothDevice = iterator.next();

            if (bluetoothDevice.getBluetoothClass().getMajorDeviceClass() == BluetoothClass.Device.Major.PERIPHERAL) {

                mDeviceName = bluetoothDevice.getName();
                return isInputDeviceExist(mDeviceName);
            }
        }
        return false;
    }
    /**
     * 输入设备是否存在
     * @param deviceName
     * @return
     */
    private boolean isInputDeviceExist(String deviceName) {
        int[] deviceIds = InputDevice.getDeviceIds();

        for (int id : deviceIds) {
            if (InputDevice.getDevice(id).getName().equals(deviceName)) {
                return true;
            }
        }
        return false;
    }
    /**
     * 是否为扫码枪事件(部分机型KeyEvent获取的名字错误)
     * @param event
     * @return
     */
    @Deprecated
    public  boolean isScanGunEvent(KeyEvent event) {
//        String ddname=event.getDevice().getName();
        mDeviceName=event.getDevice().getName();
        Log.i("isScanGunEvent: ", mDeviceName);
        return event.getDevice().getName().equals(mDeviceName);
    }


    /**
     * 检测蓝牙是否开启
     */
    public int checkBluetoothValid() {

        if(mBluetoothAdapter == null) {//你的设备不具备蓝牙功能!
            return 1;
        }

        if(!mBluetoothAdapter.isEnabled()) {//蓝牙设备未打开,请开启此功能后重试!
            return 2;
        }
        return 3;//蓝牙正常工作
    }
}
