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


public class WidgetTextLeftRight extends LinearLayout {
    private int textSize;
    private String left_text;
    private String right_text;
    private View view;
    private TextView tv_left;
    private TextView tv_right;
    private int color;
    private int leftColor;
    private boolean bold;
    private Drawable background;
    private LinearLayout ll_item;

    public WidgetTextLeftRight(Context context) {
        this(context, null);
    }

    public WidgetTextLeftRight(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WidgetTextLeftRight(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.widget_layout_text_left_right, this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WidgetTextLeftRight, defStyleAttr, 0);
        textSize = array.getDimensionPixelSize(R.styleable.WidgetTextLeftRight_wtlr_textsize, getResources().getDimensionPixelOffset(R.dimen.common_size));
        left_text = array.getString(R.styleable.WidgetTextLeftRight_wtlr_left);
        right_text = array.getString(R.styleable.WidgetTextLeftRight_wtlr_right);
        color = array.getColor(R.styleable.WidgetTextLeftRight_wtlr_right_color, getResources().getColor(R.color.gray333));
        background= array.getDrawable(R.styleable.WidgetTextLeftRight_wtlr_background);
        leftColor = array.getColor(R.styleable.WidgetTextLeftRight_wtlr_left_color, getResources().getColor(R.color.gray333));
        bold=array.getBoolean(R.styleable.WidgetTextLeftRight_wtlr_bold,false);
        array.recycle();
        initView();
    }

    private void initView() {
        tv_left = view.findViewById(R.id.tv_left);
        tv_right = view.findViewById(R.id.tv_right);
        ll_item=view.findViewById(R.id.ll_item);
        tv_left.setText(left_text);
        tv_right.setTextColor(color);
        tv_left.setTextColor(leftColor);
        if (right_text != null) {
            tv_right.setText(right_text);
        }
            tv_right.getPaint().setFakeBoldText(bold);
        tv_left.getPaint().setFakeBoldText(bold);
        tv_right.setTextSize(textSize);
        tv_left.setTextSize(textSize);
        if(background!=null){
            ll_item.setBackground(background);
        }
    }

    public String getMsg() {
        return right_text;
    }

    public void setRight_text(String right_text) {
        this.right_text = right_text;
        tv_right.setText(right_text);
    }

    public void setLeft_text(String left_text) {
        this.left_text = left_text;
        tv_left.setText(left_text);
    }

    public String getRight_text() {
        return tv_right.getText().toString();
    }

    public void setRight_textGone() {
        tv_right.setVisibility(GONE);
    }

    public void setRight_textVISIBLE() {
        tv_right.setVisibility(VISIBLE);
    }
}
