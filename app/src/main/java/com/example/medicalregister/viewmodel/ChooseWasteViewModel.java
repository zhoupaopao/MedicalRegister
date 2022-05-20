package com.example.medicalregister.viewmodel;

import com.arch.demo.network_api.errorhandler.ExceptionHandle;
import com.arch.demo.network_api.observer.BaseListObserver;
import com.example.lib.base.MvvmBaseViewModel;
import com.example.lib.utils.SharedPrefUtil;
import com.example.medicalregister.bean.CollectWasteBean;
import com.example.medicalregister.bean.EmployeesBean;
import com.example.medicalregister.bean.LabelBean;
import com.example.medicalregister.bean.WasteInventoryBean;
import com.example.medicalregister.http.Api;

import java.util.ArrayList;

public class ChooseWasteViewModel extends MvvmBaseViewModel {
    private String title="21";
    private ArrayList<WasteInventoryBean>allList=new ArrayList<>();//废物列表
    private WasteInventoryBean nowWasteInventoryBean;//当前的危废类型
    private EmployeesBean nowEmployeesBean= SharedPrefUtil.getObjectT(SharedPrefUtil.EMPLOYEE_Bean);

    public EmployeesBean getNowEmployeesBean() {
        return nowEmployeesBean;
    }

    public WasteInventoryBean getNowWasteInventoryBean() {
        return nowWasteInventoryBean;
    }

    public void getWasteProduceRecord(){
        sendMessage("loading");
//        Api.getInstance().getProductionOrderList(offset, limit, new BaseListObserver<ArrayList<ProductionOrderDTO>>() {
        Api.getInstance().wasteProduceRecord(nowEmployeesBean.getDepartment().getName(), new BaseListObserver<ArrayList<WasteInventoryBean>>() {
            @Override
            protected void onHandleSuccess(ArrayList<WasteInventoryBean> wasteInventoryBeans) {
                sendMessage("success");
                allList.addAll(wasteInventoryBeans);

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                sendMessage("fail");
            }
        });
    }

    //获取危废列表
    public void toWeight(int pos){
        String wasteName="";
        if(pos==0){
            wasteName="感染性废物";
        }else if(pos==1){
            wasteName="损伤性废物";
        }else if(pos==2){
            wasteName="化学性废物";
        }else if(pos==3){
            wasteName="病理性废物";
        }else if(pos==4){
            wasteName="药物性废物";
        }else if(pos==5){
            wasteName="可回收性废物";
        }
        for(WasteInventoryBean collectWasteBean:allList){
            if(collectWasteBean.getWaste().getName().equals(wasteName)){
                nowWasteInventoryBean=collectWasteBean;
                sendMessage("toWeight");
                break;
            }
        }

    }






    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
