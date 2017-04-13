package com.hellow.xuefeng.hwlibrary.img;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.util.LruCache;

import com.hellow.xuefeng.hwlibrary.volley.toolbox.ImageLoader;


/**
 * @FileName: com.hiersun.dmbase.activity.ImageHelper.java
 * @author: daincly
 * @date: 2014-11-3
 * @description:
 */
public class BitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {


    private final static int maxMemory = (int) (5* 1024);

    private final static int maxSize = maxMemory;

    public BitmapCache() {
        super(maxSize);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB_MR1) {
            return value.getByteCount() / 2048;
        }
        return value.getRowBytes() * value.getHeight() / 2048;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);

    }

    @Override

    public void putBitmap(String url, Bitmap bitmap) {

        put(url, bitmap);

    }

}
