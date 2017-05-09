package com.hellow.xuefeng.hwlibrary.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.hellow.xuefeng.hwlibrary.components.simplelist.SimplePullListView;

/**
 * @FileName: com.hiersun.dmbase.activity.AbsBasePullListFragment.java
 * @author: daincly
 * @date: 2015-12-03 14:37
 * @description: <功能>
 */
public abstract class AbsBasePullListFragment extends AbsBaseListFragment {
    private final static String TAG = "AbsBasePullListFragment";
    public static final int MODE_NONE = 0x0;
    public static final int MODE_PULL_DOWN_TO_REFRESH = 0x1;
    public static final int MODE_PULL_UP_TO_REFRESH = 0x2;
    public static final int MODE_BOTH = 0x3;


    private SimplePullListView mListView;


    @Override
    protected int getHeaderLayoutID() {
        return 0;
    }

    @Override
    protected int getFooterLayoutID() {
        return 0;
    }

    @Override
    protected void initContent(Bundle savedInstanceState, View contentView) {
        super.initContent(savedInstanceState, contentView);
//        mListView = (SimplePullListView) contentView;
        mListView = (SimplePullListView) getList(contentView);
        switch (getMode()) {
            case MODE_PULL_DOWN_TO_REFRESH:
                mListView.setPullRefreshEnable(true);
                mListView.setPullLoadEnable(false);
                break;
            case MODE_PULL_UP_TO_REFRESH:
                mListView.setPullRefreshEnable(false);
                mListView.setPullLoadEnable(true);
                break;
            case MODE_BOTH:
                mListView.setPullRefreshEnable(true);
                mListView.setPullLoadEnable(true);
                break;
            case MODE_NONE:
            default:
                mListView.setPullRefreshEnable(false);
                mListView.setPullLoadEnable(false);
        }
        mListView.setPadListViewListener(new PullOnEvent());
    }

//    protected abstract View getList(View contentView);


    protected abstract int getMode();

    protected abstract void onRefresh();

    protected abstract void onLoadMore();

    public void hideLoadMore() {
        mListView.setPullLoadEnable(false);
    }

    public ListView getListView(){
        return mListView;
    }

    public void showLoadMore() {
        mListView.setPullLoadEnable(true);
    }

    public void setSelection(int position) {
        if (position > mListView.getCount()) {
            return;
        }
        mListView.setSelection(position);
    }


    public void stopPull() {
        if (mListView != null) {
            mListView.stopLoadMore();
            mListView.stopRefresh();
        }

    }

    public class PullOnEvent implements SimplePullListView.onEventListener {
        @Override
        public void onRefresh() {
            if (getMode() != MODE_BOTH && getMode() != MODE_PULL_DOWN_TO_REFRESH) {
                return;
            }
            mListView.setPullLoadEnable(true);
            AbsBasePullListFragment.this.onRefresh();
        }

        @Override
        public void onLoadMore() {
            if (getMode() != MODE_PULL_UP_TO_REFRESH && getMode() != MODE_BOTH) {
                return;
            }
            AbsBasePullListFragment.this.onLoadMore();
        }
    }
}
