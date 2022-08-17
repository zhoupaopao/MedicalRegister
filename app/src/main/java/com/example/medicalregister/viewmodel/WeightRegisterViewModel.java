package com.example.medicalregister.viewmodel;

import android.widget.Toast;

import com.arch.demo.network_api.errorhandler.ExceptionHandle;
import com.arch.demo.network_api.observer.BaseObserver;
import com.example.lib.base.MvvmBaseViewModel;
import com.example.lib.utils.SharedPrefUtil;
import com.example.lib.utils.TimeUtil;
import com.example.lib.utils.Tips;
import com.example.medicalregister.AppAplication;
import com.example.medicalregister.bean.AcheveReturnLabelBean;
import com.example.medicalregister.bean.AchieveLabelBean;
import com.example.medicalregister.bean.ApiStringBean;
import com.example.medicalregister.bean.DeviceUnit;
import com.example.medicalregister.bean.EmployeesBean;
import com.example.medicalregister.bean.LabelBean;
import com.example.medicalregister.bean.SelectedRegistBean;
import com.example.medicalregister.bean.WasteInventoryBean;
import com.example.medicalregister.http.Api;
import com.google.gson.Gson;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class WeightRegisterViewModel extends MvvmBaseViewModel {
    private EmployeesBean nowEmployeesBean= SharedPrefUtil.getObjectT(SharedPrefUtil.EMPLOYEE_Bean);
    private WasteInventoryBean nowWasteInventoryBean;
    private String WasteName;
    private LabelBean printLabelBean;

    public LabelBean getPrintLabelBean() {
        return printLabelBean;
    }

    public WasteInventoryBean getNowWasteInventoryBean() {
        return nowWasteInventoryBean;
    }

    public void setNowWasteInventoryBean(WasteInventoryBean nowWasteInventoryBean) {
        this.nowWasteInventoryBean = nowWasteInventoryBean;
    }

    public EmployeesBean getNowEmployeesBean() {
        return nowEmployeesBean;
    }
    public void achieveLabel(String weight){
        sendMessage("loading");
        AchieveLabelBean.Organization organization = new AchieveLabelBean.Organization(SharedPrefUtil.getOpenid());
//        String id,String number,String name,String type,String code,String department,String organization


        AchieveLabelBean.SkuBeanMsg skuBeanMsg = new AchieveLabelBean.SkuBeanMsg(nowWasteInventoryBean.getWaste().getId()+"", nowWasteInventoryBean.getWaste().getNumber(), nowWasteInventoryBean.getWaste().getName(), nowWasteInventoryBean.getWaste().getType(), "", nowEmployeesBean.getDepartment().getName(), SharedPrefUtil.getTenantName(),weight,nowEmployeesBean.getName(),nowEmployeesBean.getIdCard(),nowEmployeesBean.getName());
        AchieveLabelBean achieveLabelBean = new AchieveLabelBean("thing", "medicalWaste", "created", null, null, organization, skuBeanMsg);
       RequestBody requestBody= RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(achieveLabelBean));
        Api.getInstance().achieveLabel(requestBody, new BaseObserver<AcheveReturnLabelBean>() {
            @Override
            protected void onHandleSuccess(AcheveReturnLabelBean acheveReturnLabelBean) {
                sendMessage("success");
                //提交登记
                submitRegister( acheveReturnLabelBean,weight);

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                sendMessage("fail");
            }
        });
    }

    private void submitRegister(AcheveReturnLabelBean acheveReturnLabelBean,String weight) {
        sendMessage("loading");
        ArrayList<SelectedRegistBean> mList=new ArrayList<>();
        SelectedRegistBean selectedRegistBean=new SelectedRegistBean();
        selectedRegistBean.setSource(nowEmployeesBean.getDepartment().getName());
        selectedRegistBean.setWeight(Double.parseDouble(weight));
        selectedRegistBean.setWom("KG");
        selectedRegistBean.setQuantity(1);
        SelectedRegistBean.weasteBean weasteBean=new SelectedRegistBean.weasteBean(nowWasteInventoryBean.getWaste().getId()+"",nowWasteInventoryBean.getWaste().getName(),nowWasteInventoryBean.getWaste().getNumber());
        selectedRegistBean.setWaste(weasteBean);
        SelectedRegistBean.ChildRegistBean childRegistBean=new SelectedRegistBean.ChildRegistBean();
        childRegistBean.setLabel(acheveReturnLabelBean.getLabel().getNumber());
        childRegistBean.setUom("KG");
        childRegistBean.setQuantity(Double.parseDouble(weight));
        ArrayList<SelectedRegistBean.ChildRegistBean>childRegistBeans=new ArrayList<>();
        childRegistBeans.add(childRegistBean);
        selectedRegistBean.setDetails(childRegistBeans);
        selectedRegistBean.setCreatedBy(nowEmployeesBean.getName());
        mList.add(selectedRegistBean);
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(mList));
        Api.getInstance().batchSave(requestBody, new BaseObserver<ApiStringBean>() {
            @Override
            protected void onHandleSuccess(ApiStringBean apiStringBean) {

                //执行打印
                if(apiStringBean.getStatus()==201){
                    Tips.show("登记完成，请粘贴标签");
                    AppAplication.getSound().playShortResource("登记完成，请粘贴标签");
                    //获取这个标签信息
                    getLabelBean(acheveReturnLabelBean.getLabel().getNumber());

                }else{
                    Tips.show("登记异常，请重试");
                    sendMessage("success");
                }

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                sendMessage("fail");
            }
        });

    }

    //获取label用于打印
    private void getLabelBean(String number) {
        Api.getInstance().labelByNumber(number, new BaseObserver<LabelBean>() {
            @Override
            protected void onHandleSuccess(LabelBean labelBean) {
                sendMessage("success");
                printLabelBean=labelBean;
                sendMessage("toPrint");
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }
        });
    }
}
