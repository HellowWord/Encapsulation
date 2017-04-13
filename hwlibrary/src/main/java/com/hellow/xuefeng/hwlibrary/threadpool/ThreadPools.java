package com.hellow.xuefeng.hwlibrary.threadpool;

/**
 * Created by daincly on 2014/10/22.
 */
public class ThreadPools {

    private static volatile SingleThreadPool mSingleThreadPool;
    private static volatile WorkerThreadPool mWorkerThreadPool;
    private static volatile AsyncThreadPool mAsyncThreadPool;

    /**
     * 获取串行执行的线程池
     *
     * @return SingleThreadPool
     */
    public static SingleThreadPool getSingleThreadPool() {
        if (mSingleThreadPool == null) {
            synchronized (ThreadPools.class) {
                if (mSingleThreadPool == null) {
                    mSingleThreadPool = new SingleThreadPool();
                }
            }
        }
        return mSingleThreadPool;
    }

    /**
     * 获取线程池,平时的任务用这个
     *
     * @return 工作线程池
     */
    public static WorkerThreadPool getWorkThreadPool() {
        synchronized (ThreadPools.class) {
            if (mWorkerThreadPool == null || mWorkerThreadPool.isShutdown() || mWorkerThreadPool.isTerminated()) {
                mWorkerThreadPool = new WorkerThreadPool();
            }
        }
        return mWorkerThreadPool;
    }

    public static AsyncThreadPool getAsyncThreadPool() {
        synchronized (ThreadPools.class) {
            if (mAsyncThreadPool == null || mAsyncThreadPool.isShutdown() || mAsyncThreadPool.isTerminated()) {
                mAsyncThreadPool = new AsyncThreadPool();
            }
        }
        return mAsyncThreadPool;
    }
}
