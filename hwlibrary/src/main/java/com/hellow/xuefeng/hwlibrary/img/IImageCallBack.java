package com.hellow.xuefeng.hwlibrary.img;

import android.graphics.Bitmap;

/**
 * @FileName: com.hiersun.dmbase.img.IImageCallBack.java
 * @author: daincly
 * @date: 2015-11-26 18:52
 * @description: <功能>
 */


public interface IImageCallBack {
    void onSuccess(Bitmap bitmap);

    void onError();
}


