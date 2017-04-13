package com.hellow.xuefeng.hwlibrary.app;

import android.app.Application;
import android.content.Context;

import com.hellow.xuefeng.hwlibrary.debug.L;

/**
 * @FileName: com.hiersun.dmbase.app.Application.java
 * @author: daincly
 * @date: 2014-10-26 12:10
 * @description:
 */
public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";
    private static BaseApplication instance;


    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
        instance = this;
        L.plant(new L.DebugTree());
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

}
