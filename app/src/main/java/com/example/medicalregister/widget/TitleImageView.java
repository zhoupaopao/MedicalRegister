package com.example.medicalregister.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.lib.utils.Utils;
import com.example.medicalregister.R;
import com.example.medicalregister.intface.OnTextClickListener;

public class TitleImageView extends LinearLayout {

    private View view;
    private TextView tvTitle;
    private String name;
    private ImageView iv_icon;
    private Integer header;
    private LinearLayout llAdd;
    LinearLayout llBack;

    public TitleImageView(Context context) {
        this(context, null);
    }

    public TitleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public TitleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.include_title_image, this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TitleImageView, defStyleAttr, 0);
        name = array.getString(R.styleable.TitleImageView_tiv_name);
        header=array.getResourceId(R.styleable.TitleImageView_tiv_right_icon,R.mipmap.scanning_white_icon);
//        text=array.getString(R.styleable.PwdTextEditView_peev_text);
        initView();
        initListener(context);


    }


    private void initView() {
//        iv_cancel = view.findViewById(R.id.iv_cancel);
        tvTitle = view.findViewById(R.id.tvTitle);
        llAdd=view.findViewById(R.id.llAdd);
        iv_icon=view.findViewById(R.id.iv_icon);
        tvTitle.setText(name);
            iv_icon.setImageResource(header);
        llBack=view.findViewById(R.id.llBack);


    }

    private OnTextClickListener onTextClickListener;

    public void setOnTextClickListener(OnTextClickListener onTextClickListener) {
        this.onTextClickListener = onTextClickListener;
    }

    private void initListener(Context context) {
        llAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onTextClickListener != null) {
                    onTextClickListener.onTextClick(0);
                }
            }
        });
        llBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.getActivityContext(context).finish();
            }
        });

    }
    public void setImageVisible(Boolean visible) {
        if(visible){
            llAdd.setVisibility(VISIBLE);
        }else{
            llAdd.setVisibility(GONE);
        }

    }

    public void setName(String name) {
        tvTitle.setText(name);
    }

    public void setHeader(Integer header) {
        this.header = header;
    }
}
