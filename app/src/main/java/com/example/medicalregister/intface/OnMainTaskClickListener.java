package com.example.medicalregister.intface;

public interface OnMainTaskClickListener {
    //参数（父组件，当前单击的View,单击的View的位置，数据）
    void onContentClick(int position);
    void onTextClick(int position);
}
