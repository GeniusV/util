package com.geniusver.util;

import cn.hutool.core.io.BufferUtil;
import cn.hutool.core.io.IORuntimeException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.function.Consumer;

/**
 *
 *
 * @author daniel.hua
 */
public class ArthasOutputHandler {
    private Character lineBreaker;
    private ByteBuffer buffer;
    private int cacheSize;
    private OutputStream outputStream;
    private Consumer<String> linesConsumer;
    private Charset charset;


    public ArthasOutputHandler(Character lineBreaker,
                               ByteBuffer buffer,
                               int cacheSize,
                               OutputStream outputStream,
                               Consumer<String> linesConsumer,
                               Charset charset) {
        this.lineBreaker = lineBreaker;
        this.buffer = buffer;
        this.cacheSize = cacheSize;
        this.outputStream = outputStream;
        this.linesConsumer = linesConsumer;
        this.charset = charset;
    }

    public void handle(InputStream in) {
        try {
            byte[] buff = new byte[cacheSize];
            int len;
            while ((len = in.read(buff)) > 0) {
                // write to out
                this.outputStream.write(buff, 0, len);
                ensureBufferCapacity(len);
                // write buffer
                buffer.put(buff, 0, len);
                // readLines
                String line;
                while ((line = BufferUtil.readLine(buffer, charset)) != null) {
                    linesConsumer.accept(line);
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
        newBuffer.put(this.buffer);
        this.buffer = newBuffer;
    }


    public Character getLineBreaker() {
        return lineBreaker;
    }

    public void setLineBreaker(Character lineBreaker) {
        this.lineBreaker = lineBreaker;
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

    public Consumer<String> getLinesConsumer() {
        return linesConsumer;
    }

    public void setLinesConsumer(Consumer<String> linesConsumer) {
        this.linesConsumer = linesConsumer;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }
}