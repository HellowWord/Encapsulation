package com.hellow.xuefeng.hwlibrary.threadpool;

import android.annotation.TargetApi;
import android.os.Build;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by daincly on 2014/10/31.
 */
public class WorkerThreadPool extends ThreadPoolExecutor {
    public static final String TAG = "WorkerThreadPool";
    private final static int MAX_THREAD_SIZE = 4;
    private final static Long THREAD_LIFETIME = 10L;
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public WorkerThreadPool() {
        super(1, MAX_THREAD_SIZE,
                THREAD_LIFETIME, TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>()
        );
    }


}
