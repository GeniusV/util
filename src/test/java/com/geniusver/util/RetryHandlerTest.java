package com.geniusver.util;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * RetryHandlerTest
 *
 * @author GeniusV
 */
class RetryHandlerTest {

    @Test
    void normalFullHandle() {
        AtomicInteger tryTimes = new AtomicInteger();
        AtomicInteger catchTimes = new AtomicInteger();
        AtomicInteger fallbackTimes = new AtomicInteger();
        RetryHandler<Integer, String> rh = new RetryHandler<Integer, String>(0, 0)
                .doTry(i -> {
                    tryTimes.getAndIncrement();
                    return "1";
                })
                .doCatch((e, i) -> {
                    catchTimes.getAndIncrement();
                    System.out.println(e.toString() + " " + i);
                })
                .fallback(i -> {
                    fallbackTimes.getAndIncrement();
                    return "2";
                });
        String res = rh.handle(1);
        assertEquals("1", res);
        assertEquals(1, tryTimes.get());
        assertEquals(0, catchTimes.get());
        assertEquals(0, fallbackTimes.get());
    }

    @Test
    void normalHandleWithoutFallback() {
        AtomicInteger tryTimes = new AtomicInteger();
        AtomicInteger catchTimes = new AtomicInteger();
        AtomicInteger fallbackTimes = new AtomicInteger();
        RetryHandler<Integer, String> rh = new RetryHandler<Integer, String>(0, 0)
                .doTry(i -> {
                    tryTimes.getAndIncrement();
                    return "1";
                })
                .doCatch((e, i) -> {
                    catchTimes.getAndIncrement();
                    System.out.println(e.toString() + " " + i);
                });
        String res = rh.handle(1);
        assertEquals("1", res);
        assertEquals(1, tryTimes.get());
        assertEquals(0, catchTimes.get());
        assertEquals(0, fallbackTimes.get());
    }

    @Test
    void withoutHandler() {
        AtomicInteger tryTimes = new AtomicInteger();
        AtomicInteger catchTimes = new AtomicInteger();
        AtomicInteger fallbackTimes = new AtomicInteger();
        RetryHandler<Integer, String> rh = new RetryHandler<Integer, String>(0, 0)
                .doCatch((e, i) -> {
                    catchTimes.getAndIncrement();
                    System.out.println(e.toString() + " " + i);
                })
                .fallback(i -> {
                    fallbackTimes.getAndIncrement();
                    return "2";
                });
        assertThrows(IllegalArgumentException.class, () -> rh.handle(1));
        assertEquals(0, tryTimes.get());
        assertEquals(0, catchTimes.get());
        assertEquals(0, fallbackTimes.get());
    }

    @Test
    void withoutExceptionHandler() {
        AtomicInteger tryTimes = new AtomicInteger();
        AtomicInteger catchTimes = new AtomicInteger();
        AtomicInteger fallbackTimes = new AtomicInteger();
        RetryHandler<Integer, String> rh = new RetryHandler<Integer, String>(0, 0)
                .doTry(i -> {
                    tryTimes.getAndIncrement();
                    return "1";
                })
                .fallback(i -> {
                    fallbackTimes.getAndIncrement();
                    return "2";
                });
        assertThrows(IllegalArgumentException.class, () -> rh.handle(1));
        assertEquals(0, tryTimes.get());
        assertEquals(0, catchTimes.get());
        assertEquals(0, fallbackTimes.get());
    }

    @Test
    void retryCanRecover() {
        AtomicInteger tryTimes = new AtomicInteger();
        AtomicInteger catchTimes = new AtomicInteger();
        AtomicInteger fallbackTimes = new AtomicInteger();
        RetryHandler<Integer, String> rh = new RetryHandler<Integer, String>(0, 0)
                .doTry(i -> {
                    tryTimes.getAndIncrement();
                    return "1";
                })
                .doCatch((e, i) -> {
                    catchTimes.getAndIncrement();
                    System.out.println(e.toString() + " " + i);
                })
                .fallback(i -> {
                    fallbackTimes.getAndIncrement();
                    return "2";
                });
        String res = rh.handle(1);
        assertEquals("1", res);
        assertEquals(1, tryTimes.get());
        assertEquals(0, catchTimes.get());
        assertEquals(0, fallbackTimes.get());
    }

    @Test
    void retryCannotRecover() {
        AtomicInteger tryTimes = new AtomicInteger();
        AtomicInteger catchTimes = new AtomicInteger();
        AtomicInteger fallbackTimes = new AtomicInteger();
        RetryHandler<Integer, String> rh = new RetryHandler<Integer, String>(2, 0)
                .doTry(i -> {
                    tryTimes.getAndIncrement();
                    throw new RuntimeException("failed");
                })
                .doCatch((e, i) -> {
                    catchTimes.getAndIncrement();
                    System.out.println(e.toString() + " " + i);
                })
                .fallback(i -> {
                    fallbackTimes.getAndIncrement();
                    return "2";
                });
        String res = rh.handle(1);
        assertEquals("2", res);
        assertEquals(3, tryTimes.get());
        assertEquals(3, catchTimes.get());
        assertEquals(1, fallbackTimes.get());
    }

    @Test
    void exceptionWhenHandleException() {

    }

    @Test
    void exceptionWhenFallback() {

    }
}