package com.example.medicalregister.viewmodel;

import android.bluetooth.BluetoothDevice;

import com.example.lib.base.MvvmBaseViewModel;

import java.util.ArrayList;

public class BlueToothListViewModel extends MvvmBaseViewModel {
    private ArrayList<BluetoothDevice>mList=new ArrayList<>();

    public ArrayList<BluetoothDevice> getmList() {

        return mList;
    }

    public void setmList(ArrayList<BluetoothDevice> mList) {
        this.mList = mList;
    }
}
