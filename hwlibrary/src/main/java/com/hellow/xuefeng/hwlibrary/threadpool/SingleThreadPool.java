package com.hellow.xuefeng.hwlibrary.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by daincly on 2014/10/22.
 */
public class SingleThreadPool extends ThreadPoolExecutor {
    public SingleThreadPool() {
        super(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
    }
}
