package com.geniusver.concurrent;

import cn.hutool.core.lang.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.function.Function;

/**
 * ConcurrencyTester
 * <p>
 * Example:
 * <pre><code>
 *     new ConcurrentTester()
 *         .addWorker(2, integer -> "test1-" + integer, () -> System.out.println(Thread.currentThread().getName()))
 *         .addWorker(3, integer -> "test7-" + integer, () -> System.out.println(Thread.currentThread().getName()))
 *         .start();
 * </code></pre>
 *
 * @author GeniusV
 */
public class ConcurrentTester {
    private final List<Task> taskList = new ArrayList<>();

    /**
     * Add test logic
     *
     * @param num                thread number
     * @param threadNameFunction function to get thread name, the integer is thread index.
     * @param runnable           runnable
     * @return ConcurrentTester
     */
    public ConcurrentTester addWorker(int num, Function<Integer, String> threadNameFunction, Runnable runnable) {
        Assert.isTrue(num >= 1, "num must >= 1");
        Assert.notNull(threadNameFunction, "threadNameFunction must not be null");
        Assert.notNull(runnable, "runnable must not be null");
        taskList.add(new Task(num, threadNameFunction, runnable));
        return this;
    }

    /**
     * Start tests.
     *
     * @throws InterruptedRuntimeException throw if thread is interrupted when waiting for work thread complete.
     */
    public void start() {
        if (taskList.isEmpty()) {
            return;
        }
        int totalThreadNum = taskList.stream().mapToInt(task -> task.num).sum();
        CyclicBarrier barrier = new CyclicBarrier(totalThreadNum);
        CountDownLatch countDownLatch = new CountDownLatch(totalThreadNum);
        for (Task task : taskList) {
            for (int i = 0; i < task.num; i++) {
                Thread thread = new Thread(new WorkerWrapper(task, countDownLatch, barrier),
                        task.threadNameFunction.apply(i));
                thread.start();
            }
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new InterruptedRuntimeException(e);
        }
    }


    private static class Task {
        private final Integer num;
        private final Function<Integer, String> threadNameFunction;
        private final Runnable runnable;

        public Task(Integer num, Function<Integer, String> threadNameFunction, Runnable runnable) {
            this.num = num;
            this.threadNameFunction = threadNameFunction;
            this.runnable = runnable;
        }
    }

    private static class WorkerWrapper implements Runnable {
        private final Task task;
        private final CountDownLatch latch;
        private final CyclicBarrier barrier;

        public WorkerWrapper(Task task, CountDownLatch latch, CyclicBarrier barrier) {
            this.task = task;
            this.latch = latch;
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                barrier.await();
                task.runnable.run();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }
    }

}
