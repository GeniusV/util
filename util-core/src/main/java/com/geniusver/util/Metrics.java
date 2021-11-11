package com.geniusver.util;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.thread.ThreadFactoryBuilder;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Metrics
 *
 * @author GeniusV
 */
public class Metrics implements AutoCloseable {
    private String name;
    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = null;
    private AtomicLong count = new AtomicLong();

    private int avgPeriodInSeconds = 10;

    private long[] bins = null;
    private int pos = 0;

    public Metrics(String name) {
        this.name = name;
    }

    public Metrics start() {
        init();
        scheduledThreadPoolExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                long current = count.getAndSet(0);
                bins[pos] = current;
                if (pos + 1 >= avgPeriodInSeconds) {
                    pos = 0;
                } else {
                    pos++;
                }

                long avgSum = 0;
                for (long bin : bins) {
                    avgSum += bin;
                }
                System.out.println(StrFormatter.format("{} current qps: {}, avg qps in {} seconds: {}", name, current, avgPeriodInSeconds, avgSum / avgPeriodInSeconds));
            }
        }, 1000L, 1000L, TimeUnit.MILLISECONDS);
        return this;
    }

    @Override
    public void close() {
        this.stop();
    }

    private void init() {
        if (this.scheduledThreadPoolExecutor != null) {
            return;
        }

        this.scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, new ThreadFactoryBuilder().setDaemon(true).setNamePrefix("metrics-" + name).build());
        this.bins = new long[this.avgPeriodInSeconds];
    }

    public void count() {
        this.count.incrementAndGet();
    }

    public void stop() {
        scheduledThreadPoolExecutor.shutdown();
        try {
            scheduledThreadPoolExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException("interrupted when waiting stop", e);
        }
    }

    public int getAvgPeriodInSeconds() {
        return avgPeriodInSeconds;
    }

    public void setAvgPeriodInSeconds(int avgPeriodInSeconds) {
        this.avgPeriodInSeconds = avgPeriodInSeconds;
    }
}
