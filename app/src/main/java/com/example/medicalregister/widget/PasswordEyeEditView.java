package com.example.medicalregister.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.adapters.ListenerUtil;

import com.example.medicalregister.R;


public class PasswordEyeEditView extends LinearLayout {
    private String name;
    private String hint;
    private String text;
    private ImageView iv_seeorhide;
    private EditText et_pwd;
    private View view;
    private boolean isshow = false;//隐藏密码

    public PasswordEyeEditView(Context context) {
        this(context, null);
    }

    public PasswordEyeEditView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

//    @BindingAdapter({"ptev_text"})
//    public static void setptev_text(final PwdTextEditView view, String text) {
//        if (text == null) {
//            view.et_pwd.setText("");
//        } else {
//            view.et_pwd.setText(text);
//        }
//    }

    // 双向绑定 输入框内容
    @BindingAdapter(value = "peev_text")
    public static void setcustomContent(PasswordEyeEditView view, String values) {
        values = values == null ? "" : values;
        if (values.trim().equals(view.getContent().trim())) {
//防止死循环
            return;
        } else {
            view.setContent(values);
        }
    }
//反向绑定(不需要加app:)
    @InverseBindingAdapter(attribute = "peev_text", event = "customContentAttrChanged")
    public static String getcustomContent(PasswordEyeEditView view) {
        return view.getContent().trim();
    }


    @BindingAdapter(value = {"customContentAttrChanged"}, requireAll = false)
    public static void ContentAttrChange(PasswordEyeEditView input, InverseBindingListener listener) {
        if (listener == null) {
            Log.e("test", "InverseBindingListener为空!");
        } else {
            Log.d("test", "setRefreshingAttrChanged");
            SimpleTextWatcher newTextWatch = new SimpleTextWatcher() {
                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                    listener.onChange();
                }
            };
            SimpleTextWatcher oldTextWatch = ListenerUtil.trackListener(input, newTextWatch, R.id.textWatcher);
            if (oldTextWatch != null) {
                input.removeTextWatch(oldTextWatch);
            }
            input.addTextWatch(newTextWatch);
        }
    }

    private void addTextWatch(TextWatcher textWatcher) {
        et_pwd.addTextChangedListener(textWatcher);
    }

    private void removeTextWatch(TextWatcher textWatcher) {
        et_pwd.removeTextChangedListener(textWatcher);
    }

    public PasswordEyeEditView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.assembly_layout_edit_pwd_eye, this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PasswordEyeEditView, defStyleAttr, 0);
        name = array.getString(R.styleable.PasswordEyeEditView_peev_text);
        hint = array.getString(R.styleable.PasswordEyeEditView_peev_hint);
//        text=array.getString(R.styleable.PwdTextEditView_peev_text);
        initView();
        initListener();


    }


    private void initView() {
        iv_seeorhide = view.findViewById(R.id.iv2);
//        iv_cancel = view.findViewById(R.id.iv_cancel);
        et_pwd = view.findViewById(R.id.et_password);
        if (hint == null) {
            et_pwd.setHint("请输入密码");
        } else {
            et_pwd.setHint(hint);
        }


    }

    private void initListener() {

        iv_seeorhide.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isshow) {
                    //是展示密码，需要隐藏
                    //从密码可见模式变为密码不可见模式
                    et_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    iv_seeorhide.setImageResource(R.mipmap.open_eyes);
                    isshow = false;
                } else {
                    //从密码不可见模式变为密码可见模式
                    et_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    iv_seeorhide.setImageResource(R.mipmap.close_eyes);
                    isshow = true;
                }


            }
        });
        et_pwd.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && !et_pwd.getText().toString().trim().equals("")) {
                    seeAll();
                } else {
                    hideAll();
                }
            }
        });
        et_pwd.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                if (text.equals("")) {
                    hideAll();
                } else {
                    seeAll();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void hideAll() {
        iv_seeorhide.setVisibility(GONE);
    }

    private void seeAll() {
        iv_seeorhide.setVisibility(VISIBLE);
    }

    public String getName() {
        String str_name = et_pwd.getText().toString().trim();
        return str_name;
    }

    public EditText getEt_pwd() {
        return et_pwd;
    }

    public String getContent() {
        return et_pwd.getText().toString();
    }

    public void setContent(String text) {
        et_pwd.setText(text);
    }
}
