package com.example.medicalregister.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.medicalregister.R;
import com.example.medicalregister.intface.OnTextClickListener;

public class WidgetSubmitView extends LinearLayout {
//    private Boolean hasCorner;//有没有圆角
    private int textSize;
    private View view;
    private TextView tv_click;
    private String str_text;
    private Drawable background;
    private int textcolor;

    public WidgetSubmitView(Context context) {
        this(context, null);
    }

    public WidgetSubmitView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WidgetSubmitView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.widget_layout_submit, this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WidgetSubmitView, defStyleAttr, 0);
        str_text=array.getString(R.styleable.WidgetSubmitView_wsv_text);
        background= array.getDrawable(R.styleable.WidgetSubmitView_wsv_background);
        textSize = array.getDimensionPixelSize(R.styleable.WidgetSubmitView_wsv_textsize, getResources().getDimensionPixelOffset(R.dimen.submit_size));
        textcolor=array.getColor(R.styleable.WidgetSubmitView_wsv_textcolor,getResources().getColor(R.color.white));
//        hasCorner=array.getBoolean(R.styleable.WidgetSubmitView_wsv_hascorner,false);
        array.recycle();
        initView();
    }

    private OnTextClickListener onTextClickListener;

    public void setOnTextClickListener(OnTextClickListener onTextClickListener) {
        this.onTextClickListener = onTextClickListener;
    }
    private void initView() {
        tv_click = view.findViewById(R.id.tv_click);
        if (str_text != null) {
            tv_click.setText(str_text);
        }
        tv_click.setTextSize(textSize);
        if(background!=null){
            tv_click.setBackground(background);
        }
//        if(hasCorner){
//            tv_click.setBackgroundResource(R.drawable.bg_main_corner5);
//        }else{
//            tv_click.setBackgroundResource(R.color.main_color);
//        }
        tv_click.setTextColor(textcolor);
        tv_click.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onTextClickListener!=null){
                    onTextClickListener.onTextClick(0);
                }
            }
        });
    }
}
