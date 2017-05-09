package com.hellow.xuefeng.encapsulation;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hellow.xuefeng.hwlibrary.activity.AbsBaseRecyclerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/17.
 */

public class DemoRecyclerViewFragment extends AbsBaseRecyclerFragment {
//    @Override
//    protected int getHeaderLayoutID() {
//        return 0;
//    }
//
//    @Override
//    protected int getFooterLayoutID() {
//        return 0;
//    }


    @Override
    protected RecyclerView getList(View contentView) {
        return (RecyclerView) contentView.findViewById(R.id.recycler);
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return null;
    }

    @Override
    protected RecyclerView.ItemAnimator getItemAnimator() {
        return null;
    }

    @Override
    protected ListRecyclerViewHolder getViewHolder(View itemView) {
        return new DemoViewHolder(itemView);
    }

    @Override
    public int getItemLayout() {
        return R.layout.demo_item_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        List<DemoBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoBean bean = new DemoBean();
            bean.setName("name" + i);
            list.add(bean);
        }
        setData(list);
    }

    @Override
    protected int getLoadingLayoutID() {
        return R.layout.demo_recycle_layout_other;
    }

    @Override
    protected int getContentLayoutID() {
        return R.layout.demo_recycle_layout;
    }

    @Override
    protected int getEmptyLayoutID() {
        return R.layout.demo_recycle_layout_other;
    }

    @Override
    protected int getErrorLayoutID() {
        return R.layout.demo_recycle_layout_other;
    }

    @Override
    protected void initLoading(Bundle savedInstanceState, View loadingView) {

        setCurrentViewStatus(FRAGMENT_STATUS_CONTENT);

    }

    @Override
    protected void initEmpty(Bundle savedInstanceState, View emptyView) {

    }

    @Override
    protected void initError(Bundle savedInstanceState, View errorView) {

    }

    public class DemoViewHolder extends ListRecyclerViewHolder<DemoBean> {

        private TextView textView;

        public DemoViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void init(View contentView) {
            textView = (TextView) contentView.findViewById(R.id.demo_tv);
        }

        @Override
        public void bindData(DemoBean t) {

            textView.setText(t.getName());

        }
    }


}
