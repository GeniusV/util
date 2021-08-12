package com.geniusver.util.objectstorage.impl;

import com.geniusver.util.objectstorage.Serializer;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JacksonSerializerTest
 *
 * @author GeniusV
 */
class JacksonSerializerTest {

    @Test
    void serialize() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        Serializer serializer = new JacksonSerializer();
        byte[] data = serializer.serialize(list);
        System.out.println(new String(data));
    }
}