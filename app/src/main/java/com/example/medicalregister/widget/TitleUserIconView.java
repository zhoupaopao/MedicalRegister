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
import com.example.medicalregister.intface.OnTextClickListener;


public class TitleUserIconView extends LinearLayout {

    private View view;
    private LinearLayout llDrawer;
    private TextView tv_username;
    private String name;

    public TitleUserIconView(Context context) {
        this(context, null);
    }

    public TitleUserIconView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public TitleUserIconView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.include_title_usericon, this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TitleUserIconView, defStyleAttr, 0);
        name = array.getString(R.styleable.TitleUserIconView_tuiv_name);
//        text=array.getString(R.styleable.PwdTextEditView_peev_text);
        initView();
        initListener();


    }


    private void initView() {
        llDrawer = view.findViewById(R.id.llDrawer);
//        iv_cancel = view.findViewById(R.id.iv_cancel);
        tv_username = view.findViewById(R.id.tv_username);
        tv_username.setText(name);


    }
    private OnTextClickListener onTextClickListener;

    public void setOnTextClickListener(OnTextClickListener onTextClickListener) {
        this.onTextClickListener = onTextClickListener;
    }

    private void initListener() {
        llDrawer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onTextClickListener!=null){
                    onTextClickListener.onTextClick(0);
                }
            }
        });

    }

    public void setName(String name) {
        tv_username.setText(name);
    }
}
