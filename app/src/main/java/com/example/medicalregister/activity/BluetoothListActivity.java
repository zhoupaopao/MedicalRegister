package com.example.medicalregister.activity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lib.base.BaseActivity;
import com.example.lib.utils.SharedPrefUtil;
import com.example.lib.utils.Tips;
import com.example.medicalregister.AppAplication;
import com.example.medicalregister.BR;
import com.example.medicalregister.R;
import com.example.medicalregister.adapter.BlueToothListAdapter;
import com.example.medicalregister.adapter.LabelListAdapter;
import com.example.medicalregister.databinding.ActivityBluetoothListBinding;
import com.example.medicalregister.intface.OnTextClickListener;
import com.example.medicalregister.viewmodel.BlueToothListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BluetoothListActivity extends BaseActivity<ActivityBluetoothListBinding, BlueToothListViewModel> {
    BlueToothListAdapter blueToothListAdapter;
    private final static int SEARCH_CODE = 0x123;
//    private ProgressDialog progressDialog;
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//    private List<BluetoothDevice> mBlueList = new ArrayList<>();
    @Override
    protected void initListener() {
        blueToothListAdapter=new BlueToothListAdapter(this,viewModel.getmList());
        viewDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        viewDataBinding.recyclerview.setAdapter(blueToothListAdapter);
        viewDataBinding.recyclerview.setItemAnimator(new DefaultItemAnimator());
        blueToothListAdapter.setOnTextClickListener(new OnTextClickListener() {
            @Override
            public void onTextClick(int position) {
                SharedPrefUtil.putString("blue_weight",viewModel.getmList().get(position).getAddress());
                Tips.show("蓝牙地磅设置成功");
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        init();
    }
    private void init() {
        // 判断手机是否支持蓝牙
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "设备不支持蓝牙", Toast.LENGTH_SHORT).show();
            finish();
        }
        // 判断是否打开蓝牙
        if (!mBluetoothAdapter.isEnabled()) {
            //弹出对话框提示用户是后打开
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent,SEARCH_CODE);
        } else {
            // 不做提示，强行打开
            mBluetoothAdapter.enable();
        }
        startDiscovery();

    }

    /**
     * 注册异步搜索蓝牙设备的广播
     */
    private void startDiscovery() {
        Set<BluetoothDevice> bondedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
//        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        for (BluetoothDevice bluetoothDevice : bondedDevices) {
            if (!viewModel.getmList().contains(bluetoothDevice)) {
                //添加到集合中
                viewModel.getmList().add(bluetoothDevice);
            }
        }
        // 找到设备的广播
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        // 注册广播
        registerReceiver(receiver, filter);
        // 搜索完成的广播
        IntentFilter filter1 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        // 注册广播
        registerReceiver(receiver, filter1);
        Log.e("TAG", "startDiscovery: 注册广播");
        startScanBluth();
    }

    /**
     * 广播接收器
     */
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 收到的广播类型
            String action = intent.getAction();
            // 发现设备的广播
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // 从intent中获取设备
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // 没否配对
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    if (!viewModel.getmList().contains(device)) {
                        viewModel.getmList().add(device);
//                        mBlueCheckList.add(new BluetoothCheckDeviceBean(device,false));
                    }
//                    textView1.setText("附近设备：" + mBlueList.size() + "个\u3000\u3000本机蓝牙地址：" + getBluetoothAddress());

//                    adapter = new BlueListAdapter(EquipmentAddConnectActivity.this, mBlueList);
//                    listView.setAdapter(adapter);
                    blueToothListAdapter.notifyDataSetChanged();

                    Log.e("TAG", "onReceive: " + viewModel.getmList().size());
                    Log.e("TAG", "onReceive: " + (device.getName() + ":" + device.getAddress() + " ：" + "m" + "\n"));
                }
                // 搜索完成
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                // 关闭进度条
//                progressDialog.dismiss();
                if(viewModel.getmList().size()==0){
                    AppAplication.getSound().playShortResource("检测不到称重设备");
                }
                Log.e("TAG", "onReceive: 搜索完成");
            }
        }
    };
    /**
     * 搜索蓝牙的方法
     */
    private void startScanBluth() {
        // 判断是否在搜索,如果在搜索，就取消搜索
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        // 开始搜索
        mBluetoothAdapter.startDiscovery();
//        if (progressDialog == null) {
//            progressDialog = new ProgressDialog(this);
//        }
//        progressDialog.setMessage("正在搜索，请稍后！");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
    }

    @Override
    protected void onRetryBtnClick() {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==SEARCH_CODE){
            startDiscovery();
        }
    }

    @Override
    protected BlueToothListViewModel getViewModel() {
        return new BlueToothListViewModel();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bluetooth_list;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            //取消注册,防止内存泄露（onDestroy被回调代不代表Activity被回收？：具体回收看系统，由GC回收，同时广播会注册到系统
            //管理的ams中，即使activity被回收，reciver也不会被回收，所以一定要取消注册），
            unregisterReceiver(receiver);
        }
    }
}
