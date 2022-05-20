package com.example.medicalregister.common;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.IdRes;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;


/**
 * Created by lyf on 2018/8/3 16:37
 *
 * @author lyf
 * desc：通用ViwHolder
 */
public class BaseMyViewHolder extends BaseViewHolder {

    public BaseMyViewHolder(View view) {
        super(view);
    }

    public void setText(@IdRes int idRes, String value) {
        ((TextView) getView(idRes)).setText(value);
    }
    public void setVisible(@IdRes int idRes, int visible) {
        if(visible==0){
            getView(idRes).setVisibility(View.VISIBLE);
        }else if(visible==1){
            getView(idRes).setVisibility(View.GONE);
        }else if(visible==2){
            getView(idRes).setVisibility(View.INVISIBLE);
        }
    }
    public void setCheck(@IdRes int idRes, Boolean value) {
        ((CheckBox) getView(idRes)).setChecked(value);
    }
}
