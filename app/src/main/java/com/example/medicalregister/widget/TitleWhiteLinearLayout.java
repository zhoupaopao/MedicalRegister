package com.example.medicalregister.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.lib.utils.Utils;
import com.example.medicalregister.R;


/**
 * 通用标题栏
 */
public class TitleWhiteLinearLayout extends LinearLayout {
    private View view;
    private String name = "";
    private String edit="";
    private Drawable icon;
    private TextView tv_name;
    private TextView tv_edit;
    private ImageView iv_icon;
    private LinearLayout ll_back;
    public TitleWhiteLinearLayout(Context context) {
        this(context, null);
    }

    public TitleWhiteLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleWhiteLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.assembly_layout_title_white, this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TitleWhiteLinearLayout, defStyleAttr, 0);
        name = array.getString(R.styleable.TitleWhiteLinearLayout_twll_name);
        icon = array.getDrawable(R.styleable.TitleWhiteLinearLayout_twll_icon);
        edit= array.getString(R.styleable.TitleWhiteLinearLayout_twll_edit);
        array.recycle();

        initView(context);
        initListener(context);
    }

    private void initListener(Context context) {
        ll_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.getActivityContext(context).finish();
            }
        });
    }

    public TextView getTv_edit() {
        return tv_edit;
    }

    private void initView(Context context) {
        tv_name=view.findViewById(R.id.tv_name);
        iv_icon=view.findViewById(R.id.iv_detail);
        tv_edit=view.findViewById(R.id.tv_edit);
        ll_back=view.findViewById(R.id.ll_back);
        if(name.equals("")){
            tv_name.setVisibility(GONE);
        }else{
            tv_name.setText(name);
        }
        if(edit==null){
            tv_edit.setVisibility(GONE);
        }else{
            tv_edit.setText(edit);
        }
        if(icon==null){
            iv_icon.setVisibility(GONE);
        }else{
            iv_icon.setImageDrawable(icon);
        }
    }

    public void setName(String name) {
        tv_name.setText(name);
    }
}
