package com.example.medicalregister.common;

import android.util.Log;

import com.example.lib.base.IBaseView;
import com.example.lib.base.MvvmBaseViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CustomPadListViewModel extends MvvmBaseViewModel<CustomPadListViewModel.CallBack> {
    private ArrayList<CustomListItem> arrayList = new ArrayList<>();
    private ArrayList<CustomTopBean> arrayList_top = new ArrayList<>();
    private int nowClick = 0;
    private int num = 1;
    private int limit = 20;
    private ArrayList<Integer> posList = new ArrayList<>();
//    private String []headUrls={
//            "https://dss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3681880960,455182084&fm=26&gp=0.jpg",
//            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2709866189,4129601854&fm=26&gp=0.jpg",
//            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1844318833,114428117&fm=26&gp=0.jpg",
//            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3304919436,1392532584&fm=26&gp=0.jpg"
//    };


    public ArrayList<Integer> getPosList() {
        return posList;
    }


    public void addOrRemove(int pos, boolean check) {

        if (check) {
//            //选中事件
            //直接放入，最后删除或者修改的时候再统一排序
            posList.add(pos);

//            if(posList.size()==0){
//                posList.add(pos);
//            }else{
//                for(int i=0;i<posList.size();i++){
//                    int itempos=posList.get(i);
//                    if(itempos>pos){
//                        posList.add(i,pos);
//                        break;
//                    }
//                    if(i==posList.size()-1){
//                        //最后一个了，说明是最大的
//                        posList.add(pos);
//                    }
//                }
//            }
        } else {
            //取消事件
            int itempos = posList.indexOf(pos);
            posList.remove(itempos);
        }
//        posList.add(pos);
    }

    public void moveSelect(){
        Collections.sort(posList, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        Log.i("onClick11: ", posList.toString());
        for(int i=0;i<posList.size();i++){
            arrayList.remove(posList.get(i)-i);
            if(i==posList.size()-1){
                break;
            }
        }
        posList.clear();
    }

    //    public void remove(int pos){
//        posList.remove(pos);
//    }
    public void clear() {
        posList.clear();
    }



    public ArrayList<CustomListItem> getArrayList() {
        return arrayList;
    }

    public int getNowClick() {
        return nowClick;
    }

    public void setNowClick(int nowClick) {
        this.nowClick = nowClick;
    }

    public ArrayList<CustomTopBean> getArrayList_top() {
        return arrayList_top;
    }
    //    public void getMessage(){
//        for(int i=0;i<headUrls.length;i++){
//            Student student=new Student(headUrls[i],"小命"+i,"男");
//            students.add(student);
//        }
//        //更新列表可以加上某个固定的参数，数据变化通知更新
//        getPageView().listAdapter();
//
//    }

    public void showDialog() {
        showLoading(true);
    }
//    public void addList(){
//        getMessage();
//    }

    public interface CallBack extends IBaseView {
        void listAdapter();

        void topAdapter();
    }

    public void addTopList() {
        arrayList_top.add(new CustomTopBean("销量排名"));
        arrayList_top.add(new CustomTopBean("产量排名"));
        arrayList_top.add(new CustomTopBean("营业额"));
        arrayList_top.add(new CustomTopBean("品种"));
        arrayList_top.add(new CustomTopBean("销售质量"));
        arrayList_top.add(new CustomTopBean("其他"));
        arrayList_top.add(new CustomTopBean("产量排名"));
        arrayList_top.add(new CustomTopBean("营业额"));
        arrayList_top.add(new CustomTopBean("品种"));
        arrayList_top.add(new CustomTopBean("销售质量"));
        arrayList_top.add(new CustomTopBean("其他"));
        getPageView().topAdapter();

    }

    public void addList() {
        for (int i = num; i < limit; i++) {
            arrayList.add(new CustomListItem("id" + i, "number" + i, "name" + i, "title" + i, "message" + i));
        }
        num = num + limit;
        getPageView().listAdapter();
    }
}
