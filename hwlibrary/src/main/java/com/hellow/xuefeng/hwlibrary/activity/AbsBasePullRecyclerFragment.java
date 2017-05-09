package com.hellow.xuefeng.hwlibrary.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hellow.xuefeng.hwlibrary.swipetoloadlayout.OnLoadMoreListener;
import com.hellow.xuefeng.hwlibrary.swipetoloadlayout.OnRefreshListener;
import com.hellow.xuefeng.hwlibrary.swipetoloadlayout.SwipeToLoadLayout;

/**
 * Created by Administrator on 2017/5/8.
 */

public abstract class AbsBasePullRecyclerFragment extends AbsBaseRecyclerFragment {

    private static final String TAG = "AbsBasePullRecyclerFragment";

    private RecyclerView mRecyclerView;

    private SwipeToLoadLayout mSwipeToLoadLayout;

    private PullOnEvent mPullOnEvent;


    @Override
    protected void initContent(Bundle savedInstanceState, View contentView) {
        super.initContent(savedInstanceState, contentView);
        mRecyclerView = getList(contentView);
        mSwipeToLoadLayout=(SwipeToLoadLayout) getSwipe(contentView);
        mPullOnEvent=new PullOnEvent();
        mSwipeToLoadLayout.setOnRefreshListener(mPullOnEvent);
        mSwipeToLoadLayout.setOnLoadMoreListener(mPullOnEvent);
    }

    protected abstract void onRefresh();

    protected abstract void onLoadMore();

    protected abstract View getSwipe(View contentView);

    public void stopRefresh() {
        if (mRecyclerView != null && mSwipeToLoadLayout!=null) {
            mSwipeToLoadLayout.setRefreshEnabled(false);
        }

    }

    public void stopLoadMore() {
        if (mRecyclerView != null && mSwipeToLoadLayout!=null) {
            mSwipeToLoadLayout.setLoadingMore(false);
        }

    }


    public RecyclerView getRecyclerView(){
        return mRecyclerView;
    }

    public void setSelection(int position) {
        if (position > mRecyclerView.getChildCount()) {
            return;
        }
        mRecyclerView.getLayoutManager().scrollToPosition(position);
    }


    public class PullOnEvent implements OnRefreshListener, OnLoadMoreListener {
        @Override
        public void onRefresh() {
            mSwipeToLoadLayout.setRefreshing(false);
            AbsBasePullRecyclerFragment.this.onRefresh();
        }

        @Override
        public void onLoadMore() {
            AbsBasePullRecyclerFragment.this.onLoadMore();
        }
    }
}
