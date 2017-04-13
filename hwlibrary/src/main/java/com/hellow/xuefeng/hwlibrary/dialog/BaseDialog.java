package com.hellow.xuefeng.hwlibrary.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * @FileName: com.hiersun.dmbase.components.dialog.BaseDialog.java
 * @author: daincly
 * @date: 2015-10-12
 * @description:
 */
public abstract class BaseDialog extends DialogFragment {
    private View mContentView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));
        if (cancelable()) {
            getDialog().setCanceledOnTouchOutside(false);
        } else {
           setCancelable(false);
        }
        mContentView = inflater.inflate(getLayoutID(), null, false);
//        ButterKnife.bind(this, mContentView);

        return mContentView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init(savedInstanceState, mContentView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.unbind(this);
    }


    public boolean isShowing() {
        if (getDialog() == null) {
            return false;
        }
        return getDialog().isShowing();
    }

    protected void setAnimID(int animID) {
        getDialog().getWindow().getAttributes().windowAnimations = animID;
    }


    protected abstract boolean cancelable();

    protected abstract int getLayoutID();

    protected abstract void init(Bundle savedInstanceState, View content);


}
