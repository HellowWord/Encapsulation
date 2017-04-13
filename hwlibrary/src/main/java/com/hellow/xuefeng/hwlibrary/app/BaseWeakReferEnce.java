package com.hellow.xuefeng.hwlibrary.app;

import java.lang.ref.WeakReference;

/**
 * @FileName: com.hiersun.dmbase.app.BaseWeakReferEnce.java
 * @author: daincly
 * @date: 2015-10-8 10:33
 * @description:
 */
public class BaseWeakReferEnce<T>  {
    private WeakReference<T> mReference;
    protected BaseWeakReferEnce(T t) {
        mReference = new WeakReference<T>(t);
    }
    protected T get(){
        return mReference.get();
    }


}
