/*
 * WeakReferenceHandler.java
 * classes : com.baidu.netdisk.util.WeakReferenceHandler
 * @author chenyuquan
 * V 1.0.0
 * Create at 2013-4-23 下午6:36:17
 */
package com.hellow.xuefeng.hwlibrary.app;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;


/**
 * @FileName: com.hiersun.dmbase.app.BaseHandler.java
 * @author: daincly
 * @date: 2014-10-22 12:20
 * @description:
 */
public abstract class BaseHandler<T> extends Handler {

    private static final String TAG = BaseHandler.class.getSimpleName();

    private WeakReference<T> mReference;

    public BaseHandler(T reference) {
        mReference = new WeakReference<T>(reference);
    }

    public BaseHandler(T reference, Looper looper) {
        super(looper);
        mReference = new WeakReference<T>(reference);
    }

    @Override
    public final void handleMessage(Message msg) {
        T t = mReference.get();
        if (t == null || msg == null) {
            return;
        }
        handleMessage(t, msg);
    }

    protected abstract void handleMessage(T reference, Message msg);

    public static Message getMessage(){
       return Message.obtain();
    }

}

