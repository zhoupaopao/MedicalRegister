package com.example.medicalregister.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalregister.R;
import com.example.medicalregister.bean.LabelBean;
import com.example.medicalregister.intface.OnTextClickListener;

import java.util.List;

public class BlueToothListAdapter extends RecyclerView.Adapter<BlueToothListAdapter.ViewHolder>{
    List<BluetoothDevice> data;
    private Context context;

    public BlueToothListAdapter(Context context, List<BluetoothDevice> data) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_bluetooth_list,parent,false);
        return new ViewHolder(view);
    }

    private OnTextClickListener onTextClickListener;




    public void setOnTextClickListener(OnTextClickListener onTextClickListener) {
        this.onTextClickListener = onTextClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BluetoothDevice bluetoothDevice=data.get(position);
        holder.tv_item_name.setText(TextUtils.isEmpty(bluetoothDevice.getName())?"未知设备" : bluetoothDevice.getName());
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTextClickListener.onTextClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout ll_item;
        private TextView tv_item_name;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_item=itemView.findViewById(R.id.ll_item);
            tv_item_name=itemView.findViewById(R.id.tv_item_name);
        }
    }
}
