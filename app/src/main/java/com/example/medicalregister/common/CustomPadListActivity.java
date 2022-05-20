package com.example.medicalregister.common;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.lib.base.BaseActivity;
import com.example.lib.utils.Tips;
import com.example.medicalregister.BR;
import com.example.medicalregister.R;
import com.example.medicalregister.databinding.ActivityPadCustomListBinding;
import com.example.medicalregister.intface.OnCheckClickListener;
import com.example.medicalregister.widget.PageControl;


public class CustomPadListActivity extends BaseActivity<ActivityPadCustomListBinding, CustomPadListViewModel> implements CustomPadListViewModel.CallBack {


    CustomPadListAadpter customListAadpter;
    CustomTopAadpter customTopAadpter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_pad_custom_list;
    }



    @Override
    protected void initData() {
        viewDataBinding.twllTitle.setName("平板通用列表11");
        viewModel.addList();
        viewModel.addTopList();
    }

    @Override
    protected void initListener() {
        viewDataBinding.llDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        viewDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        customListAadpter = new CustomPadListAadpter(R.layout.item_custom_list, viewModel.getArrayList());
        viewDataBinding.recyclerview.setAdapter(customListAadpter);
        //设置横向滚动
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        viewDataBinding.recyclerviewShaixuan.setLayoutManager(layoutManager);
        customTopAadpter = new CustomTopAadpter(R.layout.item_custom_top, viewModel.getArrayList_top());
        viewDataBinding.recyclerviewShaixuan.setAdapter(customTopAadpter);
        viewDataBinding.recyclerviewShaixuan.setItemAnimator(new DefaultItemAnimator());
        Log.i("TAG", "initView: ");
//        customTopAadpter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
//                Tips.show("OnItemClick");
//            }
//        });
        viewDataBinding.tvFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPop(viewDataBinding.tvFirst);
            }
        });
        viewDataBinding.tvSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPop(viewDataBinding.tvSecond);
            }
        });
        viewDataBinding.pageControl.setTotalPage(10);
        viewDataBinding.pageControl.setPageChangeListener(new PageControl.OnPageChangeListener() {
            @Override
            public void pageChanged(PageControl pageControl, int numPerPage) {

            }
        });
        customTopAadpter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
//                Tips.show("onItemChildClick");
                showPop(viewDataBinding.llSec);
                //点击后ui的反应
                checkItem(view, position);

            }
        });
        viewDataBinding.tvShaixuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDataBinding.llDrawer.openDrawer(viewDataBinding.llLeft);
            }
        });
        viewDataBinding.tvChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ArrayList<CustomListItem>lastList=new ArrayList<>();
//                for(CustomListItem customListItem:viewModel.getArrayList()){
//                    if(customListItem.isChecked()){
//                        customListItem.setName("11111111");
//                    }
//                }
                for(int pos:viewModel.getPosList()){
                    viewModel.getArrayList().get(pos).setName("12132131231");
                }
                customListAadpter.notifyDataSetChanged();
//                viewModel.getArrayList().remove()
            }
        });
        viewDataBinding.tvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.moveSelect();

//                Iterator<CustomListItem> it = viewModel.getArrayList().iterator();
//                while(it.hasNext()){
//                    CustomListItem x = it.next();
//                    if(x.isChecked()){
//                        it.remove();
//                    }
//                }
                customListAadpter.notifyDataSetChanged();
            }
        });
        viewDataBinding.twllTitle.getTv_edit().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去编辑
                customListAadpter.setVisible(0);
            }
        });
        customListAadpter.setOnCheckClickListener(new OnCheckClickListener() {
            @Override
            public void onCheckClick(int position,boolean check) {
                viewModel.addOrRemove(position,check);
            }
        });
    }


//    protected void initData() {

//    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    protected CustomPadListViewModel getViewModel() {
        return new CustomPadListViewModel();
    }

    @Override
    public int getBindingVariable() {
        return  BR.viewModel;
    }



    PopupWindow pop;
    LinearLayout ll_msg;
    private void showPop(View topView) {

    }

    private void checkItem(View view, int position) {
        int viewWidth = ((TextView) view).getWidth();
        Rect rect = new Rect();
        view.getLocalVisibleRect(rect);
        //判断当前view是否在界面当中
        /**
         * 有两种情况
         * 1。view位于左侧，说明left不是0
         * 2.view位于右侧，width不等于right
         */
        Log.d("onItemChildClick: ", viewWidth + "");
        Log.d("onItemChildClick: ", rect + "");
        if (rect.left != 0) {
            Tips.show("" + rect.left);
            viewDataBinding.recyclerviewShaixuan.scrollBy(-rect.left, 0);
        } else if (rect.right != viewWidth) {
            Tips.show("" + rect.right);
            viewDataBinding.recyclerviewShaixuan.scrollBy(viewWidth - rect.right, 0);
        } else {
            Tips.show("在中间");
        }
        if (viewModel.getNowClick() != position) {
            if (viewModel.getNowClick() != -1) {
                viewModel.getArrayList_top().get(viewModel.getNowClick()).setClicked(false);
            }
            viewModel.getArrayList_top().get(position).setClicked(true);
            customTopAadpter.notifyDataSetChanged();
            viewModel.setNowClick(position);
        }
    }



//    protected void initListener() {
//
//        viewDataBinding.llDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//        viewDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
//        customListAadpter = new CustomListAadpter(R.layout.item_custom_list, viewModel.getArrayList());
//        viewDataBinding.recyclerview.setAdapter(customListAadpter);
//        //设置横向滚动
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
//        viewDataBinding.recyclerviewShaixuan.setLayoutManager(layoutManager);
//        customTopAadpter = new CustomTopAadpter(R.layout.item_custom_top, viewModel.getArrayList_top());
//        viewDataBinding.recyclerviewShaixuan.setAdapter(customTopAadpter);
//        viewDataBinding.recyclerviewShaixuan.setItemAnimator(new DefaultItemAnimator());
//        Log.i("TAG", "initView: ");
////        customTopAadpter.setOnItemClickListener(new OnItemClickListener() {
////            @Override
////            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
////                Tips.show("OnItemClick");
////            }
////        });
//        viewDataBinding.tvFirst.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPop(viewDataBinding.tvFirst);
//            }
//        });
//        viewDataBinding.tvSecond.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPop(viewDataBinding.tvSecond);
//            }
//        });
//        viewDataBinding.pageControl.setTotalPage(10);
//        viewDataBinding.pageControl.setPageChangeListener(new PageControl.OnPageChangeListener() {
//            @Override
//            public void pageChanged(PageControl pageControl, int numPerPage) {
//
//            }
//        });
//        customTopAadpter.setOnItemChildClickListener(new OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
////                Tips.show("onItemChildClick");
//                showPop(viewDataBinding.llSec);
//                //点击后ui的反应
//                checkItem(view, position);
//
//            }
//        });
//        viewDataBinding.tvShaixuan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewDataBinding.llDrawer.openDrawer(viewDataBinding.llLeft);
//            }
//        });
//    }




//    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.tv_shaixuan) {
//
//        }
//    }

    @Override
    public void listAdapter() {
        customListAadpter.notifyDataSetChanged();
    }

    @Override
    public void topAdapter() {
        customTopAadpter.notifyDataSetChanged();
    }
}
