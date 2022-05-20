package com.example.medicalregister.common;

import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.medicalregister.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomTopAadpter extends BaseQuickAdapter<CustomTopBean, BaseMyViewHolder> implements OnItemClickListener {

    private List<CustomListItem> formBeans = new ArrayList<>();
    private MyClick click;

    public CustomTopAadpter(int layoutResId, @Nullable List<CustomTopBean> data) {
        super(layoutResId, data);
        addChildClickViewIds(R.id.tv_item);
    }

//    public CustomListAadpter(int layoutResId) {
//        super(layoutResId);
//    }


    @Override
    protected void convert(@NotNull BaseMyViewHolder baseMyViewHolder, CustomTopBean customListItem) {
//        helper.getLayoutPosition()获取当前下标
        baseMyViewHolder.setText(R.id.tv_item,customListItem.getText());
        if(customListItem.isClicked()){
            baseMyViewHolder.getView(R.id.tv_item).setBackgroundResource(R.drawable.bg_corner_radius_red_15);
        }else{
            baseMyViewHolder.getView(R.id.tv_item).setBackgroundResource(R.drawable.bg_corner_radius_gray_10);
        }

    }

    public void setClick(MyClick click){
        this.click = click;
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

    }

    private interface MyClick{
        void click(View v);
    }


}
