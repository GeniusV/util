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

import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.io.resource.NoResourceException;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import cn.hutool.system.SystemUtil;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
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
     * @param arthasCommand    arthas command to execute
     * @param outputStream     outputStream to write result
     * @param exceptionHandler handler to handle exception
     */
    public static void executeArthas(String arthasCommand,
                                     OutputStream outputStream,
                                     Consumer<Exception> exceptionHandler) {
        Assert.notBlank(arthasCommand, "arthasCommand cannot be empty");
        Assert.notNull(outputStream, "resultHandler cannot be null");
        Assert.notNull(exceptionHandler, "exceptionHandler cannot be null");
        if (config == null) {
            config = new ArthasConfig();
        }
        CountDownLatch latch = new CountDownLatch(1);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,
                1,
                0L,
                TimeUnit.SECONDS,
                new SynchronousQueue<>());
        threadPoolExecutor.submit(() -> {
            try {
                String finalCommand = StrFormatter.format("{} -jar \"{}\" {} -c \"{}\"",
                        config.getJavaPath(),
                        config.getArthasBootJarPath(),
                        SystemUtil.getCurrentPID(), arthasCommand);
                Process process = RuntimeUtil.exec(finalCommand);
                InputStream inputStream = process.getInputStream();
                byte[] buff = new byte[8192];
                int len;
                FastByteArrayOutputStream analyzeOutputStream = new FastByteArrayOutputStream();
                while ((len = inputStream.read(buff)) > 0) {
                    outputStream.write(buff, 0, len);

                    // if arthas not started yet
                    if (latch.getCount() > 0) {
                        analyzeOutputStream.write(buff, 0, len);
                        String str = analyzeOutputStream.toString();
                        // read from inputStream, if shows affect, then we consider arthas init complete
                        if (!StrUtil.isEmpty(str) && str.contains("Affect(class-cnt")) {
                            latch.countDown();
                        }
                    }
                }
            } catch (Exception e) {
                exceptionHandler.accept(e);
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
}
