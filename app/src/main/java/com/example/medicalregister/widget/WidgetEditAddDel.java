package com.example.medicalregister.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.medicalregister.R;
import com.example.medicalregister.intface.AddDeleteInterface;


public class WidgetEditAddDel extends LinearLayout {
    AddDeleteInterface addDeleteInterface;
    private View view;
    ImageView iv_delete;
    ImageView iv_add;
    EditText et_num;
    int maxNum=999;
    int num=1;
    public WidgetEditAddDel(Context context) {
        this(context,null);
    }

    public WidgetEditAddDel(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WidgetEditAddDel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.widget_edit_adddel, this);
        initView(context);
        initListener();
    }

    public void setAddDeleteInterface(AddDeleteInterface addDeleteInterface) {
        this.addDeleteInterface = addDeleteInterface;
    }

    private void initView(Context context) {
        iv_add=view.findViewById(R.id.iv_add);
        iv_delete=view.findViewById(R.id.iv_delete);
        et_num=view.findViewById(R.id.et_num);
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

    public EditText getEtNum(){
        return et_num;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }
    public void setNum(int Num) {
        num=Num;
        et_num.setText(num+"");
    }
    private void initListener() {
        iv_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num!=maxNum){
                    num=num+1;
                    et_num.setText(num+"");
                    if(addDeleteInterface!=null){
                        addDeleteInterface.doAdd();
                    }

                }
            }
        });
        iv_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num!=1){
                    num=num-1;
                    et_num.setText(num+"");
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
        TextWatcher numWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().equals("")){
                    num= Integer.parseInt(charSequence.toString().trim());
                    if(num>maxNum){
                        num=maxNum;
                        et_num.setText(num+"");
                        et_num.setSelection((num+"").length());
                    }
                }else{
                    num=0;
                }
//内容操作
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        et_num.addTextChangedListener(numWatcher);
        et_num.setTag(numWatcher);
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
