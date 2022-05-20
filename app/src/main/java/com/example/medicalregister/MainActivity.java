package com.example.medicalregister;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.arch.demo.network_api.errorhandler.ExceptionHandle;
import com.arch.demo.network_api.observer.BaseObserver;
import com.example.lib.bean.TokenBean;
import com.example.lib.utils.PhoneInfoUtil;
import com.example.lib.utils.TimeUtil;
import com.example.medicalregister.activity.EnterEquipActivity;
import com.example.medicalregister.activity.HomeMainActivity;
import com.example.lib.utils.SharedPrefUtil;
import com.example.medicalregister.activity.LabelListActivity;
import com.example.medicalregister.activity.PrintLabelActivity;
import com.example.medicalregister.activity.WeightRegisterActivity;
import com.example.medicalregister.bean.DeviceUnit;
import com.example.medicalregister.bean.EmployeesBean;
import com.example.medicalregister.http.Api;
import com.example.medicalregister.utils.PermissionUtils;
import com.example.medicalregister.utils.StringUtil;
import com.nlf.calendar.Lunar;

import java.io.IOException;

public class MainActivity extends Activity {
    Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        if(SharedPrefUtil.getString(SharedPrefUtil.ip).equals("")){
//        TokenBean tokenBean=new TokenBean();
//        tokenBean.setToken_type("bearer");
//        tokenBean.setAccess_token("a0f0537f-d11a-4ab3-a36a-ab25f3ea1903");
//        SharedPrefUtil.putTokenBean(tokenBean);
//        SharedPrefUtil.putString(SharedPrefUtil.ip,"192.168.5.96");
//            SharedPrefUtil.putString(SharedPrefUtil.ip,"180.113.146.185");
//        SharedPrefUtil.putString(SharedPrefUtil.port,"9097");
            SharedPrefUtil.putString(SharedPrefUtil.ip,"114.115.204.108");
            SharedPrefUtil.putString(SharedPrefUtil.port,"8080");

        PermissionUtils.medicalRegisterQx(new PermissionUtils.PermissionResult() {
            @Override
            public void ok() {
                //这里要判断设备是否有录入了，录入过了直接进入
//                startActivity(new Intent(MainActivity.this, PrintLabelActivity.class));
//                startActivity(new Intent(MainActivity.this, HomeMainActivity.class));
                String deviceId= null;
                try {
                    deviceId = PhoneInfoUtil.readKey(MainActivity.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("DeviceId", deviceId);
                if(!StringUtil.isEmpty(deviceId)&&SharedPrefUtil.getUserBean()!=null){
                    startActivity(new Intent(MainActivity.this, HomeMainActivity.class));
                }else{
                    startActivity(new Intent(MainActivity.this, EnterEquipActivity.class));
                }
//                startActivity(new Intent(MainActivity.this, EnterEquipActivity.class));
//                startActivity(new Intent(MainActivity.this, LabelListActivity.class));
//                Intent intent = new Intent(MainActivity.this, WeightRegisterActivity.class);
//                startActivity(intent);
                finish();
            }
        });

        handler.postDelayed(runnable,0);
//        try {
//            PhoneInfoUtil.saveBitmap(this);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        }
//        SharedPrefUtil.putUsername("18800000001");
//        SharedPrefUtil.putPassword("123456");
//        if(SharedPrefUtil.getWorkStation()!=null){

    }
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
//            Log.i("run: ", "run: ");
            //获取时间
            Log.i("TAG", "心跳");
            heartDances();
            handler.postDelayed(this, 60000);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        getApplicationContext().unregisterReceiver(null);
    }
    private void heartDances() {
//        Api.getInstance().getProductionOrderList(offset, limit, new BaseListObserver<ArrayList<ProductionOrderDTO>>() {
        Api.getInstance().heartDances(new BaseObserver<String>() {
            @Override
            protected void onHandleSuccess(String deviceUnit) {
                //获取到设备信息，进行和工牌信息比对
                Log.i("TAG", deviceUnit);
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
//                sendMessage("fail");
            }
        });
    }
}