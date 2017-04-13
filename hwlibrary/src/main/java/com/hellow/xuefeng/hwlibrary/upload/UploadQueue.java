package com.hellow.xuefeng.hwlibrary.upload;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @FileName: com.hiersun.dmbase.upload.UploadManager.java
 * @author: daincly
 * @date: 2015-12-17 19:04
 * @description: <功能>
 */
public class UploadQueue {
    private final static String TAG = "UploadManager";
    private ThreadPoolExecutor mThreadPool;
    private String mUrl;
    public UploadQueue(String url) {
        mUrl = url;
        mThreadPool = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    }

    public void add(UploadRequest request) {
        mThreadPool.execute(new UploadTask(mUrl,request));
    }
}
