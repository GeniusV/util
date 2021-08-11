package com.geniusver.util;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.thread.ThreadUtil;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * RetryHandler <p>
 *
 * Example: <code><pre>
 * RetryHandler<Integer, String> rh = new RetryHandler<Integer, String>(3, 1000L)
 *             .doTry(i -> {
 *                 return "1";
 *             })
 *             .doCatch((e, i) -> {
 *                 System.out.println(e.toString() + " " + i);
 *             })
 *             .fallback(i -> {
 *                 return "2";
 *             });
 * String res = rh.handle(1);
 * </pre></code>
 *
 * If doTry lambda throws an exception, the exception can be handled in doCatch lambda.
 * Then current thread will sleep 1000ms and re-execute doTry lambda up to 3 time(total execute 4 times).
 * When re-execute doTry lambda for the third time and still throws an exception, fallback lambda will be called.<p>
 *
 * If any exception is thrown from doCatch and fallback lambda will not be caught by RetryHandler. This is useful if
 * the exception from doTry lambda is not recoverable (like ClassNotFoundException), you can directly throw the exception
 * or wrap it to stop whole process. <p>
 *
 * This class IS NOT thread-safe, so DO NOT use it between threads. <p>
 *
 * @author GeniusV
 */
public class RetryHandler<T, R> {
    private final int totalTryTimes;
    private final long retryInterval;
    private Function<T, R> handler;
    private BiConsumer<Exception, T> exceptionHandler;
    private Function<T, R> fallbackHandler;

    public RetryHandler(int retryTimes, long retryInterval) {
        Assert.isTrue(retryTimes >= 0, "retryTimes must >= 0");
        Assert.isTrue(retryInterval >= 0, "retryInterval must >= 0");
        this.totalTryTimes = retryTimes + 1;
        this.retryInterval = retryInterval;
    }

    public RetryHandler<T, R> doTry(Function<T, R> handler) {
        Assert.notNull(handler, "handler cannot be null");
        this.handler = handler;
        return this;
    }

    public RetryHandler<T, R> doCatch(BiConsumer<Exception, T> handler) {
        Assert.notNull(handler, "handler cannot be null");
        this.exceptionHandler = handler;
        return this;
    }

    public RetryHandler<T, R> fallback(Function<T, R> handler) {
        Assert.notNull(handler, "handler cannot be null");
        this.fallbackHandler = handler;
        return this;
    }

    public R handle(T param) {
        Assert.notNull(handler, "handler cannot be null");
        Assert.notNull(exceptionHandler, "exceptionHandler cannot be null");
        int currentProcessTimes = 1;
        R res = null;
        while (currentProcessTimes <= totalTryTimes) {
            Exception ex = null;
            try {
                res = handler.apply(param);
                return res;
            } catch (Exception e) {
                ex = e;
            }
            exceptionHandler.accept(ex, param);
            if (currentProcessTimes < totalTryTimes && retryInterval > 0) {
                ThreadUtil.sleep(retryInterval);
            }
            currentProcessTimes++;
        }
        if (fallbackHandler != null) {
            res = fallbackHandler.apply(param);
        }
        return res;
    }

    public int getTotalTryTimes() {
        return totalTryTimes;
    }

    public long getRetryInterval() {
        return retryInterval;
    }

    public Function<T, R> getHandler() {
        return handler;
    }

    public BiConsumer<Exception, T> getExceptionHandler() {
        return exceptionHandler;
    }

    public Function<T, R> getFallbackHandler() {
        return fallbackHandler;
    }
}
