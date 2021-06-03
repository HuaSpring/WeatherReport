package com.hua.weather.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author by Spring. 2021/05/28 17:57
 */
public class ThreadPool<T> {
    private static ThreadPoolExecutor executorService;
    private static ThreadPool mInstance;


    public static ThreadPool getInstance() {
        if (mInstance == null) {
            synchronized (ThreadPool.class) {
                if (mInstance == null) {
                    mInstance = new ThreadPool();
                }
            }
        }
        return mInstance;
    }


    private ThreadPool() {
        int poolSize = Runtime.getRuntime().availableProcessors() * 2;
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(512);
        RejectedExecutionHandler policy = new ThreadPoolExecutor.DiscardPolicy();

        executorService = new ThreadPoolExecutor(poolSize, poolSize,
                0, TimeUnit.SECONDS,
                queue,
                policy);
    }


    public Future<?> submit(Runnable task) {
        return executorService.submit(task);
    }

    public Future<T> submit(Callable<T> task) {
        return executorService.submit(task);
    }


    public <T> Future<T> submit(Runnable task, T result) {
        if (task == null) throw new NullPointerException();

        return executorService.submit(task, result);
    }


}
