package com.example.medicalregister.common;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.medicalregister.R;
import com.example.medicalregister.intface.OnCheckClickListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomPadListAadpter extends BaseQuickAdapter<CustomListItem, BaseMyViewHolder> {
    /**
     * 是否允许修改
     */
    private boolean isChange;
    private List<CustomListItem> formBeans = new ArrayList<>();
    private int visible=1;//gone


    public CustomPadListAadpter(int layoutResId, @Nullable List<CustomListItem> data) {
        super(layoutResId, data);
        this.formBeans=data;

    }

//    public CustomListAadpter(int layoutResId) {
//        super(layoutResId);
//    }


    public void setVisible(int visible) {
        this.visible = visible;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(@NotNull BaseMyViewHolder baseMyViewHolder, CustomListItem customListItem) {
//        helper.getLayoutPosition()获取当前下标
        baseMyViewHolder.setText(R.id.item_id,customListItem.getId());
        baseMyViewHolder.setText(R.id.item_number,customListItem.getNumber());
        baseMyViewHolder.setText(R.id.item_name,customListItem.getName());
        baseMyViewHolder.setText(R.id.item_title,customListItem.getTitle());
        baseMyViewHolder.setText(R.id.item_message,customListItem.getMessage());
        baseMyViewHolder.setVisible(R.id.checkbox,visible);
        baseMyViewHolder.setCheck(R.id.checkbox,customListItem.isChecked());
//        Log.i("onClick:out ", ((CheckBox)baseMyViewHolder.getView(R.id.checkbox)).isChecked()+"|"+baseMyViewHolder.getLayoutPosition());
        ((CheckBox)baseMyViewHolder.getView(R.id.checkbox)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onClick: ", ((CheckBox)baseMyViewHolder.getView(R.id.checkbox)).isChecked()+"|"+baseMyViewHolder.getLayoutPosition());
                customListItem.setChecked(((CheckBox)baseMyViewHolder.getView(R.id.checkbox)).isChecked());

                if(onCheckClickListener!=null){
                    onCheckClickListener.onCheckClick(baseMyViewHolder.getLayoutPosition(),((CheckBox)baseMyViewHolder.getView(R.id.checkbox)).isChecked());
                }
            }
        });
        ((Button)baseMyViewHolder.getView(R.id.btnDelete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formBeans.remove(baseMyViewHolder.getLayoutPosition());
                notifyItemRemoved(baseMyViewHolder.getLayoutPosition());
                if (baseMyViewHolder.getLayoutPosition() != formBeans.size()) {
                    notifyItemRangeChanged(baseMyViewHolder.getLayoutPosition(), formBeans.size() - baseMyViewHolder.getLayoutPosition());
                }
            }
        });

    }
    private OnCheckClickListener onCheckClickListener;

    public void setOnCheckClickListener(OnCheckClickListener onCheckClickListener) {
        this.onCheckClickListener = onCheckClickListener;
    }
}
