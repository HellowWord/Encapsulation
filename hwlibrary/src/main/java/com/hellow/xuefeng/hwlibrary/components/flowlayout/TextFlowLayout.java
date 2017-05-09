package com.hellow.xuefeng.hwlibrary.components.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import java.util.List;


/**
 * @FileName: com.hiersun.dmbase.components.flowlayout.TextFlowLayout.java
 * @author: daincly
 * @date: 2015-4-21
 * @description:
 */
public class TextFlowLayout extends FlowLayout implements View.OnClickListener {

    private IOnItemClickListener mListener;

    public TextFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDate(int textViewId, List<String> items) {
        if (items == null || items.isEmpty() || textViewId == 0) {
            return;
        }
        LayoutInflater inflater = LayoutInflater.from(getContext());
        int n = items.size();
        for (int i = 0; i < n; i++) {
            View childView = inflater.inflate(textViewId, this, false);
            setChildView(childView, items.get(i));
            this.addView(childView);
        }
        invalidate();
    }
    protected void setChildView(View v, String str) {
        if (!(v instanceof TextView)) {
            return;
        }
        TextView tx = (TextView) v;
        tx.setText(str);
        if(mListener!=null){
            tx.setOnClickListener(this);
        }
    }

    @Override
    public final void onClick(View v) {
        if(mListener==null||!(v instanceof TextView)){
            return;
        }
        mListener.onClick((String)((TextView)v).getText().toString());
    }

    public void setOnclickListener (IOnItemClickListener listener){
        mListener = listener;
    }


    public static interface IOnItemClickListener{
        void onClick(String item);
    }



}
