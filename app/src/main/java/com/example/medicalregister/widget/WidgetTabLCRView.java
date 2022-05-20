package com.example.medicalregister.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.medicalregister.R;

/**
 * 列表顶部的tab，有左中右三种类型
 */
public class WidgetTabLCRView extends LinearLayout {
    private Boolean checked;//是否选中
    private View view;
    private TextView tv_name;
    private String str_name;
    private String str_type;//是左中右哪一种类型 1：左，2中，3右

    public WidgetTabLCRView(Context context) {
        this(context, null);
    }

    public WidgetTabLCRView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WidgetTabLCRView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.widget_layout_tab_lcr, this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WidgetTabLCRView, defStyleAttr, 0);
        str_name=array.getString(R.styleable.WidgetTabLCRView_wtlv_name);
        checked=array.getBoolean(R.styleable.WidgetTabLCRView_wtlv_check,false);
        str_type=array.getString(R.styleable.WidgetTabLCRView_wtlv_type);
        array.recycle();
        initView();
    }

    private void initView() {
        tv_name = view.findViewById(R.id.tv_name);
        if (str_name != null) {
            tv_name.setText(str_name);
        }
        setCheckChange();
    }

    private void setCheckChange() {
        if(checked){
            tv_name.setTextColor(getResources().getColor(R.color.white));
            if(str_type!=null){
                switch (str_type){
                    case "1":
                        tv_name.setBackgroundResource(R.drawable.bg_left_0d4e91_corner2_checked);
                        break;
                    case "2":
                        tv_name.setBackgroundResource(R.drawable.bg_center_0d4e91_corner2_checked);
                        break;
                    case "3":
                        tv_name.setBackgroundResource(R.drawable.bg_right_0d4e91_corner2_checked);
                        break;
                }
            }
        }else{
            tv_name.setTextColor(getResources().getColor(R.color.color_0D4E91));
            if(str_type!=null){
                switch (str_type){
                    case "1":
                        tv_name.setBackgroundResource(R.drawable.bg_left_0d4e91_corner2_unchecked);
                        break;
                    case "2":
                        tv_name.setBackgroundResource(R.drawable.bg_center_0d4e91_corner2_unchecked);
                        break;
                    case "3":
                        tv_name.setBackgroundResource(R.drawable.bg_right_0d4e91_corner2_unchecked);
                        break;
                }
            }
        }
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
        setCheckChange();
    }
}
