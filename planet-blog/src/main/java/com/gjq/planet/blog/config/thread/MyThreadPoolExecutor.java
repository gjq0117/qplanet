package com.gjq.planet.blog.config.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: gjq0117
 * @date: 2024/4/22 17:45
 * @description: 利用装饰器模式  自定义ThreadPoolExecutor，目的是给线程池定义一个名字
 */
@Slf4j
public class MyThreadPoolExecutor extends ThreadPoolExecutor {


    /**
     * 线程池名字前缀
     */
    private final String threadNamePrefix;

    /**
     * 序号
     */
    private final AtomicInteger threadNum = new AtomicInteger(1);

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler, String threadNamePrefix) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        this.threadNamePrefix = threadNamePrefix;
    }

    @Override
    public void execute(Runnable command) {
        super.execute(wrapRunnable(command));
    }

    private Runnable wrapRunnable(final Runnable task) {
        return () -> {
            Thread.currentThread().setName(threadNamePrefix + threadNum.getAndIncrement());
            Thread.currentThread().setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
            task.run();
        };
    }
}
