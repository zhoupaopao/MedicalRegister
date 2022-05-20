package com.example.medicalregister.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextWatcher;
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

import com.example.lib.utils.Utils;
import com.example.medicalregister.R;
import com.example.medicalregister.intface.OnTextClickListener;

public class TitleSearchView extends LinearLayout {

    private View view;
    private EditText etTitle;
    private String name;
    private ImageView iv_search;
    private ImageView iv_clean;
    LinearLayout llBack;

    public TitleSearchView(Context context) {
        this(context, null);
    }

    public TitleSearchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public TitleSearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.include_title_search, this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TitleSearchView, defStyleAttr, 0);
        name = array.getString(R.styleable.TitleSearchView_tsv_name);
//        text=array.getString(R.styleable.PwdTextEditView_peev_text);
        initView();
        initListener(context);


    }


    // 双向绑定 输入框内容
    @BindingAdapter(value = "tsv_name")
    public static void setcustomContent(TitleSearchView view, String values) {
        values = values == null ? "" : values;
        if (values.trim().equals(view.getContent().trim())) {
//防止死循环
            return;
        } else {
            view.setContent(values);
        }
    }
    //反向绑定
    @InverseBindingAdapter(attribute = "tsv_name", event = "customContentAttrChanged")
    public static String getcustomContent(TitleSearchView view) {
        return view.getContent().trim();
    }


    @BindingAdapter(value = {"customContentAttrChanged"}, requireAll = false)
    public static void ContentAttrChange(TitleSearchView input, InverseBindingListener listener) {
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
            SimpleTextWatcher oldTextWatch = ListenerUtil.trackListener(input, newTextWatch, com.example.lib.R.id.textWatcher);
            if (oldTextWatch != null) {
                input.removeTextWatch(oldTextWatch);
            }
            input.addTextWatch(newTextWatch);
        }
    }

    private void addTextWatch(TextWatcher textWatcher) {
        etTitle.addTextChangedListener(textWatcher);
    }

    private void removeTextWatch(TextWatcher textWatcher) {
        etTitle.removeTextChangedListener(textWatcher);
    }
    private void initView() {
        iv_clean = view.findViewById(R.id.iv_clean);
        etTitle = view.findViewById(R.id.etTitle);
        iv_search=view.findViewById(R.id.iv_search);
        llBack=view.findViewById(R.id.llBack);


    }

    public EditText getEtTitle() {
        return etTitle;
    }

    private OnTextClickListener onTextClickListener;

    public void setOnTextClickListener(OnTextClickListener onTextClickListener) {
        this.onTextClickListener = onTextClickListener;
    }
    public void setContent(String text) {
        etTitle.setText(text);
    }
    public String getContent() {
        return etTitle.getText().toString();
    }

    private void initListener(Context context) {
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onTextClickListener != null) {
                    onTextClickListener.onTextClick(0);
                }
            }
        });
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.getActivityContext(context).finish();
            }
        });
        iv_clean.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                name
                name="";
                etTitle.setText("");
            }
        });

    }


}
