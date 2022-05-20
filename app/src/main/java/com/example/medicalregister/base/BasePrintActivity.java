package com.example.medicalregister.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.caysn.autoreplyprint.AutoReplyPrint;
import com.example.lib.base.BaseActivity;
import com.example.lib.base.IBaseView;
import com.example.lib.base.MvvmBaseViewModel;
import com.example.lib.base.ViewManager;
import com.example.lib.utils.SharedPrefUtil;
import com.example.lib.utils.Tips;
import com.gyf.immersionbar.ImmersionBar;
import com.sun.jna.Pointer;

import java.util.Calendar;
import java.util.Date;

public abstract class BasePrintActivity<V extends ViewDataBinding, VM extends MvvmBaseViewModel> extends AppCompatActivity implements IBaseView {
    protected VM viewModel;
    protected V viewDataBinding;
    private ProgressDialog mProgressDialog;
    protected Context mContext;
    private static final int REQUEST_CODE_SCAN = 0x0000;
    protected Pointer h = Pointer.NULL;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(com.example.lib.R.color.bg_00519D).statusBarDarkFont(false, 0.2f).init();
        initParameters();
        initViewModel();
        performDataBinding();
        initInternalObserver();

        mContext = this;

        initListener();
        initData();
        ViewManager.getInstance().addActivity(this);


        //存放打印相关的
        new Thread(new Runnable() {
            @Override
            public void run() {
                AddCallback();
//                String[] devicePaths = AutoReplyPrint.CP_Port_EnumUsb_Helper.EnumUsb();
//                StringBuffer stringBuffer=new StringBuffer();
//                if (devicePaths != null) {
//                    for (int i = 0; i < devicePaths.length; ++i) {
//                        String name = devicePaths[i];
//                        stringBuffer.append(name);
////                        if (stringBuffer.toString().trim().equals("")) {
////                            text = name;
////                            cbxListUSB.setText(text);
////                        }
//                    }
//                }
//                h = AutoReplyPrint.INSTANCE.CP_Port_OpenCom("/dev/ttyS1", 115200, AutoReplyPrint.CP_ComDataBits_8, AutoReplyPrint.CP_ComParity_NoParity, AutoReplyPrint.CP_ComStopBits_One, 0, 1);
//                Log.i("TAG", devicePaths[0]);

                    connectDevice(0);


            }
        }).start();

        //这里是openport

    }

    private void connectDevice(int num){
        int maxNum=2;

        try {
            h = AutoReplyPrint.INSTANCE.CP_Port_OpenUsb("VID:0x4B43,PID:0x3538", 1);
        }catch (Exception e){
            if(num>=maxNum){
                Tips.show("打印机连接失败");
            }else{
                num=num+1;
                connectDevice(num);
            }

        }

    }
    /***
     *   初始化参数
     */
    protected void initParameters() {

    }
    /** 初始化数据 */

    protected abstract void initListener();
    protected abstract void initData();
    private void initViewModel() {
        viewModel = getViewModel();
        if(viewModel != null) {
            viewModel.attachUI(this);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewModel != null && viewModel.isUIAttached()) {
            viewModel.detachUI();
        }
        if(mProgressDialog!=null&&mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        ClosePort();
        ViewManager.getInstance().finishActivity(this);
    }




    @Override
    public void showLoadingDialog(String hint) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        mProgressDialog.setMessage(hint);
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
    }
    @Override
    public void hideLoadingDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }




    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    protected abstract void onRetryBtnClick();

    protected abstract VM getViewModel();

    public abstract int getBindingVariable();

    public abstract
    @LayoutRes
    int getLayoutId();

    private void performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.viewModel = viewModel == null ? getViewModel() : viewModel;
        if(getBindingVariable() > 0) {
            viewDataBinding.setVariable(getBindingVariable(), viewModel);
        }
        viewDataBinding.executePendingBindings();
        viewDataBinding.setLifecycleOwner(this);
    }


    protected  void initInternalObserver(){
        viewModel.loadingEvent.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean o) {
                if(o){
                    showLoadingDialog("加载中");
                }else{
                    hideLoadingDialog();
                }
            }
        });
    }


    //下面用于存放打印相关的数据
    private void AddCallback() {
        AutoReplyPrint.INSTANCE.CP_Port_AddOnPortOpenedEvent(opened_callback, Pointer.NULL);
        AutoReplyPrint.INSTANCE.CP_Port_AddOnPortOpenFailedEvent(openfailed_callback, Pointer.NULL);
        AutoReplyPrint.INSTANCE.CP_Port_AddOnPortClosedEvent(closed_callback, Pointer.NULL);
        AutoReplyPrint.INSTANCE.CP_Printer_AddOnPrinterStatusEvent(status_callback, Pointer.NULL);
        AutoReplyPrint.INSTANCE.CP_Printer_AddOnPrinterReceivedEvent(received_callback, Pointer.NULL);
        AutoReplyPrint.INSTANCE.CP_Printer_AddOnPrinterPrintedEvent(printed_callback, Pointer.NULL);
    }
    private void RemoveCallback() {
        AutoReplyPrint.INSTANCE.CP_Port_RemoveOnPortOpenedEvent(opened_callback);
        AutoReplyPrint.INSTANCE.CP_Port_RemoveOnPortOpenFailedEvent(openfailed_callback);
        AutoReplyPrint.INSTANCE.CP_Port_RemoveOnPortClosedEvent(closed_callback);
        AutoReplyPrint.INSTANCE.CP_Printer_RemoveOnPrinterStatusEvent(status_callback);
        AutoReplyPrint.INSTANCE.CP_Printer_RemoveOnPrinterReceivedEvent(received_callback);
        AutoReplyPrint.INSTANCE.CP_Printer_RemoveOnPrinterPrintedEvent(printed_callback);
    }

    public void ClosePort() {
        if (h != Pointer.NULL) {
            AutoReplyPrint.INSTANCE.CP_Port_Close(h);
            h = Pointer.NULL;
        }
    }
    AutoReplyPrint.CP_OnPortOpenedEvent_Callback opened_callback = new AutoReplyPrint.CP_OnPortOpenedEvent_Callback() {
        @Override
        public void CP_OnPortOpenedEvent(Pointer handle, String name, Pointer private_data) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    Toast.makeText(mContext, "Open Success", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
    AutoReplyPrint.CP_OnPortOpenFailedEvent_Callback openfailed_callback = new AutoReplyPrint.CP_OnPortOpenFailedEvent_Callback() {
        @Override
        public void CP_OnPortOpenFailedEvent(Pointer handle, String name, Pointer private_data) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(SharedPrefUtil.getBoolean("needShowMsg",true)){
                        Toast.makeText(mContext, "Open Failed", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    };
    AutoReplyPrint.CP_OnPortClosedEvent_Callback closed_callback = new AutoReplyPrint.CP_OnPortClosedEvent_Callback() {
        @Override
        public void CP_OnPortClosedEvent(Pointer h, Pointer private_data) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ClosePort();
                }
            });
        }
    };
    AutoReplyPrint.CP_OnPrinterStatusEvent_Callback status_callback = new AutoReplyPrint.CP_OnPrinterStatusEvent_Callback() {
        @Override
        public void CP_OnPrinterStatusEvent(Pointer h, final long printer_error_status, final long printer_info_status, Pointer private_data) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Calendar calendar = Calendar.getInstance();
                    Date calendarDate = calendar.getTime();
                    String time = DateFormat.format("yyyy-MM-dd kk:mm:ss", calendarDate).toString();
                    AutoReplyPrint.CP_PrinterStatus status = new AutoReplyPrint.CP_PrinterStatus(printer_error_status, printer_info_status);
                    String error_status_string = String.format(" Printer Error Status: 0x%04X", printer_error_status & 0xffff);
                    if (status.ERROR_OCCURED()) {
                        if (status.ERROR_CUTTER())
                            error_status_string += "[ERROR_CUTTER]";
                        if (status.ERROR_FLASH())
                            error_status_string += "[ERROR_FLASH]";
                        if (status.ERROR_NOPAPER())
                            error_status_string += "[ERROR_NOPAPER]";
                        if (status.ERROR_VOLTAGE())
                            error_status_string += "[ERROR_VOLTAGE]";
                        if (status.ERROR_MARKER())
                            error_status_string += "[ERROR_MARKER]";
                        if (status.ERROR_ENGINE())
                            error_status_string += "[ERROR_MOVEMENT]";
                        if (status.ERROR_OVERHEAT())
                            error_status_string += "[ERROR_OVERHEAT]";
                        if (status.ERROR_COVERUP())
                            error_status_string += "[ERROR_COVERUP]";
                        if (status.ERROR_MOTOR())
                            error_status_string += "[ERROR_MOTOR]";
                    }
                    String info_status_string = String.format(" Printer Info Status: 0x%04X", printer_info_status & 0xffff);
                    if (status.INFO_LABELMODE())
                        info_status_string += "[Label Mode]";
                    if (status.INFO_LABELPAPER())
                        info_status_string += "[Label Paper]";
                    if (status.INFO_PAPERNOFETCH())
                        info_status_string += "[Paper Not Fetch]";

//                    textViewPrinterErrorStatus.setText(time + error_status_string);
//                    textViewPrinterInfoStatus.setText(time + info_status_string);
                }
            });
        }
    };
    AutoReplyPrint.CP_OnPrinterReceivedEvent_Callback received_callback = new AutoReplyPrint.CP_OnPrinterReceivedEvent_Callback() {
        @Override
        public void CP_OnPrinterReceivedEvent(Pointer h, final int printer_received_byte_count, Pointer private_data) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Calendar calendar = Calendar.getInstance();
                    Date calendarDate = calendar.getTime();
                    String time = DateFormat.format("yyyy-MM-dd kk:mm:ss", calendarDate).toString();
//                    textViewPrinterReceived.setText(time + " PrinterReceived: " + printer_received_byte_count);
                }
            });
        }
    };
    AutoReplyPrint.CP_OnPrinterPrintedEvent_Callback printed_callback = new AutoReplyPrint.CP_OnPrinterPrintedEvent_Callback() {
        @Override
        public void CP_OnPrinterPrintedEvent(Pointer h, final int printer_printed_page_id, Pointer private_data) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Calendar calendar = Calendar.getInstance();
                    Date calendarDate = calendar.getTime();
                    String time = DateFormat.format("yyyy-MM-dd kk:mm:ss", calendarDate).toString();
//                    textViewPrinterPrinted.setText(time + " PrinterPrinted: " + printer_printed_page_id);
                }
            });
        }
    };
}
