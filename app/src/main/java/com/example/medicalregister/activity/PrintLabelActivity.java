package com.example.medicalregister.activity;

import com.caysn.autoreplyprint.AutoReplyPrint;
import com.example.lib.base.BaseActivity;
import com.example.lib.utils.PhoneInfoUtil;
import com.example.medicalregister.BR;
import com.example.medicalregister.R;
import com.example.medicalregister.base.BasePrintActivity;
import com.example.medicalregister.databinding.ActivityPrintLabelBinding;
import com.example.medicalregister.intface.OnTextClickListener;

import com.example.medicalregister.viewmodel.PrintLabelViewModel;
import com.sun.jna.Pointer;

import java.lang.reflect.Method;

public class PrintLabelActivity extends BasePrintActivity<ActivityPrintLabelBinding, PrintLabelViewModel> {
    @Override
    protected void initListener() {
        viewDataBinding.wsvRestart.setOnTextClickListener(new OnTextClickListener() {
            @Override
            public void onTextClick(int position) {
                PhoneInfoUtil.restartSystem();
            }
        });
        viewDataBinding.wsvToSi.setOnTextClickListener(new OnTextClickListener() {
            @Override
            public void onTextClick(int position) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            TestFunction fun = new TestFunction();
                            fun.ctx = PrintLabelActivity.this;
                            Method m = TestFunction.class.getDeclaredMethod("CP_Label_FeedPaperToTearPosition", Pointer.class);
                            m.invoke(fun, h);
                        } catch (Throwable tr) {
                            tr.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                RefreshUI();
                            }
                        });
                    }
                }).start();

            }
        });
        viewDataBinding.wsvJiaozhun.setOnTextClickListener(new OnTextClickListener() {
            @Override
            public void onTextClick(int position) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            TestFunction fun = new TestFunction();
                            fun.ctx = PrintLabelActivity.this;
                            Method m = TestFunction.class.getDeclaredMethod("Test_Label_CalibrateLabel", Pointer.class);
                            m.invoke(fun, h);
                        } catch (Throwable tr) {
                            tr.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                RefreshUI();
                            }
                        });
                    }
                }).start();

            }
        });
        viewDataBinding.wsvPrint.setOnTextClickListener(new OnTextClickListener() {
            @Override
            public void onTextClick(int position) {
                //打印

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            TestFunction fun = new TestFunction();
                            fun.ctx = PrintLabelActivity.this;
                            Method m = TestFunction.class.getDeclaredMethod("Test_Label_DrawLine", Pointer.class);
                            m.invoke(fun, h);
                        } catch (Throwable tr) {
                            tr.printStackTrace();
                        }
                       runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                RefreshUI();
                            }
                        });
                    }
                }).start();
            }
        });
    }

    @Override
    protected void initData() {

    }



    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    protected PrintLabelViewModel getViewModel() {
        return new PrintLabelViewModel();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_print_label;
    }
}
