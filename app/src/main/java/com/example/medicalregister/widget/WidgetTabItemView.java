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

public class WidgetTabItemView extends LinearLayout {
    private Boolean checked;//是否选中
    private View view;
    private View bottom_view;
    private TextView tv_name;
    private String str_name;

    public WidgetTabItemView(Context context) {
        this(context, null);
    }

    public WidgetTabItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WidgetTabItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.widget_layout_tab_item, this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WidgetTabItemView, defStyleAttr, 0);
        str_name=array.getString(R.styleable.WidgetTabItemView_wtiv_name);
        checked=array.getBoolean(R.styleable.WidgetTabItemView_wtiv_check,false);
        array.recycle();
        initView();
    }

    private void initView() {
        tv_name = view.findViewById(R.id.tv_name);
        bottom_view=view.findViewById(R.id.bottom_view);
        if (str_name != null) {
            tv_name.setText(str_name);
        }
        setCheckChange();


//        if(hasCorner){
//            tv_click.setBackgroundResource(R.drawable.bg_main_corner5);
//        }else{
//            tv_click.setBackgroundResource(R.color.main_color);
//        }
    }

    private void setCheckChange() {
        if(checked){
            tv_name.setTextColor(getResources().getColor(R.color.gray333));
            bottom_view.setVisibility(VISIBLE);
        }else{
            tv_name.setTextColor(getResources().getColor(R.color.text_gray));
            bottom_view.setVisibility(INVISIBLE);
        }
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
        setCheckChange();
    }
}
