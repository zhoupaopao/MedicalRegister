package com.example.lib.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;


import androidx.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;

import com.example.lib.bean.TokenBean;
import com.example.lib.utils.SharedPrefUtil;
import com.example.lib.utils.StringUtils;
import com.example.lib.utils.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;


/**
 * 要想使用BaseApplication，必须在组件中实现自己的Application，并且继承BaseApplication；
 * 组件中实现的Application必须在debug包中的AndroidManifest.xml中注册，否则无法使用；
 * 组件的Application需置于java/debug文件夹中，不得放于主代码；
 * 组件中获取Context的方法必须为:Utils.getContext()，不允许其他写法；
 *
 * @author 2016/12/2 17:02
 * @version V1.0.0
 * @name BaseApplication
 */
public class BaseApplication extends MultiDexApplication {

    public static final String ROOT_PACKAGE = "com.emr_pad";

    private static BaseApplication sInstance;

    private static Context mContext;



    public static Context getAppContext() {
        return mContext;
    }
    public static BaseApplication getIns() {
        if (sInstance == null) {
            sInstance = new BaseApplication();
        }
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mContext=this;
        Utils.init(this);

        if (isAppDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
        //初始化sp存储
        SharedPrefUtil.init(this);
//        //回调接口初始化完成接口回调
//        QbSdk.PreInitCallback pcb=new QbSdk.PreInitCallback() {
//            @Override
//            public void onCoreInitFinished() {
//
//            }
//            @Override
//            public void onViewInitFinished(boolean b) {
//                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
//                Log.e("myApplication", " x5内核加载成功？" + b);
//            }
//        };
//
//        //x5内核预加载，异步初始化x5 webview所需环境
//        QbSdk.initX5Environment(getApplicationContext(), pcb);

        }

    private static List<Activity> activityList = new ArrayList<>();
    private static List<Activity> activitySubmitList = new ArrayList<>();
    /**
     * 添加Activity到容器中
     */
    public static void addActivity(Activity activity) {
        if (activityList != null && activityList.size() > 0) {
            if (!activityList.contains(activity)) {
                activityList.add(activity);
            }
        } else {
            activityList.add(activity);
        }
    }


    public static boolean isAppDebug() {
        if (StringUtils.isSpace(mContext.getPackageName())) return false;
        try {
            PackageManager pm = mContext.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(mContext.getPackageName(), 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }





    public static void doReLogin() {

        Log.e("zzz", "执行了dorelogin");

        Log.e("zzz", "activityList.size():" + activityList.size());
        if (activityList != null && activityList.size() > 0) {
            for (Activity activity : activityList) {
                activity.finish();
            }
        }


        Log.e("zzz", "SharedPrefUtil.exitLogin()");
        SharedPrefUtil.exitLogin();


//        if (activityList != null && activityList.size() > 0) {
//            for (Activity activity : activityList) {
//                activity.finish();
//            }
//        }
//        SharedPrefUtil.exitLogin();

    }
}
