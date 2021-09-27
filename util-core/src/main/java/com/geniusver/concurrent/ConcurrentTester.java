package com.geniusver.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.function.Function;

/**
 * ConcurrencyTester
 *
 * @author GeniusV
 */
public class ConcurrentTester {
    private final List<Task> taskList = new ArrayList<>();


    public ConcurrentTester addWorker(int num, Function<Integer, String> threadNameFunction, Runnable runnable) {
        if (num < 1) {
            throw new IllegalArgumentException("num must >= 1");
        }
        if (threadNameFunction == null) {
            throw new IllegalArgumentException("threadNameFunction must not be null");
        }
        if (runnable == null) {
            throw new IllegalArgumentException("runnable must not be null");
        }
        taskList.add(new Task(num, threadNameFunction, runnable));
        return this;
    }

    public void start() {
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
            throw new RuntimeException(e);
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
