package com.hellow.xuefeng.hwlibrary.components.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @FileName: com.hiersun.dmbase.components.ListViewForScrollView.java
 * @author: daincly
 * @date: 2016-01-16 15:05
 * @description: <功能>
 */
public class ListViewForScrollView extends ListView {
    private final static String TAG = "ListViewForScrollView";

    public ListViewForScrollView(Context context) {
        super(context);
    }

    public ListViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewForScrollView(Context context, AttributeSet attrs,
                                 int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
