package com.hellow.xuefeng.hwlibrary.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewAnimator;

import com.hellow.xuefeng.hwlibrary.debug.L;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @FileName: com.hiersun.dmbase.activity.AbsPolyBaseFragment.java
 * @author: daincly
 * @date: 2014-10-8 14:27
 * @description:
 */
public abstract class AbsPolyBaseFragment extends AbsBaseFragment {
    private static final String TAG = "AbsPolyBaseFragment";
    public static final int FRAGMENT_STATUS_COUNTS = 4;
    public static final int FRAGMENT_STATUS_LOADING = 0;//加载
    public static final int FRAGMENT_STATUS_CONTENT = 1;//加载数据成功
    public static final int FRAGMENT_STATUS_EMPTY = 2;//空数据
    public static final int FRAGMENT_STATUS_ERROR = 3;//加载数据出错

    private ViewAnimator mViewAnimator;
    private Unbinder mUnbinder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewAnimator = new ViewAnimator(getContext());

        mViewAnimator.addView(inflater.inflate(getLoadingLayoutID(), null), FRAGMENT_STATUS_LOADING);

        mViewAnimator.addView(inflater.inflate(getContentLayoutID(), null), FRAGMENT_STATUS_CONTENT);

        mViewAnimator.addView(inflater.inflate(getEmptyLayoutID(), null), FRAGMENT_STATUS_EMPTY);

        mViewAnimator.addView(inflater.inflate(getErrorLayoutID(), null), FRAGMENT_STATUS_ERROR);

        mViewAnimator.setDisplayedChild(FRAGMENT_STATUS_LOADING);

        return mViewAnimator;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mUnbinder = ButterKnife.bind(this, mViewAnimator);
        initLoading(savedInstanceState, getStatusView(FRAGMENT_STATUS_LOADING));
        initContent(savedInstanceState, getStatusView(FRAGMENT_STATUS_CONTENT));
        initEmpty(savedInstanceState, getStatusView(FRAGMENT_STATUS_EMPTY));
        initError(savedInstanceState, getStatusView(FRAGMENT_STATUS_ERROR));
    }


    public View getStatusView(int status) {
        if (mViewAnimator == null || mViewAnimator.getChildCount() < FRAGMENT_STATUS_COUNTS) {
            L.e("ViewAnimator format error");
            return null;
        }
        return mViewAnimator.getChildAt(status);
    }

    /**
     * 设置 页面展示状态
     * 可以传入CONTENT_VIEW_STATUS_XXXX 来设置fragment的不同页面展示
     *
     * @param status
     */
    public void setCurrentViewStatus(int status) {
        if (mViewAnimator == null || mViewAnimator.getChildCount() < FRAGMENT_STATUS_COUNTS) {
            L.e("ViewAnimator format error");
            return;
        }
        if (status < 0 || status >= FRAGMENT_STATUS_COUNTS) {
            L.e("status outOff index IllegalArgumentException");
            return;
        }
        mViewAnimator.setDisplayedChild(status);

    }

    public int getCurrentViewStatus() {
        if (mViewAnimator == null || mViewAnimator.getChildCount() < FRAGMENT_STATUS_COUNTS) {
            L.e("ViewAnimator format error");
            return -1;
        }
        return mViewAnimator.getDisplayedChild();
    }

    @Override
    public void onDestroyView() {
        if (mUnbinder!=null){
            mUnbinder.unbind();
        }
        super.onDestroyView();
    }

    @Override
    protected final void init(Bundle savedInstanceState, View contentView) {

    }

    @Override
    protected int getLayoutID() {
        return 0;
    }

    protected abstract int getLoadingLayoutID();

    protected abstract int getContentLayoutID();

    protected abstract int getEmptyLayoutID();

    protected abstract int getErrorLayoutID();

    protected abstract void initLoading(Bundle savedInstanceState, View loadingView);

    protected abstract void initContent(Bundle savedInstanceState, View contentView);

    protected abstract void initEmpty(Bundle savedInstanceState, View emptyView);

    protected abstract void initError(Bundle savedInstanceState, View errorView);

}
