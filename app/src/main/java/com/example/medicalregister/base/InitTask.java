package com.example.medicalregister.base;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;


import com.example.lib.R;
import com.zebra.adc.decoder.Barcode2DWithSoft;

public class InitTask extends AsyncTask<String, Integer, Boolean> {
    private BaseScanActivity activity;
    private Barcode2DWithSoft barcode2DWithSoft = null;
    private ProgressDialog progressDialog;

    Handler mHandler;
    public InitTask(BaseScanActivity activity,Barcode2DWithSoft barcode2DWithSoft,Handler mHandler){
        this.activity=activity;
        this.barcode2DWithSoft=barcode2DWithSoft;
        this.mHandler=mHandler;
    }
    @Override
    protected Boolean doInBackground(String... params) {

        if (barcode2DWithSoft == null) {
            barcode2DWithSoft = Barcode2DWithSoft.getInstance();
        }

        boolean reuslt = false;
        if (barcode2DWithSoft != null) {
            reuslt = barcode2DWithSoft.open(activity);
            Log.i("zzz", "open=" + reuslt);

        }
        return reuslt;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (result) {
            barcode2DWithSoft.setParameter(324, 1);
            barcode2DWithSoft.setParameter(300, 0); // Snapshot Aiming
            barcode2DWithSoft.setParameter(361, 0); // Image Capture Illumination

            // interleaved 2 of 5
            barcode2DWithSoft.setParameter(6, 1);
            barcode2DWithSoft.setParameter(22, 0);
            barcode2DWithSoft.setParameter(23, 55);
            Toast.makeText(activity, "红外扫码模块初始化成功", Toast.LENGTH_SHORT).show();

            //传值
            Message msg = mHandler.obtainMessage();
            if(result!=null){
                msg.what = 1;
                msg.obj = barcode2DWithSoft;
            }else{
                msg.what = 2;
            }
            mHandler.sendMessage(msg);
        } else {
            Toast.makeText(activity, "红外扫码模块初始化失败，请关闭页面重试", Toast.LENGTH_SHORT).show();
        }
        //cancelDialog
        dismissProgressDialog();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //showDialog
        showProgressDialog(false);
    }
    private void showProgressDialog(boolean cancelable) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(activity, R.style.TransparentDialogTheme);
            progressDialog.setCancelable(cancelable);
            progressDialog.show();
        } else {
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
        }
//        if (customDialog == null) {
//            customDialog = new CustomDialog(this, R.style.CustomDialog);
//            customDialog.show();
//        } else {
//            if (!customDialog.isShowing()) {
//                customDialog.show();
//            }
//        }

    }

    private void dismissProgressDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
