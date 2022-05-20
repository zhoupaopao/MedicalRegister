package com.example.medicalregister.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.adapters.ListenerUtil;

import com.example.medicalregister.R;
import com.example.medicalregister.intface.OnSearchClickListener;

import static android.content.Context.INPUT_METHOD_SERVICE;

//搜索框
public class WidgetQsSearchEditView extends LinearLayout {
    private OnSearchClickListener onSearchClickListener;
    private View view;
    private EditText et_search;
    private ImageView iv_cancel;
    private TextView tv_search;
    private String hint;

    public WidgetQsSearchEditView(Context context) {
        this(context,null);
    }

    public WidgetQsSearchEditView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public void setOnSearchClickListener(OnSearchClickListener onSearchClickListener) {
        this.onSearchClickListener = onSearchClickListener;
    }

    public WidgetQsSearchEditView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view= LayoutInflater.from(context).inflate(R.layout.widget_search_edittext_qs, this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WidgetQsSearchEditView, defStyleAttr, 0);
        hint=array.getString(R.styleable.WidgetQsSearchEditView_wqsev_hint);
        array.recycle();
        initView(context);
        initListener(context);
    }

    private void initView(Context context) {
        et_search=view.findViewById(R.id.et_search);
        tv_search=view.findViewById(R.id.tv_search);
        iv_cancel=view.findViewById(R.id.iv_cancel);
        if(hint!=null){
            et_search.setHint(hint);
        }
    }

    // 双向绑定 输入框内容
    @BindingAdapter(value = "wqsev_text")
    public static void setcustomContent(WidgetQsSearchEditView view, String values) {
        values = values == null ? "" : values;
        if (values.trim().equals(view.getContent().trim())) {
//防止死循环
            return;
        } else {
            view.setContent(values);
        }
    }
    //反向绑定(不需要加app:)
    @InverseBindingAdapter(attribute = "wqsev_text", event = "customContentAttrChanged")
    public static String getcustomContent(WidgetQsSearchEditView view) {
        return view.getContent().trim();
    }


    @BindingAdapter(value = {"customContentAttrChanged"}, requireAll = false)
    public static void ContentAttrChange(WidgetQsSearchEditView input, InverseBindingListener listener) {
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
        et_search.addTextChangedListener(textWatcher);
    }

    private void removeTextWatch(TextWatcher textWatcher) {
        et_search.removeTextChangedListener(textWatcher);
    }

    public String getEtString(){
        return et_search.getText().toString().trim();
    }

    private void initListener(Context context) {
        tv_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onSearchClickListener!=null){
                    onSearchClickListener.onBtSearch(getEtString());
                }
            }
        });
        iv_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                et_search.setText("");
                if(onSearchClickListener!=null){
                    onSearchClickListener.onBtSearch(getEtString());
                }
            }
        });
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
    public String getContent() {
        return et_search.getText().toString();
    }

    public void setContent(String text) {
        et_search.setText(text);
    }
}
