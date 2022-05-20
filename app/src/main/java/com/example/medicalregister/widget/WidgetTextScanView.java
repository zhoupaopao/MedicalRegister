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

import com.example.medicalregister.R;
import com.example.medicalregister.intface.OnTextClickListener;


/**
 * 右侧有个扫描图标的行
 */
public class WidgetTextScanView extends LinearLayout {
    private int textSize;
    private String left_text;
    private String right_text;
    private View view;
    private TextView tv_left;
    private TextView tv_right;
    private int color;
    private int leftColor;
    private Integer icon;
    private ImageView iv_icon;
    private boolean bold;

    public WidgetTextScanView(Context context) {
        this(context, null);
    }

    public WidgetTextScanView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WidgetTextScanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.widget_layout_text_scan, this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WidgetTextScanView, defStyleAttr, 0);
        textSize = array.getDimensionPixelSize(R.styleable.WidgetTextScanView_wtsv_textsize, getResources().getDimensionPixelOffset(R.dimen.widget_scan_size));
        left_text = array.getString(R.styleable.WidgetTextScanView_wtsv_left);
        right_text = array.getString(R.styleable.WidgetTextScanView_wtsv_right);
        color = array.getColor(R.styleable.WidgetTextScanView_wtsv_right_color, getResources().getColor(R.color.bg_89898D));
        leftColor = array.getColor(R.styleable.WidgetTextScanView_wtsv_left_color, getResources().getColor(R.color.black));
        icon=array.getResourceId(R.styleable.WidgetTextScanView_wtsv_right_icon,R.mipmap.icon_scan_black);
        array.recycle();
        initView();
    }

    private void initView() {
        tv_left = view.findViewById(R.id.tv_left);
        tv_right = view.findViewById(R.id.tv_right);
        iv_icon=view.findViewById(R.id.iv_icon);
        tv_left.setText(left_text);
        tv_right.setTextColor(color);
        tv_left.setTextColor(leftColor);
        iv_icon.setImageResource(icon);
        if (right_text != null) {
            tv_right.setText(right_text);
        }
            tv_right.getPaint().setFakeBoldText(bold);
        tv_left.getPaint().setFakeBoldText(bold);
        tv_right.setTextSize(textSize);
        tv_left.setTextSize(textSize);
    }

    private OnTextClickListener onTextClickListener;

    public void setOnTextClickListener(OnTextClickListener onTextClickListener) {
        this.onTextClickListener = onTextClickListener;
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

    public void setColor(int rightColor){
        this.color = rightColor;
        tv_right.setTextColor(getResources().getColor(rightColor));
    }
}
