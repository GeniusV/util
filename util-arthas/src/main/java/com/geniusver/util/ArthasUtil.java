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

import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.system.SystemUtil;

import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Arthas
 *
 * @author GeniusV
 */
public class ArthasUtil {
    private static ArthasConfig config = null;

    /**
     * Execute arthas command and review result in real time
     * <p>
     * This method will return only when arthas init completed.
     *
     * @param command arthas command to execute
     */
    public static void executeCommand(String command) {
        executeCommand(command, System.out, Throwable::printStackTrace);
    }

    /**
     * Execute arthas command and review result in real time
     * <p>
     * This method will return only when arthas init completed.
     *
     * @param arthasCommand    arthas command to execute
     * @param outputStream     outputStream to write result
     * @param exceptionHandler handler to handle exception
     */
    public static void executeCommand(String arthasCommand,
                                      OutputStream outputStream,
                                      Consumer<Exception> exceptionHandler) {
        Assert.notBlank(arthasCommand, "arthasCommand cannot be empty");
        Assert.notNull(outputStream, "resultHandler cannot be null");
        Assert.notNull(exceptionHandler, "exceptionHandler cannot be null");
        if (config == null) {
            setConfig(new ArthasConfig());
        }
        CountDownLatch latch = new CountDownLatch(1);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,
                1,
                0L,
                TimeUnit.SECONDS,
                new SynchronousQueue<>());
        String finalCommand = StrFormatter.format("{} -jar \"{}\" {} -c \"{}\"",
                config.getJavaPath(),
                config.getArthasBootJarPath(),
                SystemUtil.getCurrentPID(),
                arthasCommand);
        threadPoolExecutor.submit(() -> {
            try {
                Process process = RuntimeUtil.exec(finalCommand);
                ArthasOutputHandler handler = new ArthasOutputHandler(
                        ByteBuffer.allocate(config.getBufferSize() * 2),
                        config.getBufferSize(),
                        outputStream,
                        StandardCharsets.UTF_8);
                handler.handle(process, latch);
            } catch (ArthasExecuteException e) {
                e.setArthasCommand(arthasCommand);
                e.setFinalCommand(finalCommand);
                exceptionHandler.accept(e);
            } catch (Exception e) {
                ArthasExecuteException ex = new ArthasExecuteException("arthas command execute failed", e);
                ex.setArthasCommand(arthasCommand);
                ex.setFinalCommand(finalCommand);
                exceptionHandler.accept(ex);
            }
        });
        threadPoolExecutor.shutdown();
        try {
            // wait until arthas process outputs "Affect(class-cnt"
            latch.await();
        } catch (InterruptedException e) {
            exceptionHandler.accept(e);
        }
    }

    public static ArthasConfig getConfig() {
        return config;
    }

    public synchronized static void setConfig(ArthasConfig config) {
        ArthasUtil.config = config;
    }
}
