package com.hellow.xuefeng.encapsulation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hellow.xuefeng.hwlibrary.activity.AbsBaseActivity;

public class MainActivity extends AbsBaseActivity {

    @Override
    protected int getLayoutID() {
        Log.e("111","hahaha");
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState, View contentView) {

    }
}
