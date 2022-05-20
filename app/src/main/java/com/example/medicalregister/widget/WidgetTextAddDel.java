package com.example.medicalregister.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.medicalregister.R;
import com.example.medicalregister.intface.AddDeleteInterface;


public class WidgetTextAddDel extends LinearLayout {
    AddDeleteInterface addDeleteInterface;
    private View view;
    ImageView iv_delete;
    ImageView iv_add;
    TextView tv_num;
    int maxNum=999;
    int num=1;
    public WidgetTextAddDel(Context context) {
        this(context,null);
    }

    public WidgetTextAddDel(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WidgetTextAddDel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.widget_text_adddel, this);
        initView(context);
        initListener();
    }

    public void setAddDeleteInterface(AddDeleteInterface addDeleteInterface) {
        this.addDeleteInterface = addDeleteInterface;
    }

    private void initView(Context context) {
        iv_add=view.findViewById(R.id.iv_add);
        iv_delete=view.findViewById(R.id.iv_delete);
        tv_num=view.findViewById(R.id.tv_num);
    }

    public ImageView getIv_delete() {
        return iv_delete;
    }



    public ImageView getIv_add() {
        return iv_add;
    }



    public int getEt_num() {
        return num;
    }

    public TextView getTv_num() {
        return tv_num;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }
    public void setNum(int Num) {
        num=Num;
        tv_num.setText(num+"");
    }
    private void initListener() {
        iv_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num<maxNum){
                    num=num+1;
                    tv_num.setText(num+"");
                    if(addDeleteInterface!=null){
                        addDeleteInterface.doAdd();
                    }

                }
            }
        });
        iv_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num!=0){
                    num=num-1;
                    tv_num.setText(num+"");
                    if(addDeleteInterface!=null) {
                        addDeleteInterface.doDel();
                    }
                }
            }
        });
//        et_num.setOnFocusChangeListener(new OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    //失去焦点
//                    if (et_num.getText().toString().equals("")||et_num.getText().toString().equals("0")){
//                        Toast.makeText(view.getContext(),"输入数量不正确",Toast.LENGTH_SHORT).show();
//                        et_num.setFocusable(true);
//                        et_num.setFocusableInTouchMode(true);
//                        et_num.requestFocus();
//                        ((Activity)view.getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//                    }
//                }
//            }
//        });

//        et_num.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                if(!charSequence.toString().trim().equals("")){
//                    num= Integer.parseInt(charSequence.toString().trim());
//                    if(num>maxNum){
//                        num=maxNum;
//                        et_num.setText(num+"");
//                        et_num.setSelection((num+"").length());
//                    }
//                }else{
//                    num=0;
//                }
////                if(charSequence.toString().trim().equals("")||charSequence.toString().trim().equals("0")){
////                    num=1;
////                    et_num.setText(num+"");
////                    et_num.setSelection((num+"").length());
////                }else{
////                    num= Integer.parseInt(charSequence.toString().trim());
////                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
    }
}
