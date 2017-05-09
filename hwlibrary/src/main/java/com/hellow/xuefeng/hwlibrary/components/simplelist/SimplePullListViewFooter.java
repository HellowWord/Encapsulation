/**
 * @file PullAndDownListViewFooter.java
 * @create January  18, 2015 6:28:41 PM
 * @author wf
 * @description An ListView support (a) Pull down to refresh, (b) Pull up to load more.
 * Implement ListViewListener, and see stopRefresh() / stopLoadMore().
 */
package com.hellow.xuefeng.hwlibrary.components.simplelist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hellow.xuefeng.hwlibrary.R;


/**
 * @FileName: com.hiersun.dmbase.components.simplelist.SimplePullListViewFooter.java
 * @author: daincly
 * @date: 2015-10-12 10:42
 * @description:
 */
public class SimplePullListViewFooter extends LinearLayout {
    private static final String TAG = "PullListViewFooter";
    public static final int STATE_NORMAL = 0;
    public static final int STATE_READY = 1;
    public static final int STATE_LOADING = 2;

    private Context mContext;

    private View mContentView;
    private View mProgressBar;
    private TextView mHintView;

    public SimplePullListViewFooter(Context context) {
        super(context);
        initView(context);
    }

    public SimplePullListViewFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }


    public void setState(int state) {
        mHintView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mHintView.setVisibility(View.INVISIBLE);
        if (state == STATE_READY) {
            mHintView.setVisibility(View.VISIBLE);
            mHintView.setText(R.string.pull_listview_footer_hint_ready);
        } else if (state == STATE_LOADING) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mHintView.setVisibility(View.VISIBLE);
            mHintView.setText(R.string.pull_listview_footer_hint_normal);
        }
    }

    public void setBottomMargin(int height) {
        if (height < 0) return;
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        lp.bottomMargin = height;
        mContentView.setLayoutParams(lp);
    }

    public int getBottomMargin() {
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        return lp.bottomMargin;
    }

    /**
     * normal status
     */
    public void normal() {
        mHintView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }


    /**
     * loading status
     */
    public void loading() {
        mHintView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * hide footer when disable pull load more
     */
    public void hide() {
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        lp.height = 1;
        mContentView.setLayoutParams(lp);
        mContentView.setVisibility(View.INVISIBLE);
    }

    public boolean isHide() {
        return mContentView.getVisibility() == View.INVISIBLE;
    }

    /**
     * show footer
     */
    public void show() {
        mContentView.setVisibility(View.VISIBLE);
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        lp.height = LayoutParams.WRAP_CONTENT;
        mContentView.setLayoutParams(lp);
    }

    private void initView(Context context) {
        mContext = context;
        LinearLayout moreView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.pull_listview_footer, null);
        addView(moreView);
        moreView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        mContentView = moreView.findViewById(R.id.padlistview_footer_content);
        mProgressBar = moreView.findViewById(R.id.padlistview_footer_progressbar);
        mHintView = (TextView) moreView.findViewById(R.id.padlistview_footer_hint_textview);
    }


}
