package com.example.lib.base;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.lib.R;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2019/3/25.
 */

public class ScanQRCodeActivity extends Activity {
    private CaptureManager capture;
    private DecoratedBarcodeView bv_barcode;
    private TextView sgd;
    private static final int CAMERA_REQUEST_CODE = 2;//请求码
    private CameraManager cameraManager;
    private boolean isOpen = true;//默认关闭
    private Camera camera;
    protected String[] needPermissions = {
            Manifest.permission.CAMERA
    };
    private long mExitTime=0;
    private static final int PERMISSON_REQUESTCODE = 0;
    private ImageView question;
    private RelativeLayout rl1;
    private RelativeLayout rl2;
    private ImageView sdt;
    private SharedPreferences sp;
    private BarcodeCallback barcodeCallback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (result != null){
                bv_barcode.pause();
                Log.e(getClass().getName(), "获取到的扫描结果是：" + result.getText());
//                Toast.makeText(ScanQRCodeActivity.this,"获取到的扫描结果是：" + result.getText(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("SN", result.getText());
                setResult(Activity.RESULT_OK, intent);
                finish();
//可以对result进行一些判断，比如说扫描结果不符合就再进行一次扫描
//                if (result.getText().contains("符合我的结果")){
                    //符合的可以不在扫描了，当然你想继续扫描也是可以的
//                    Toast.makeText(ScanQRCodeActivity.this,"获取到的扫描结果是：" + result.getText()+",正确跳转中……",Toast.LENGTH_SHORT).show();
//                    Intent intent=new Intent();
//                    intent.setClass(ScanQRCodeActivity.this,StartSettingActivity.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Toast.makeText(ScanQRCodeActivity.this,"不符合要求，获取到的扫描结果是：" + result.getText(),Toast.LENGTH_SHORT).show();
//                    bv_barcode.resume();
//                }
            }
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {

        }
    };
    public static void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360; // compensate the mirror
        } else { // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode);

        closeAndroidPDialog();//关闭androidp提示框使用，要慎用
//        checkPersion();
        initView();
        initListener();


    }

    private void initView() {
        bv_barcode = (DecoratedBarcodeView) findViewById(R.id.bv_barcode);
        bv_barcode.decodeContinuous(barcodeCallback);


    }

    private void initListener() {

    }

    // 判断是否有闪光灯功能
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }
    private void closeAndroidPDialog(){
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkPersion() {
        try {

            if (Build.VERSION.SDK_INT >= 23
                    && getApplicationInfo().targetSdkVersion >= 23) {
                List<String> needRequestPermissonList = new ArrayList<>();
                needRequestPermissonList.add(Manifest.permission.CAMERA);
                if (null != needRequestPermissonList
                        && needRequestPermissonList.size() > 0) {
                    String[] array = needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]);
                    Method method = getClass().getMethod("requestPermissions", new Class[]{String[].class,
                            int.class});

                    method.invoke(this, array, PERMISSON_REQUESTCODE);
                }
            }
        } catch (Throwable e) {
        }
    }
    @TargetApi(23)
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(paramArrayOfInt)) {
                finish();
            }
        }
    }
    /**
     * 检测是否所有的权限都已经授权
     * @param grantResults
     * @return
     * @since 2.5.0
     *
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
//        capture.onResume();
        bv_barcode.resume();
    }


    @Override
    protected void onPause() {
        super.onPause();
//        capture.onPause();
        bv_barcode.pause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1){
            String SN=data.getStringExtra("SN");
            Toast.makeText(ScanQRCodeActivity.this,SN, Toast.LENGTH_SHORT).show();
//            Intent intent=new Intent();re
//            intent.putExtra("SN",SN);
//            intent.setClass(ScanQRCodeActivity.this,AmmeterSettingActivity.class);
//            startActivity(intent);
        }
    }
}
