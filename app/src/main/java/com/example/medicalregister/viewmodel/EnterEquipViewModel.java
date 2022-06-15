package com.example.medicalregister.viewmodel;

import android.app.Application;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.arch.demo.network_api.errorhandler.ExceptionHandle;
import com.arch.demo.network_api.observer.BaseObserver;
import com.dou361.dialogui.DialogUIUtils;
import com.example.lib.base.MvvmBaseViewModel;
import com.example.lib.bean.TokenBean;
import com.example.lib.bean.UserBean;
import com.example.lib.utils.PhoneInfoUtil;
import com.example.lib.utils.SharedPrefUtil;
import com.example.lib.utils.Tips;
import com.example.lib.utils.Utils;
import com.example.medicalregister.AppAplication;
import com.example.medicalregister.R;
import com.example.medicalregister.bean.DeviceUnit;
import com.example.medicalregister.bean.EmployeesBean;
import com.example.medicalregister.bean.LabelBean;
import com.example.medicalregister.bean.PlatformLoginBean;
import com.example.medicalregister.bean.UpdateBean;
import com.example.medicalregister.http.Api;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.lib.utils.Utils.getContext;

public class EnterEquipViewModel extends MvvmBaseViewModel {

    private String title;
    private MutableLiveData<Integer>nowStep;//当前进行的步骤
    private String scanLabelNumber="";//扫描到的标签号
    private UserBean nowUserBean;
    private PlatformLoginBean nowPlatformLoginBean;
    private EmployeesBean nowEmployeesBean;
    private String deviceNumber;
    private  DeviceUnit nowDeviceUnit;
    private String downLoadUrl="";


    public String getDownLoadUrl() {
        return downLoadUrl;
    }

    public DeviceUnit getNowDeviceUnit() {
        return nowDeviceUnit;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public MutableLiveData<Integer> getNowStep() {
        if(nowStep==null){
            nowStep=new MutableLiveData<>();
            nowStep.setValue(0);
        }
        return nowStep;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //用工牌上的number去获取token
    public void token(String number){
        //
        scanLabelNumber=number;
        sendMessage("loading");
//        Api.getInstance().getProductionOrderList(offset, limit, new BaseListObserver<ArrayList<ProductionOrderDTO>>() {
        Api.getInstance().tokenByNumber(number, new BaseObserver<TokenBean>() {
            @Override
            protected void onHandleSuccess(TokenBean tokenBean) {

                SharedPrefUtil.putTokenBean(tokenBean);
                accountsme();
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                sendMessage("fail");
            }
        });

    }

    private void accountsme() {
//        Api.getInstance().getProductionOrderList(offset, limit, new BaseListObserver<ArrayList<ProductionOrderDTO>>() {
        Api.getInstance().accountMe( new BaseObserver<UserBean>() {
            @Override
            protected void onHandleSuccess(UserBean userBean) {
                        List<UserBean.TenantsBean> tempTenants = userBean.getTenants();
                        if (tempTenants != null && tempTenants.size() > 0) {
                            UserBean.TenantsBean trueTenant = null;
                            ps:
                            for (UserBean.TenantsBean tempTenant : tempTenants) {
                                if (tempTenant.isPrimary()) {
                                    trueTenant = tempTenant;
                                    break ps;
                                }
                            }

                            if (trueTenant != null) {
                                //获取登录权限，必须是护士
                                nowUserBean=userBean;
                                platformLogin( trueTenant.getOpenid());
                            } else {
                                Tips.show("没有主租户信息");
                                sendMessage("success");
                            }
                        } else {
                            Tips.show("没有租户信息");
                            sendMessage("success");
                        }

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                sendMessage("fail");
            }
        });
    }
    private void platformLogin( String openid) {

//        Api.getInstance().getProductionOrderList(offset, limit, new BaseListObserver<ArrayList<ProductionOrderDTO>>() {
        Api.getInstance().platformLogin(openid, new BaseObserver<PlatformLoginBean>() {
            @Override
            protected void onHandleSuccess(PlatformLoginBean platformLoginBean) {

                List<PlatformLoginBean.RolesBean> rolesBeans = platformLoginBean.getRoles();
                boolean isCollect = false;
                boolean isNurse=false;
                if (rolesBeans != null && rolesBeans.size() > 0) {
                    ps:
                    for (PlatformLoginBean.RolesBean rb : rolesBeans) {
                        if (rb.getType().equals("NURSE")) {
                            isNurse = true;
//                                    break ps;
                        }
                    }
//                            for (int i = 0; i < rolesBeans.size(); i++) {
////                                if (rolesBeans.get(i).getType().equals("WMS_ADMINISTRATOR")) {
////                                    isAdmin = true;
////                                }
//                                if (rolesBeans.get(i).getType().equals("WASTE_COLLECTOR")) {
//                                    isCollect = true;
//                                }
//                            }
                }
                if (isNurse) {
                    nowPlatformLoginBean=platformLoginBean;
                    //登录成功了，需要去获取标签信息
//                    labels(scanLabelNumber);
                    SharedPrefUtil.putUserBean(nowUserBean);

                    SharedPrefUtil.putString("organization",nowPlatformLoginBean.getOrganization().getName());
                    //把扫描到的工牌号也记录下来
                    SharedPrefUtil.putString("employeeLabel",scanLabelNumber);
                    sendMessage("toHomeMain");
                } else {
                    Tips.show("角色认证不通过");
                    sendMessage("success");
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                sendMessage("fail");
            }
        });

    }

    private void saveMsg(EmployeesBean employeesBean){
        SharedPrefUtil.putUserBean(nowUserBean);

        SharedPrefUtil.putRoleType(new Gson().toJson(nowPlatformLoginBean.getRoles()));
        SharedPrefUtil.putString("organization",nowPlatformLoginBean.getOrganization().getName());
        SharedPrefUtil.putString("idCard",nowPlatformLoginBean.getEmployee().getIdCard());
        SharedPrefUtil.putString("EmployeeIdCard",nowPlatformLoginBean.getEmployee().getIdCard());
        SharedPrefUtil.putString("rolesId",nowPlatformLoginBean.getRoles().get(0).getId()+"");

        //把扫描到的工牌号也记录下来
        SharedPrefUtil.putString("employeeLabel",scanLabelNumber);
        //员工信息页记录下来
        SharedPrefUtil.putObjectT(SharedPrefUtil.EMPLOYEE_Bean,employeesBean);
    }

    public void labels(String number){
//        Api.getInstance().getProductionOrderList(offset, limit, new BaseListObserver<ArrayList<ProductionOrderDTO>>() {
        Api.getInstance().labels(number, new BaseObserver<LabelBean>() {
            @Override
            protected void onHandleSuccess(LabelBean labelBean) {
                try {
                    if (labelBean.getType().equals("thing") && labelBean.getObject().equals("employee")) {
                        //扫到的是科室
                        employees(labelBean.getData().getNumber());
                    }else{
                        Tips.show("无法识别此二维码");
                        sendMessage("success");
                    }

                } catch (Exception e) {
                    Tips.show("暂不支持该条码");
                    sendMessage("success");

                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                sendMessage("fail");
            }
        });
    }

    private void employees(String number){
//        Api.getInstance().getProductionOrderList(offset, limit, new BaseListObserver<ArrayList<ProductionOrderDTO>>() {
        Api.getInstance().employees(number, new BaseObserver<EmployeesBean>() {
            @Override
            protected void onHandleSuccess(EmployeesBean employeesBean) {

                // TODO: 2022/4/25  这里就拿到了员工信息了，接着就要去或者这个机器的唯一码，看看是否符合这个科室
//                nowEmployeesBean=employeesBean;
//                getDeviceMsg(employeesBean);
                SharedPrefUtil.putUserBean(nowUserBean);
                sendMessage("toHomeMain");
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                sendMessage("fail");
            }
        });
    }


    public void update(){
        Api.getInstance().update( new BaseObserver<UpdateBean>() {
            @Override
            protected void onHandleSuccess(UpdateBean updateBean) {
                if (Utils.getAppVersionCode(getContext())<updateBean.getUpdateVersion()) {
                     downLoadUrl = updateBean.getAddress();
                    if (!TextUtils.isEmpty(downLoadUrl)) {
                        sendMessage("showAskPop");
                    }
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                sendMessage("fail");
            }
        });

    }

//    private void getDeviceMsg(EmployeesBean employeesBean) {
////        Api.getInstance().getProductionOrderList(offset, limit, new BaseListObserver<ArrayList<ProductionOrderDTO>>() {
//        Api.getInstance().deviceByNumber(deviceNumber, new BaseObserver<DeviceUnit>() {
//            @Override
//            protected void onHandleSuccess(DeviceUnit deviceUnit) {
//                sendMessage("success");
//                //获取到设备信息，进行和工牌信息比对
//                if(deviceUnit.getDepartmentId().equals(employeesBean.getDepartment().getId())){
//                    //说明是正确的工牌，进入首页
//                    saveMsg(employeesBean);
//                    sendMessage("toHomeMain");
//                }else{
//                    //科室部门不一致
//                    nowDeviceUnit=deviceUnit;
//                    nowStep.setValue(2);
//                }
//
//            }
//
//            @Override
//            public void onError(ExceptionHandle.ResponeThrowable e) {
//                sendMessage("fail");
//            }
//        });
//    }

}
