package com.fun.learn.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author LittlePart
 * @version 1.0
 * @date 2020/03/2020/3/31 20:14
 */
@Slf4j
public class ThreadUtil {

    private static final ScheduledThreadPoolExecutor EXECUTOR = new ScheduledThreadPoolExecutor(
            32,
            getThreadFactory("thread-commons-%d", 1L),
            new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    log.warn("当前请求过多，部分任务已经无法处理");
                }
            });

    static {
        EXECUTOR.setMaximumPoolSize(64);
        EXECUTOR.setKeepAliveTime(20L, TimeUnit.SECONDS);
    }

    public static ThreadFactory getThreadFactory(final String pattern, long begin) {
        return new ThreadFactory() {
            private AtomicLong atomicLong = new AtomicLong(1L);

            @Override
            public Thread newThread(Runnable r) {
                if (atomicLong.get() == Long.MAX_VALUE) {
                    atomicLong.getAndSet(1L);
                }
                return new Thread(r, String.format(pattern, atomicLong.getAndIncrement()));
            }
        };
    }

    public static ExecutorService getPoolInst() {
        return EXECUTOR;
    }

    public static ScheduledThreadPoolExecutor getScheduledPoolInst() {
        return EXECUTOR;
    }

}
