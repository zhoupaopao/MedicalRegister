package com.example.medicalregister.viewmodel;

import com.arch.demo.network_api.errorhandler.ExceptionHandle;
import com.arch.demo.network_api.observer.BaseListObserver;
import com.example.lib.base.MvvmBaseViewModel;
import com.example.medicalregister.bean.LabelBean;
import com.example.medicalregister.http.Api;
import com.lvrenyang.io.Label;

import java.util.ArrayList;

public class LabelListViewModel extends MvvmBaseViewModel {
    private ArrayList<LabelBean> mList=new ArrayList<>();
    private int offset=0;
    private int limit=20;
    public ArrayList<LabelBean> getmList() {
        return mList;
    }

    //改用入库单列表
    public void getLabelList(Boolean isRefresh){
        if(isRefresh){
            offset=0;
            mList.clear();
        }else{
            offset=offset+limit;
        }
        sendMessage("loading");
//        Api.getInstance().getProductionOrderList(offset, limit, new BaseListObserver<ArrayList<ProductionOrderDTO>>() {
        Api.getInstance().labelMy(offset,limit, new BaseListObserver<ArrayList<LabelBean>>() {
            @Override
            protected void onHandleSuccess(ArrayList<LabelBean> labelBeans) {
                sendMessage("success");
                mList.addAll(labelBeans);
                sendMessage("adapter");
                if(labelBeans.size()<limit){
                    sendMessage("noMoreData");
                }
                if(isRefresh){
                    sendMessage("finishRefresh");
                }else{
                    sendMessage("finishLoadMore");
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                sendMessage("fail");
            }
        });
    }
}
