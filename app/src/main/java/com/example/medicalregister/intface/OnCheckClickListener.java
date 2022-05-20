package com.example.medicalregister.intface;

public interface OnCheckClickListener {
    //参数（父组件，当前单击的View,单击的View的位置，数据）
    void onCheckClick(int position,boolean check);
}
