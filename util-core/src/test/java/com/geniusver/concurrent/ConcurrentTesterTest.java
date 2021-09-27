package com.geniusver.concurrent;

import org.junit.jupiter.api.Test;

/**
 * ConcurrentTesterTest
 *
 * @author GeniusV
 */
class ConcurrentTesterTest {
    @Test
    void test() {
        new ConcurrentTester()
                .addWorker(3, integer -> "test1-" + integer, () -> System.out.println(Thread.currentThread().getName()))
                .addWorker(3, integer -> "test2-" + integer, () -> System.out.println(Thread.currentThread().getName()))
                .addWorker(3, integer -> "test3-" + integer, () -> System.out.println(Thread.currentThread().getName()))
                .addWorker(3, integer -> "test4-" + integer, () -> System.out.println(Thread.currentThread().getName()))
                .addWorker(3, integer -> "test5-" + integer, () -> System.out.println(Thread.currentThread().getName()))
                .addWorker(3, integer -> "test6-" + integer, () -> System.out.println(Thread.currentThread().getName()))
                .addWorker(3, integer -> "test7-" + integer, () -> System.out.println(Thread.currentThread().getName()))
                .start();
    }
}