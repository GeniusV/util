package com.geniusver.util;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.thread.ThreadUtil;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * RetryHandler
 *
 * @author GeniusV
 */
public class RetryHandler<T, R> {
    private final int totalTryTimes;
    private final long retryInterval;
    private Function<T, R> handler;
    private BiConsumer<Exception, T> exceptionHandler;
    private Function<T, R> fallbackHandler;

    public RetryHandler(int totalTryTimes, long retryInterval) {
        Assert.isTrue(totalTryTimes >= 0);
        Assert.isTrue(retryInterval >= 0);
        this.totalTryTimes = totalTryTimes + 1;
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
