package com.geniusver.util;

import cn.hutool.core.io.BufferUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.StrUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;
import java.util.function.BiConsumer;

/**
 * @author daniel.hua
 */
public class ArthasOutputHandler {
    private final BiConsumer<String, ArthasInitProcessContext> initLineConsumer = (line, context) -> {
        CountDownLatch latch = context.getInitSuccessLatch();
        Process process = context.getProcess();
        // if arthas not started yet
        if (latch.getCount() > 0) {
            // read from inputStream, if shows affect, then we consider arthas init completeA
            if (!StrUtil.isEmpty(line) && line.contains("Affect(class-cnt")) {
                latch.countDown();
            }
        }

        if (line.contains("No class or method is affected")) {
            latch.countDown();
            process.destroy();
            throw new ArthasExecuteException("Class not method not found. failed to execute command ");
        }
    };

    private ByteBuffer buffer;
    private int cacheSize;
    private OutputStream outputStream;
    private Charset charset;

    public ArthasOutputHandler(ByteBuffer buffer,
                               int cacheSize,
                               OutputStream outputStream,
                               Charset charset) {
        this.buffer = buffer;
        this.cacheSize = cacheSize;
        this.outputStream = outputStream;
        this.charset = charset;
    }


    public void handle(Process process, CountDownLatch initSuccessLatch) {
        InputStream in = process.getInputStream();
        try {
            byte[] buff = new byte[cacheSize];
            int len;
            while ((len = in.read(buff)) > 0) {
                // write to out
                this.outputStream.write(buff, 0, len);
                ensureBufferCapacity(len);
                // write buffer
                buffer.put(buff, 0, len);
                // flip to read
                buffer.flip();
                // readLines
                ArthasInitProcessContext initProcessContext = new ArthasInitProcessContext(process, initSuccessLatch);
                String line;
                while ((line = BufferUtil.readLine(buffer, charset)) != null) {
                    initLineConsumer.accept(line, initProcessContext);
                }
                buffer.compact();
            }
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    private void ensureBufferCapacity(int len) {
        while (len > buffer.remaining()) {
            expandBuffer();
        }
    }

    private void expandBuffer() {
        ByteBuffer newBuffer = ByteBuffer.allocate(this.buffer.capacity() * 2);
        newBuffer.put(this.buffer.array());
        this.buffer = newBuffer;
    }


    public ByteBuffer getBuffer() {
        return buffer;
    }

    public void setBuffer(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    private static class ArthasInitProcessContext {
        private Process process;
        private CountDownLatch initSuccessLatch;

        public ArthasInitProcessContext(Process process, CountDownLatch initSuccessLatch) {
            this.process = process;
            this.initSuccessLatch = initSuccessLatch;
        }

        public Process getProcess() {
            return process;
        }

        public CountDownLatch getInitSuccessLatch() {
            return initSuccessLatch;
        }
    }
}