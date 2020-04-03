package com.fun.learn.utils;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author LittlePart
 * @version 1.0
 * @date 2020/03/2020/3/11 10:57
 */
public class ThreadPoolUtil {

    private static final AtomicLong THREAD_COUNT = new AtomicLong(1);
    /**
     * int corePoolSize,
     * int maximumPoolSize,
     * long keepAliveTime,
     * TimeUnit unit,
     * BlockingQueue<Runnable> workQueue,
     * ThreadFactory threadFactory
     */
    private static final ThreadPoolExecutor pool = new ThreadPoolExecutor(
            8, 16, 10L, TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(),
            r -> new Thread("Common-Util-Thread-" + THREAD_COUNT.getAndIncrement()));

    public static ThreadPoolExecutor getPool(){
        return pool;
    }
}
