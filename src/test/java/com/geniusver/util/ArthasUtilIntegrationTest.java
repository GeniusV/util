package com.geniusver.util;

import cn.hutool.core.io.resource.NoResourceException;
import cn.hutool.setting.dialect.Props;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author GeniusV
 */
class ArthasUtilIntegrationTest {
    @BeforeAll
    static void beforeAll() {
        ArthasConfig config = new ArthasConfig();
        config.setJavaPath("java");
        config.setArthasBootJarPath("D:\\Program Files\\arthas-3.1.8-bin\\arthas-boot.jar");
        ArthasUtil.setConfig(config);
    }

    @Test
    void trace() throws ClassNotFoundException {
        Class.forName("com.geniusver.util.MathGame");
        ArthasUtil.executeCommand("trace com.geniusver.util.MathGame primeFactors");
        System.out.println("init ok");
        runMathGame(10);
    }


    @Test
    void testExecuteFailed() throws ClassNotFoundException, InterruptedException {
//        Class.forName("com.geniusver.util.MathGame");
        ArthasUtil.executeCommand("trace com.geniusver.util.MathGame primeFactors");
        runMathGame(10);
    }

    private void runMathGame(int times) {
        MathGame game = new MathGame();
        for (int i = 0; i < times; i++) {
            try {
                game.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void loadNonExistProperties() {
        assertThrows(NoResourceException.class, () -> {
            Props props = new Props("not exist");
        });
    }
}