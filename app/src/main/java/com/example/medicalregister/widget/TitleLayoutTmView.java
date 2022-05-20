package com.example.medicalregister.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.example.lib.utils.EmptyUtil;
import com.example.lib.utils.Utils;
import com.example.medicalregister.R;
import com.example.medicalregister.intface.OnBackClickListener;
import com.example.medicalregister.intface.OnTextClickListener;

public class TitleLayoutTmView extends LinearLayout {

    private View view;
    private TextView tvTitle;
    private String name;
    LinearLayout llBack;
    private String right;
    private TextView tvRight;

    public TitleLayoutTmView(Context context) {
        this(context, null);
    }

    public TitleLayoutTmView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearLayout getLlBack() {
        return llBack;
    }

    public TitleLayoutTmView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.include_tm_title_layout, this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TitleLayoutTmView, defStyleAttr, 0);
        name = array.getString(R.styleable.TitleLayoutTmView_tltv_name);
        right=array.getString(R.styleable.TitleLayoutTmView_tltv_right);
//        text=array.getString(R.styleable.PwdTextEditView_peev_text);
        initView();
        initListener(context);


    }



    // 双向绑定 输入框内容
    @BindingAdapter(value = "tltv_title_name")
    public static void setcustomContent(TitleLayoutTmView view, String values) {
        values = values == null ? "" : values;
        view.setName(values);
    }


    private void initView() {
//        iv_cancel = view.findViewById(R.id.iv_cancel);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvTitle.setText(name);
        llBack=view.findViewById(R.id.llBack);
        tvRight=view.findViewById(R.id.tvRight);
        if(!EmptyUtil.isEmpty(right)){
            //没有这个参数
            tvRight.setText(right);
            tvRight.setVisibility(VISIBLE);
        }else{
            tvRight.setVisibility(GONE);
        }

    }
    private OnBackClickListener onBackClickListener;
    private OnTextClickListener onTextClickListener;
    public void setOnTextClickListener(OnTextClickListener onTextClickListener) {
        this.onTextClickListener = onTextClickListener;
    }

    public void setOnBackClickListener(OnBackClickListener onBackClickListener) {
        this.onBackClickListener = onBackClickListener;
    }

    private void initListener(Context context) {

        llBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onBackClickListener!=null){
                    onBackClickListener.onBackClick();
                }else{
                    Utils.getActivityContext(context).finish();
                }
            }
        });
        tvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onTextClickListener != null) {
                    onTextClickListener.onTextClick(0);
                }
            }
        });



    }

    public void setName(String name) {
        tvTitle.setText(name);
    }
    public void setRight(String text){
        if(!EmptyUtil.isEmpty(right)){
            tvRight.setText(text);
            tvRight.setVisibility(VISIBLE);
        }else{
            tvRight.setVisibility(GONE);
        }
    }
}
