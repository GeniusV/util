/*
 * Copyright 2021 GeniusV
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.geniusver.util;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

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
        RetryHandler<Integer, String> rh = new RetryHandler<Integer, String>(2, 0)
                .doTry(i -> {
                    tryTimes.getAndIncrement();
                    if (tryTimes.get() < 2) {
                        throw new RuntimeException("failed");
                    }
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
        assertEquals(2, tryTimes.get());
        assertEquals(1, catchTimes.get());
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
        AtomicInteger tryTimes = new AtomicInteger();
        AtomicInteger catchTimes = new AtomicInteger();
        AtomicInteger fallbackTimes = new AtomicInteger();
        RetryHandler<Integer, String> rh = new RetryHandler<Integer, String>(3, 0)
                .doTry(i -> {
                    tryTimes.getAndIncrement();
                    throw new RuntimeException("try failed");
                })
                .doCatch((e, i) -> {
                    catchTimes.getAndIncrement();
                    System.out.println(e.toString() + " " + i);
                    throw new RuntimeException("catch failed");
                })
                .fallback(i -> {
                    fallbackTimes.getAndIncrement();
                    return "2";
                });
        assertThrows(RuntimeException.class, () -> rh.handle(1));
        assertEquals(1, tryTimes.get());
        assertEquals(1, catchTimes.get());
        assertEquals(0, fallbackTimes.get());
    }

    @Test
    void fallbackFailed() {
        AtomicInteger tryTimes = new AtomicInteger();
        AtomicInteger catchTimes = new AtomicInteger();
        AtomicInteger fallbackTimes = new AtomicInteger();
        RetryHandler<Integer, String> rh = new RetryHandler<Integer, String>(2, 0)
                .doTry(i -> {
                    tryTimes.getAndIncrement();
                    throw new RuntimeException("try failed");
                })
                .doCatch((e, i) -> {
                    catchTimes.getAndIncrement();
                    System.out.println(e.toString() + " " + i);
                })
                .fallback(i -> {
                    fallbackTimes.getAndIncrement();
                    throw new RuntimeException("fallback failed");

                });
        assertThrows(RuntimeException.class, () -> rh.handle(1));
        assertEquals(3, tryTimes.get());
        assertEquals(3, catchTimes.get());
        assertEquals(1, fallbackTimes.get());
    }

    @Test
    void withoutFallback() {
        AtomicInteger tryTimes = new AtomicInteger();
        AtomicInteger catchTimes = new AtomicInteger();
        AtomicInteger fallbackTimes = new AtomicInteger();
        RetryHandler<Integer, String> rh = new RetryHandler<Integer, String>(2, 0)
                .doTry(i -> {
                    tryTimes.getAndIncrement();
                    throw new RuntimeException("try failed");
                })
                .doCatch((e, i) -> {
                    catchTimes.getAndIncrement();
                    System.out.println(e.toString() + " " + i);
                });
        assertThrows(RuntimeException.class, () -> rh.handle(1));
        assertEquals(3, tryTimes.get());
        assertEquals(3, catchTimes.get());
        assertEquals(0, fallbackTimes.get());
    }

    @Test
    void withoutFallbackReturnNull() {
        AtomicInteger tryTimes = new AtomicInteger();
        AtomicInteger catchTimes = new AtomicInteger();
        AtomicInteger fallbackTimes = new AtomicInteger();
        RetryHandler<Integer, String> rh = new RetryHandler<Integer, String>(2, 0)
                .doTry(i -> {
                    tryTimes.getAndIncrement();
                    throw new RuntimeException("try failed");
                })
                .doCatch((e, i) -> {
                    catchTimes.getAndIncrement();
                    System.out.println(e.toString() + " " + i);
                }).returnNull(true);
        String res = rh.handle(1);
        assertNull(res);
        assertEquals(3, tryTimes.get());
        assertEquals(3, catchTimes.get());
        assertEquals(0, fallbackTimes.get());
    }
}