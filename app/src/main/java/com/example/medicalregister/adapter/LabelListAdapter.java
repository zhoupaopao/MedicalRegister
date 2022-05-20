package com.example.medicalregister.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lib.utils.TimeUtil;
import com.example.medicalregister.R;
import com.example.medicalregister.bean.LabelBean;
import com.example.medicalregister.intface.OnTextClickListener;
import com.example.medicalregister.utils.StringUtil;

import java.util.List;

public class LabelListAdapter extends RecyclerView.Adapter<LabelListAdapter.ViewHolder>{
    List<LabelBean> data;
    private Context context;

    public LabelListAdapter(Context context, List<LabelBean> data) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_label_list,parent,false);
        return new ViewHolder(view);
    }

    private OnTextClickListener onTextClickListener;




    public void setOnTextClickListener(OnTextClickListener onTextClickListener) {
        this.onTextClickListener = onTextClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LabelBean labelBean=data.get(position);
        if(position%2==0){
            //可以整除
            holder.ll_item.setBackground(null);
        }else{
            holder.ll_item.setBackgroundColor(context.getResources().getColor(R.color.color_421779D4));
        }
        holder.tv_create_at.setText(TimeUtil.getFormatTimeFromTimestamp(Long.parseLong(labelBean.getCreatedAt()),"yyyy-MM-dd HH:MM:ss"));
        holder.tv_label_number.setText(labelBean.getNumber());
        holder.tv_weight.setText(labelBean.getData().getWeight()+"");
        holder.tv_waste_name.setText(labelBean.getData().getName());
        holder.tv_source_name.setText(labelBean.getData().getDepartment());
        if(StringUtil.isEmpty(labelBean.getData().getCollector())){
            holder.tv_register_name.setText("");
        }else{
            holder.tv_register_name.setText(labelBean.getData().getCollector());
        }



        holder.tv_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onTextClickListener!=null){
                    onTextClickListener.onTextClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout ll_item;
        private TextView tv_print;
        private TextView tv_create_at;//称重时间
        private TextView tv_label_number;//标签号
        private TextView tv_weight;//重量
        private TextView tv_waste_name;//危废类型
        private TextView tv_source_name;//科室
        private TextView tv_register_name;//登记人

        public ViewHolder(View itemView) {
            super(itemView);
            ll_item=itemView.findViewById(R.id.ll_item);
            tv_print=itemView.findViewById(R.id.tv_print);
            tv_create_at=itemView.findViewById(R.id.tv_create_at);
            tv_label_number=itemView.findViewById(R.id.tv_label_number);
            tv_weight=itemView.findViewById(R.id.tv_weight);
            tv_waste_name=itemView.findViewById(R.id.tv_waste_name);
            tv_source_name=itemView.findViewById(R.id.tv_source_name);
            tv_register_name=itemView.findViewById(R.id.tv_register_name);
        }
    }
}
