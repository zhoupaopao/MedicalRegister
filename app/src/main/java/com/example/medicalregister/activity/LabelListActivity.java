package com.example.medicalregister.activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lib.base.BaseActivity;
import com.example.lib.bean.TokenBean;
import com.example.lib.utils.SharedPrefUtil;
import com.example.medicalregister.BR;
import com.example.medicalregister.R;
import com.example.medicalregister.adapter.LabelListAdapter;
import com.example.medicalregister.base.BasePrintActivity;
import com.example.medicalregister.databinding.ActivityLabelListBinding;
import com.example.medicalregister.intface.OnTextClickListener;
import com.example.medicalregister.viewmodel.LabelListViewModel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.sun.jna.Pointer;

import java.lang.reflect.Method;

public class LabelListActivity extends BasePrintActivity<ActivityLabelListBinding, LabelListViewModel> {
    LabelListAdapter labelListAdapter;
    @Override
    protected void initListener() {
        labelListAdapter=new LabelListAdapter(this,viewModel.getmList());
        viewDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        viewDataBinding.recyclerview.setAdapter(labelListAdapter);
        viewDataBinding.recyclerview.setItemAnimator(new DefaultItemAnimator());
        viewModel.getMessageEvent().observe(this, (Observer<? super String>) message -> {
            if (message.equals("success")) {
//                Tips.show(message);
//                adapter.notifyDataSetChanged();
                hideLoadingDialog();
            } else if (message.equals("loading")) {
                showLoadingDialog("加载中");
            } else if (message.equals("fail")) {
//                Tips.show(message);
                hideLoadingDialog();
            }else if (message.equals("adapter")) {
                labelListAdapter.notifyDataSetChanged();
            } else if(message.equals("noMoreData")){
                viewDataBinding.smartRefreshLayout.setNoMoreData(true);
            }else if(message.equals("finishRefresh")){
                viewDataBinding.smartRefreshLayout.finishRefresh();
            }else if(message.equals("finishLoadMore")){
                viewDataBinding.smartRefreshLayout.finishLoadMore();
            }

        });
        viewDataBinding.smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                viewModel.getLabelList(true);
            }
        });
        viewDataBinding.smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                viewModel.getLabelList(false);
            }
        });
        viewDataBinding.tltvTitle.setOnTextClickListener(new OnTextClickListener() {
            @Override
            public void onTextClick(int position) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            TestFunction fun = new TestFunction();
                            fun.ctx = LabelListActivity.this;
                            Method m = TestFunction.class.getDeclaredMethod("Test_Label_CalibrateLabel", Pointer.class);
//                            Method m = TestFunction.class.getDeclaredMethod("Test_Label_DrawText", Pointer.class);

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
        labelListAdapter.setOnTextClickListener(new OnTextClickListener() {
            @Override
            public void onTextClick(int position) {
                //去执行打印
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            TestFunction fun = new TestFunction();
                            fun.labelBean=viewModel.getmList().get(position);
                            fun.ctx = LabelListActivity.this;
                            Method m = TestFunction.class.getDeclaredMethod("Test_Label_DrawLine", Pointer.class);
//                            Method m = TestFunction.class.getDeclaredMethod("Test_Label_DrawText", Pointer.class);

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
        viewModel.getLabelList(true);
    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    protected LabelListViewModel getViewModel() {
        return new LabelListViewModel();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_label_list;
    }
}
