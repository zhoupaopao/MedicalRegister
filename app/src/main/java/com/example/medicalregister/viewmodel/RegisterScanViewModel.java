package com.example.medicalregister.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.arch.demo.network_api.errorhandler.ExceptionHandle;
import com.arch.demo.network_api.observer.BaseObserver;
import com.example.lib.base.MvvmBaseViewModel;
import com.example.lib.utils.SharedPrefUtil;
import com.example.lib.utils.Tips;
import com.example.medicalregister.bean.EmployeesBean;
import com.example.medicalregister.bean.LabelBean;
import com.example.medicalregister.bean.PlatformLoginBean;
import com.example.medicalregister.http.Api;

import java.util.ArrayList;

public class RegisterScanViewModel extends MvvmBaseViewModel {
    private MutableLiveData<Integer>nowStep;
    private EmployeesBean.DepartmentBean nowDepartmentBean;

    public EmployeesBean.DepartmentBean getNowDepartmentBean() {
        return nowDepartmentBean;
    }

    public MutableLiveData<Integer> getNowStep() {
        if(nowStep==null){
            nowStep=new MutableLiveData<>();
            nowStep.setValue(0);
        }
        return nowStep;
    }

    //校验扫的标签是不是登录账号
    public void checkLabel(String number){
        //这里直接采用本地校验了
        // TODO: 2022/5/18 需要通过number去获取这个员工是否是护士
//        labels(number);
        labelRole(number);
//        if(SharedPrefUtil.getString("employeeLabel").equals(number)){
//            nowStep.setValue(2);
//        }else{
//            nowStep.setValue(1);
//        }
    }

    public void labelRole(String number){
        sendMessage("loading");
//        Api.getInstance().getProductionOrderList(offset, limit, new BaseListObserver<ArrayList<ProductionOrderDTO>>() {
        Api.getInstance().labelRoles(number, new BaseObserver<ArrayList<PlatformLoginBean.RolesBean>>() {
            @Override
            protected void onHandleSuccess(ArrayList<PlatformLoginBean.RolesBean> rolesBeans) {
                sendMessage("success");
                    boolean isNurse=false;
                    if (rolesBeans != null && rolesBeans.size() > 0) {
                        for (PlatformLoginBean.RolesBean rb : rolesBeans) {
                            if (rb.getType().equals("NURSE")) {
                                isNurse = true;
                                break;
                            }
                        }
                        if(isNurse){
                            //有权限进入

                            labels(number);
                        }else{
                            Tips.show("该工牌不符合要求");
                        }
                    }else{
                        Tips.show("该工牌不符合要求");
                    }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                sendMessage("fail");
            }
        });
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

        sendMessage("loading");
//        Api.getInstance().getProductionOrderList(offset, limit, new BaseListObserver<ArrayList<ProductionOrderDTO>>() {
        Api.getInstance().employees(number, new BaseObserver<EmployeesBean>() {
            @Override
            protected void onHandleSuccess(EmployeesBean employeesBean) {
                sendMessage("success");
                SharedPrefUtil.putObjectT(SharedPrefUtil.EMPLOYEE_Bean,employeesBean);
                nowStep.setValue(2);
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                sendMessage("fail");
            }
        });
    }
}
