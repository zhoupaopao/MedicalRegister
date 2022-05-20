package com.example.medicalregister.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;


import com.example.medicalregister.R;
import com.example.medicalregister.intface.OnSearchClickListener;

import static android.content.Context.INPUT_METHOD_SERVICE;

//搜索框
public class WidgetSearchEditView extends LinearLayout {
    private OnSearchClickListener onSearchClickListener;
    private View view;
    private EditText et_search;
    private String hint;

    public WidgetSearchEditView(Context context) {
        this(context,null);
    }

    public WidgetSearchEditView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public void setOnSearchClickListener(OnSearchClickListener onSearchClickListener) {
        this.onSearchClickListener = onSearchClickListener;
    }

    public WidgetSearchEditView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view= LayoutInflater.from(context).inflate(R.layout.widget_search_edittext, this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WidgetSearchEditView, defStyleAttr, 0);
        hint=array.getString(R.styleable.WidgetSearchEditView_wsev_hint);
        array.recycle();
        initView(context);
        initListener(context);
    }

    private void initView(Context context) {
        et_search=view.findViewById(R.id.et_search);
        if(hint!=null){
            et_search.setHint(hint);
        }
    }

    public String getEtString(){
        return et_search.getText().toString().trim();
    }

    private void initListener(Context context) {
        et_search.setOnKeyListener((v, keyCode, event) -> {

            if (keyCode == KeyEvent.KEYCODE_ENTER&&event.getAction()==KeyEvent.ACTION_DOWN) {
                // 先隐藏键盘
                ((InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(view
                                .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                if(onSearchClickListener!=null){
                    onSearchClickListener.onBtSearch(getEtString());
                }
            }
            return false;
        });
    }
}
