package com.hellow.xuefeng.hwlibrary.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ViewAnimator;

import com.hellow.xuefeng.hwlibrary.debug.L;


/**
 * Created by daincly on 15/9/22.
 */

/**
 * @FileName: com.hiersun.dmbase.activity.AbsContentView.java
 * @author: daincly
 * @date: 2014-9-22 10:48
 * @description:
 */
public abstract class AbsContentView extends ViewAnimator {
    private static final String TAG = "AbsContentView";
    public static final int CONTENT_VIEW_STATUS_COUNTS = 4;
    public static final int CONTENT_VIEW_STATUS_LOADING = 0;//加载页面
    public static final int CONTENT_VIEW_STATUS_CONTENT = 1;//内容页
    public static final int CONTENT_VIEW_STATUS_EMPTY = 2;//无数据页面
    public static final int CONTENT_VIEW_STATUS_ERROR = 3;//错误页面


    private ViewAnimator mViewAnimator;

    public AbsContentView(Context context) {
        super(context);
    }


    public void formatLayout(Context context, int contentViewID) {

        LayoutInflater inflater = LayoutInflater.from(context);
        mViewAnimator = new ViewAnimator(context);

        mViewAnimator.addView(inflater.inflate(getLoadingLayoutID(), null), CONTENT_VIEW_STATUS_LOADING);

        mViewAnimator.addView(inflater.inflate(contentViewID, null), CONTENT_VIEW_STATUS_CONTENT);

        mViewAnimator.addView(inflater.inflate(getEmptyLayoutID(), null), CONTENT_VIEW_STATUS_EMPTY);

        mViewAnimator.addView(inflater.inflate(getErrorLayoutID(), null), CONTENT_VIEW_STATUS_ERROR);

        mViewAnimator.setDisplayedChild(CONTENT_VIEW_STATUS_LOADING);
    }

    public void initView() {

    }

    public View getView() {
        return mViewAnimator;
    }

    public View getView(int status) {
        if (mViewAnimator == null || mViewAnimator.getChildCount() < CONTENT_VIEW_STATUS_COUNTS) {
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
        if (mViewAnimator == null || mViewAnimator.getChildCount() < CONTENT_VIEW_STATUS_COUNTS) {
            L.e("ViewAnimator format error");
            return;
        }
        if (status < 0 || status >= CONTENT_VIEW_STATUS_COUNTS) {
            L.e("status IllegalArgumentException");
            return;
        }
        mViewAnimator.setDisplayedChild(status);

    }

    public int getCurrentViewStatus() {
        if (mViewAnimator == null || mViewAnimator.getChildCount() < CONTENT_VIEW_STATUS_COUNTS) {
            L.e("ViewAnimator format error");
            return -1;
        }
        return mViewAnimator.getDisplayedChild();
    }

    protected abstract int getLoadingLayoutID();

    protected abstract int getEmptyLayoutID();

    protected abstract int getErrorLayoutID();


}
