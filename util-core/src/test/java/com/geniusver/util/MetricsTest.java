package com.geniusver.util;

import cn.hutool.core.thread.ThreadUtil;
import org.junit.jupiter.api.Test;

/**
 * MetricsTest
 *
 * @author GeniusV
 */
class MetricsTest {
    @Test
    void testMetrix() {
        try (Metrics metrics = new Metrics("test").start();) {
            for (int t = 0; t < 3; t++) {
                metrics.count();
                metrics.count();
                metrics.count();
                metrics.count();
                metrics.count();
                metrics.count();
                metrics.count();
                metrics.count();
                metrics.count();
                metrics.count();
                metrics.count();
                ThreadUtil.sleep(1000L);
            }
        }
    }
}