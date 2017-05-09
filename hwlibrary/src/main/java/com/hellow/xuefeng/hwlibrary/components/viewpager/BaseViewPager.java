package com.hellow.xuefeng.hwlibrary.components.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @FileName: com.hiersun.dmbase.components.viewpager.BaseViewPager.java
 * @author: daincly
 * @date: 2015-12-08 09:22
 * @description: <功能>
 */
public class BaseViewPager extends ViewPager {
    private final static String TAG = "BaseViewPager";
    private boolean isCanScroll;
    public BaseViewPager(Context context) {
        super(context);
    }


    public BaseViewPager(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    public void setCanscroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }


    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        // TODO Auto-generated method stub
        if (isCanScroll) {
            return super.onTouchEvent(arg0);
        } else {
            return false;
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isCanScroll) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }
}
