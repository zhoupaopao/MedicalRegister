package com.example.medicalregister.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.cipherlab.barcode.GeneralString;
import com.cipherlab.barcode.ReaderManager;
import com.cipherlab.barcode.decoder.BcReaderType;
import com.cipherlab.barcode.decoder.Enable_State;
import com.cipherlab.barcode.decoder.KeyboardEmulationType;
import com.cipherlab.barcode.decoder.OutputEnterChar;
import com.cipherlab.barcode.decoder.OutputEnterWay;
import com.cipherlab.barcode.decoderparams.ReaderOutputConfiguration;
import com.example.lib.base.IBaseView;
import com.example.lib.base.MvvmBaseViewModel;
import com.example.lib.base.ViewManager;
import com.example.lib.utils.SoundManage;
import com.example.medicalregister.R;
import com.gyf.immersionbar.ImmersionBar;
import com.zebra.adc.decoder.Barcode2DWithSoft;

import org.greenrobot.eventbus.EventBus;

//import com.arch.demo.core.R;
//import com.arch.demo.core.loadsir.EmptyCallback;
//import com.arch.demo.core.loadsir.ErrorCallback;
//import com.arch.demo.core.loadsir.LoadingCallback;
//import com.arch.demo.core.utils.AppManager;
//import com.arch.demo.core.viewmodel.IMvvmBaseViewModel;
//import com.kingja.loadsir.callback.Callback;
//import com.kingja.loadsir.core.LoadService;
//import com.kingja.loadsir.core.LoadSir;


public abstract class BaseScanActivity<V extends ViewDataBinding, VM extends MvvmBaseViewModel> extends AppCompatActivity implements IBaseView {
    protected VM viewModel;
    protected V viewDataBinding;
    private ProgressDialog mProgressDialog;
    protected Context mContext;
    private static final int REQUEST_CODE_SCAN = 0x0000;

//    protected static Barcode2DWithSoft barcode2DWithSoft=null ;
    private IntentFilter filter;
    private ReaderManager mReaderManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.bg_00519D).statusBarDarkFont(false, 0.2f).init();
        initParameters();
        initViewModel();
        performDataBinding();
        initInternalObserver();

        mContext = this;
        initListener();
        initData();
        ViewManager.getInstance().addActivity(this);
    }

    protected void scan()
    {



        // ***************************************************//
        //  7-6. get/set Code 39(same usage as above)
        // ***************************************************//
//        {
//            if (mReaderManager != null)
//            {
//
//                // step1: new a class, the object is set to default value
//                Code39 settings = new Code39();
//
//                // step2: to check does barcode scanner support this symbology
//                if (ClResult.Err_NotSupport == mReaderManager.Get_Symbology(settings))
//                {
//                    // barcode scanner of device does not support this kind of symbology
//                    return;
//                }
//
//                // step3: if barcode scanner support this symbology，then user can change attribute
//                settings.enable = Enable_State.TRUE;
//                settings.fullASCII = Enable_State.TRUE;
//                settings.checkDigitVerification = Enable_State.FALSE;
//                settings.transmitCheckDigit = Enable_State.FALSE;
//                settings.convertToCode32 = Enable_State.FALSE;
//                settings.convertToCode32Prefix = Enable_State.FALSE;
//
//                // step4
//                // Set settings and check retrun value, if user get ClResult.S_ERR, it means failed,
//                // if user get Err_InvalidParameter, it means user put wrong value into items
//                // if user get Err_NotSupport, it means the barcode reader does not support this kind of settings
//                // if user get S_OK, it means set settings is successful.
//                ClResult clRet = mReaderManager.Set_Symbology(settings);
//                if (ClResult.S_ERR == clRet)
//                    Toast.makeText(this, "Set_Symbology " + settings.getClass().getSimpleName() + " was failed", Toast.LENGTH_SHORT).show();
//                else if (ClResult.Err_InvalidParameter == clRet)
//                    Toast.makeText(this, "Set_Symbology " + settings.getClass().getSimpleName() + " was InvalidParameter",	Toast.LENGTH_SHORT).show();
//                else if (ClResult.Err_NotSupport == clRet)
//                    Toast.makeText(this, "Set_Symbology " + settings.getClass().getSimpleName() + " was NotSupport", Toast.LENGTH_SHORT).show();
//                else if (ClResult.S_OK == clRet)
//                    Toast.makeText(this, "Set_Symbology " + settings.getClass().getSimpleName() + " was successful", Toast.LENGTH_SHORT).show();
//            }
//        }









        // ***************************************************//
        //  8. software trigger
        // ***************************************************//
        {

            // software way to scan barcode (ex, create a button (call this API inside onClick))
            // if decode barcode successfully, app will get an intent "Intent_SOFTTRIGGER_DATA"
            // get decoded data from intent.getStringExtra() (it does need to implement a BroadcastReceiver (see below))
            if (mReaderManager != null)
            {
                Thread sThread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        mReaderManager.SoftScanTrigger();
                    }
                });
                sThread.setPriority( Thread.MAX_PRIORITY );
                sThread.start();

            }


        }

        // ***************************************************//
        //  9. reset to default(include all symbologies and Notification、UserPreferences、ReaderOutputConfiguration
        // ***************************************************//
        {
			/*
			if (mReaderManager != null)
			{
				if (ClResult.S_ERR == mReaderManager.ResetReaderToDefault())
				{
					Toast.makeText(this, "ResetReaderToDefault was failed",
							Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(this, "ResetReaderToDefault was done!",
							Toast.LENGTH_SHORT).show();
				}
			}
			*/
        }

        // ***************************************************//
        //  10. get version of barcode reader service
        // ***************************************************//
        {
			/*
			if (mReaderManager != null)
			{
				String ver = mReaderManager.Get_BarcodeServiceVer();
			}
			*/
        }

        // ***************************************************//
        //  11. for special case , get data from keyboard emulation(not from intent) after software trigger
        // ***************************************************//
        {
			/*
			// software way to scan barcode (ex, create a button (call this API inside onClick))
			// if decode barcode successfully, app will get an intent "Intent_SOFTTRIGGER_DATA"
			// get decoded data from intent.getStringExtra() (it does need to implement a BroadcastReceiver (see below))
			if (mReaderManager != null)
			{
				Thread sThread = new Thread(new Runnable() {

					@Override
					public void run() {
						mReaderManager.SoftwareTriggerAndGetDataFromKeyboardEmulation();
					}
				});
				sThread.setPriority( Thread.MAX_PRIORITY );
				sThread.start();

			}
			*/

        }

    }


    /***
     *   初始化参数
     */
    protected void initParameters() {
        mReaderManager = ReaderManager.InitInstance(this);

        filter = new IntentFilter();
        filter.addAction(GeneralString.Intent_SOFTTRIGGER_DATA);
        filter.addAction(GeneralString.Intent_PASS_TO_APP);
        filter.addAction(GeneralString.Intent_READERSERVICE_CONNECTED);
        registerReceiver(myDataReceiver, filter);
    }

    private final BroadcastReceiver myDataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(GeneralString.Intent_SOFTTRIGGER_DATA)) {

                // extra string from intent
                String data = intent.getStringExtra(GeneralString.BcReaderData);
                EventBus.getDefault().postSticky(data);
                // show decoded data
            }else if (intent.getAction().equals(GeneralString.Intent_PASS_TO_APP)){
                // If user disable KeyboardEmulation, barcode reader service will broadcast Intent_PASS_TO_APP

                // extra string from intent
                String data = intent.getStringExtra(GeneralString.BcReaderData);
                // show decoded data
                EventBus.getDefault().postSticky(data);

            }else if(intent.getAction().equals(GeneralString.Intent_READERSERVICE_CONNECTED)){
                // Make sure this app bind to barcode reader service , then user can use APIs to get/set settings from barcode reader service

                BcReaderType myReaderType = mReaderManager.GetReaderType();
//                e1.setText(myReaderType.toString());
                //第一次进来就会发送
//                EventBus.getDefault().postSticky(myReaderType.toString());

                ReaderOutputConfiguration settings = new ReaderOutputConfiguration();
                mReaderManager.Get_ReaderOutputConfiguration(settings);
                settings.enableKeyboardEmulation = KeyboardEmulationType.None;
                settings.autoEnterWay = OutputEnterWay.Disable;
                settings.autoEnterChar = OutputEnterChar.None;
                settings.showCodeLen = Enable_State.FALSE;
                settings.showCodeType = Enable_State.FALSE;
                settings.szPrefixCode = "";
                settings.szSuffixCode = "";
                settings.useDelim = ':';

                mReaderManager.Set_ReaderOutputConfiguration(settings);

                mReaderManager.SetActive(true);

                //if(mReaderCallback != null)
                //{
                // Enable Callback function
                //	mReaderManager.SetReaderCallback(mReaderCallback);
                //}
            }

        }
    };
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
//        if (barcode2DWithSoft != null) {
//            barcode2DWithSoft.stopScan();
//            barcode2DWithSoft.close();
//        }
        super.onDestroy();
        if (viewModel != null && viewModel.isUIAttached()) {
            viewModel.detachUI();
        }
        if(mProgressDialog!=null&&mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
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
//        ExecuteTask();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }
//    protected void ExecuteTask(){
//
//        if(barcode2DWithSoft!=null){
//            barcode2DWithSoft.close();
//            barcode2DWithSoft=null;
//        }
//        new InitTask(this,barcode2DWithSoft,handler).execute();
//    }

//    protected Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 1:
//                    barcode2DWithSoft= (Barcode2DWithSoft) msg.obj;
//                    break;
//                default:
//                    break;
//            }
//        }
//
//    };
//    protected void ScanBarcode() {
////        Intent intent22 = new Intent(this, ScanQRCodeActivity.class);
////        startActivityForResult(intent22, REQUEST_CODE_SCAN);
//        //先用摄像头
//        if (barcode2DWithSoft != null) {
//            barcode2DWithSoft.scan();
//            barcode2DWithSoft.setScanCallback(ScanBack);
//        }
//
//    }



//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == 139) {
//            if (event.getRepeatCount() == 0) {
//                ScanBarcode();
//                return true;
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//    }

//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        if (keyCode == 139) {
//            if (event.getRepeatCount() == 0) {
//                if (barcode2DWithSoft != null){
//                    barcode2DWithSoft.stopScan();
//                }
//                return true;
//            }
//        }
//        return super.onKeyUp(keyCode, event);
//    }


    public Barcode2DWithSoft.ScanCallback ScanBack = (i, length, bytes) -> {
        if (length < 1) {
            if (length == -1) {//Scan cancel
                Log.i("zzz", "Scan cancel");
            } else if (length == 0) {//Scan TimeOut
                Log.i("zzz", "Scan fail");
            } else {//Scan fail
                Log.i("zzz", "Scan fail");
            }
        } else {
            SoundManage.PlaySound(BaseScanActivity.this, SoundManage.SoundType.SUCCESS);
            String barCode = new String(bytes, 0, length);
            try {
//                String label = barCode;
                EventBus.getDefault().postSticky(barCode);
            } catch (Exception e) {
                Toast.makeText(mContext, "未能识别的二维码类型", Toast.LENGTH_SHORT).show();
            }


        }

    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SCAN:
                //扫描完成
                EventBus.getDefault().register(this);
                if (resultCode == Activity.RESULT_OK && data != null) {
                    //返回的文本内容
                    String content = data.getStringExtra("SN");

                    EventBus.getDefault().postSticky(content);
                }
                break;
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
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

}
