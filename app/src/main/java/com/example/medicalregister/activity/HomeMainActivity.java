package com.example.medicalregister.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.example.lib.base.BaseActivity;
import com.example.lib.utils.PhoneInfoUtil;
import com.example.lib.utils.ScanGunKeyEventHelper;
import com.example.lib.utils.SharedPrefUtil;
import com.example.lib.utils.TimeUtil;
import com.example.lib.utils.Tips;
import com.example.medicalregister.BR;
import com.example.medicalregister.R;
import com.example.medicalregister.databinding.ActivityHomeMainBinding;
import com.example.medicalregister.viewmodel.HomeMainViewModel;
import com.nlf.calendar.Lunar;

import java.io.IOException;
import java.sql.Time;

public class HomeMainActivity extends BaseActivity<ActivityHomeMainBinding, HomeMainViewModel>{
    Handler handler=new Handler();
    @Override
    protected void initListener() {
        handler.postDelayed(runnable,0);
//        handler.removeCallbacks(runnable);

        viewDataBinding.llExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefUtil.removeString(SharedPrefUtil.USER_Bean);
                startActivity(new Intent(HomeMainActivity.this,EnterEquipActivity.class));
                finish();
            }
        });
        viewDataBinding.viewPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(HomeMainActivity.this,PrintLabelActivity.class));
                startActivity(new Intent(HomeMainActivity.this,LabelListActivity.class));
            }
        });
        viewDataBinding.viewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeMainActivity.this,RegisterScanActivity.class));
            }
        });
        viewDataBinding.viewSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeMainActivity.this,SettingActivity.class));
            }
        });
    }
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
//            Log.i("run: ", "run: ");
            //获取时间
            viewDataBinding.tvSf.setText(TimeUtil.getCurrentTime("HH:mm:ss"));
            viewDataBinding.tvNyr.setText(TimeUtil.getCurrentTime("yyyy-MM-dd"));
            viewDataBinding.tvWeek.setText(TimeUtil.getNowWeek());
            Lunar date = new Lunar();
            viewDataBinding.tvNl.setText(date.getYearInGanZhi()+"年  "+date.getMonthInChinese() + "月" + date.getDayInChinese());
            handler.postDelayed(this, 1000);
        }
    };


    @Override
    protected void initData() {

    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    protected HomeMainViewModel getViewModel() {
        return new HomeMainViewModel();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_main;
    }





}
