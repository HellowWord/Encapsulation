package com.hellow.xuefeng.hwlibrary.img;

import android.net.Uri;
import android.widget.ImageView;

import com.hellow.xuefeng.hwlibrary.app.BaseApplication;
import com.hellow.xuefeng.hwlibrary.storage.StorageUtils;
import com.hellow.xuefeng.hwlibrary.volley.Network;
import com.hellow.xuefeng.hwlibrary.volley.RequestQueue;
import com.hellow.xuefeng.hwlibrary.volley.toolbox.BasicNetwork;
import com.hellow.xuefeng.hwlibrary.volley.toolbox.DiskBasedCache;
import com.hellow.xuefeng.hwlibrary.volley.toolbox.HurlStack;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;


/**
 * @FileName: com.hiersun.dmbase.img.ImageHelper.java
 * @author: daincly
 * @date: 2014-9-16 15:16
 * @description:
 */
public abstract class ImageHelper {
    private ImageLoader mImageLoader;
    private BitmapCache mBitmapCache;
    private RequestQueue mRequestQueue;
    private DiskBasedCache mDiskBaseCache;

    protected ImageHelper() {
        init();
    }


    public void init() {
        mBitmapCache = new BitmapCache();
        Network network = new BasicNetwork(new HurlStack());
        mDiskBaseCache = getDiskCache(getDiskCachePath(), getDiskCacheSize());
        mRequestQueue = new RequestQueue(mDiskBaseCache, network);

        mRequestQueue.start();
        mImageLoader = new ImageLoader(mRequestQueue, mBitmapCache);
    }


    private DiskBasedCache getDiskCache(String path, int size) {

        File cacheDir = StorageUtils.getPathFile(path);

        DiskBasedCache mDiskBasedCache = new DiskBasedCache(cacheDir, size);

        return mDiskBasedCache;

    }


    protected abstract String getDiskCachePath();

    protected abstract int getDiskCacheSize();


    protected abstract int getErrorImageID();

    protected abstract int getDefaultImageId();


    protected void get(String url, ImageView imageView, int maxWidth, int maxHeight, int defImageId, int errImageId, IImageCallBack callback) {
        mImageLoader.get(url, ImageLoader.getImageListener(url, imageView, defImageId, errImageId, callback), maxWidth, maxHeight);
    }


    protected void get(String url, ImageView imageView) {
        get(url, imageView, null);
    }

    protected void get(String url, ImageView imageView, IImageCallBack callback) {
        if (url == null || imageView == null) {
            if (callback != null) {
                callback.onError();
            }
            return;
        }
        get(url, imageView, 0, 0, getDefaultImageId(), getErrorImageID(), callback);
    }

    //===========================================

    /**
     * 加载本地图片
     *
     * @param imageView
     * @param file
     * @param emptyCallback
     */
    protected void load(ImageView imageView, File file, Callback.EmptyCallback emptyCallback) {
        Picasso.with(BaseApplication.getContext()).load(file).resize(768, 1024).centerInside().error(getErrorImageID()).placeholder(getErrorImageID()).into(imageView, emptyCallback);
    }


    protected void load(ImageView imageView, String path) {
        Picasso.with(BaseApplication.getContext()).load(path).fit().centerInside().error(getErrorImageID()).placeholder(getErrorImageID()).into(imageView);
    }


    /**
     * 加载本地图片
     */
    protected void load(ImageView imageView, File file) {
        Picasso.with(BaseApplication.getContext()).load(file).fit().centerCrop().error(getErrorImageID()).placeholder(getErrorImageID()).into(imageView);
    }


    protected void load(ImageView imageView, Uri uri) {
        Picasso.with(BaseApplication.getContext()).load(uri).fit().centerCrop().error(getErrorImageID()).placeholder(getErrorImageID()).into(imageView);
    }


    protected void clear() {
        mDiskBaseCache.clear();
    }

}