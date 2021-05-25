package com.geniusver.util;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * InputStreamLineHandlerTest
 *
 * @author daniel.hua
 */
class ArthasOutputHandlerTest {
    @Test
    void processOneRow() {
        InputStream in = inputStream("test\n");
        ArthasOutputHandler handler = createLineHandler(line ->
                assertEquals("test", line)
        );

        handler.handle(in);
    }

    @Test
    void processNotFullRow() {

    }

    @Test
    void processManyRow() {
        InputStream in = inputStream("test1\ntest2\n");
        List<String> result = new ArrayList<>();
        ArthasOutputHandler handler = createLineHandler(result::add);

        handler.handle(in);

        assertEquals("test1", result.get(0));
        assertEquals("test2", result.get(1));
    }

    @Test
    void processVeryLongRow() {

    }

    private ArthasOutputHandler createLineHandler(Consumer<String> linesConsumer) {
        return new ArthasOutputHandler(
                '\n',
                ByteBuffer.allocate(32),
                32,
                System.out,
                linesConsumer,
                StandardCharsets.UTF_8
        );
    }

    private InputStream inputStream(String content) {
        return new ByteArrayInputStream(content.getBytes());
    }
}