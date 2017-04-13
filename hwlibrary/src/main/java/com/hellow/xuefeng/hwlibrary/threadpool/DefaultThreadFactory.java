package com.hellow.xuefeng.hwlibrary.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by daincly on 2014/10/31.
 */
public class DefaultThreadFactory implements ThreadFactory {
    private static final String TAG = DefaultThreadFactory.class.getSimpleName();
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    DefaultThreadFactory() {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        namePrefix = "pool-" +
                poolNumber.getAndIncrement() +
                "-thread-";
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0);
        t.setDaemon(true);
        if (t.getPriority() != Thread.MIN_PRIORITY)
            t.setPriority(Thread.MIN_PRIORITY);
        return t;
    }
}
