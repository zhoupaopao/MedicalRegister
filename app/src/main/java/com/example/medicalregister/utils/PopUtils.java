package com.example.medicalregister.utils;

import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.lib.base.BaseApplication;
import com.example.medicalregister.R;
import com.example.medicalregister.intface.OnAskClickListener;
import com.example.medicalregister.intface.OnPopClickListener;


public class PopUtils {
    public static PopupWindow showAsk(View view, String text, OnPopClickListener onPopClickListener){
        View viewPop = LayoutInflater.from(BaseApplication.getAppContext()).inflate(
                R.layout.popupwindow_layout_ask, null);
        PopupWindow mPopupWindowNickname = new PopupWindow(viewPop,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        mPopupWindowNickname.setBackgroundDrawable(new BitmapDrawable());
        int[] location = new int[2];
        int x = location[0];
        int y = location[1];
        TextView tv_first=viewPop.findViewById(R.id.tv_first);
        TextView tv_second=viewPop.findViewById(R.id.tv_second);
        tv_first.setText(text);
        LinearLayout ll_bg = (LinearLayout) viewPop.findViewById(R.id.ll_bg);
        ll_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPopClickListener.onCancel(mPopupWindowNickname);
//                mPopupWindowNickname.dismiss();
            }
        });
        LinearLayout ll_inner_bg=viewPop.findViewById(R.id.ll_inner_bg);
        ll_inner_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG", "onClick: ");
            }
        });
        TextView txt_cancel = (TextView) viewPop.findViewById(R.id.txt_cancel);
        TextView txt_sure = (TextView) viewPop.findViewById(R.id.txt_sure);


        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPopClickListener.onCancel(mPopupWindowNickname);
//                mPopupWindowNickname.dismiss();
            }
        });
        txt_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //执行删除
                onPopClickListener.onSure(mPopupWindowNickname);
            }
        });
        mPopupWindowNickname.showAtLocation(view, Gravity.NO_GRAVITY, x, y + mPopupWindowNickname.getHeight());
        mPopupWindowNickname.showAsDropDown(view);
        return mPopupWindowNickname;
    }

    public static PopupWindow showAsk(View view, String text, String text1, OnPopClickListener onPopClickListener){
        View viewPop = LayoutInflater.from(BaseApplication.getAppContext()).inflate(
                R.layout.popupwindow_layout_ask, null);
        PopupWindow mPopupWindowNickname = new PopupWindow(viewPop,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        mPopupWindowNickname.setBackgroundDrawable(new BitmapDrawable());
        int[] location = new int[2];
        int x = location[0];
        int y = location[1];
        TextView tv_first=viewPop.findViewById(R.id.tv_first);
        TextView tv_second=viewPop.findViewById(R.id.tv_second);
        tv_first.setText(text);
        tv_second.setText(text1);
        tv_second.setVisibility(View.VISIBLE);
        LinearLayout ll_bg = (LinearLayout) viewPop.findViewById(R.id.ll_bg);
        ll_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPopClickListener.onCancel(mPopupWindowNickname);
//                mPopupWindowNickname.dismiss();
            }
        });
        LinearLayout ll_inner_bg=viewPop.findViewById(R.id.ll_inner_bg);
        ll_inner_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG", "onClick: ");
            }
        });
        TextView txt_cancel = (TextView) viewPop.findViewById(R.id.txt_cancel);
        TextView txt_sure = (TextView) viewPop.findViewById(R.id.txt_sure);


        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPopClickListener.onCancel(mPopupWindowNickname);
//                mPopupWindowNickname.dismiss();
            }
        });
        txt_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //执行删除
                onPopClickListener.onSure(mPopupWindowNickname);
            }
        });
        mPopupWindowNickname.showAtLocation(view, Gravity.NO_GRAVITY, x, y + mPopupWindowNickname.getHeight());
        mPopupWindowNickname.showAsDropDown(view);
        return mPopupWindowNickname;
    }
    public static PopupWindow showTip(View view, String text){
        View viewPop = LayoutInflater.from(BaseApplication.getAppContext()).inflate(
                R.layout.popupwindow_layout_ask, null);
        PopupWindow mPopupWindowNickname = new PopupWindow(viewPop,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        mPopupWindowNickname.setBackgroundDrawable(new BitmapDrawable());
        int[] location = new int[2];
        int x = location[0];
        int y = location[1];
        TextView tv_first=viewPop.findViewById(R.id.tv_first);
        TextView tv_second=viewPop.findViewById(R.id.tv_second);
        tv_first.setText(text);
        LinearLayout ll_bg = (LinearLayout) viewPop.findViewById(R.id.ll_bg);
        ll_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onPopClickListener.onCancel(mPopupWindowNickname);
                mPopupWindowNickname.dismiss();
            }
        });
        LinearLayout ll_inner_bg=viewPop.findViewById(R.id.ll_inner_bg);
        ll_inner_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG", "onClick: ");
            }
        });
        TextView txt_cancel = (TextView) viewPop.findViewById(R.id.txt_cancel);
        txt_cancel.setVisibility(View.GONE);
        TextView txt_sure = (TextView) viewPop.findViewById(R.id.txt_sure);


//
        txt_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //执行删除
                mPopupWindowNickname.dismiss();
            }
        });
        mPopupWindowNickname.showAtLocation(view, Gravity.NO_GRAVITY, x, y + mPopupWindowNickname.getHeight());
        mPopupWindowNickname.showAsDropDown(view);
        return mPopupWindowNickname;
    }
    public static PopupWindow showTip(View view, String text, OnAskClickListener onAskClickListener){
        View viewPop = LayoutInflater.from(BaseApplication.getAppContext()).inflate(
                R.layout.popupwindow_layout_ask, null);
        PopupWindow mPopupWindowNickname = new PopupWindow(viewPop,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        mPopupWindowNickname.setBackgroundDrawable(new BitmapDrawable());
        int[] location = new int[2];
        int x = location[0];
        int y = location[1];
        TextView tv_first=viewPop.findViewById(R.id.tv_first);
        TextView tv_second=viewPop.findViewById(R.id.tv_second);
        tv_first.setText(text);
        LinearLayout ll_bg = (LinearLayout) viewPop.findViewById(R.id.ll_bg);
        ll_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onPopClickListener.onCancel(mPopupWindowNickname);
                onAskClickListener.onClick();
                mPopupWindowNickname.dismiss();
            }
        });
        LinearLayout ll_inner_bg=viewPop.findViewById(R.id.ll_inner_bg);
        ll_inner_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG", "onClick: ");
            }
        });
        TextView txt_cancel = (TextView) viewPop.findViewById(R.id.txt_cancel);
        txt_cancel.setVisibility(View.GONE);
        TextView txt_sure = (TextView) viewPop.findViewById(R.id.txt_sure);


//
        txt_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //执行删除
                onAskClickListener.onClick();
                mPopupWindowNickname.dismiss();
            }
        });
        mPopupWindowNickname.showAtLocation(view, Gravity.NO_GRAVITY, x, y + mPopupWindowNickname.getHeight());
        mPopupWindowNickname.showAsDropDown(view);
        return mPopupWindowNickname;
    }
}
