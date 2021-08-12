package com.geniusver.util.objectstorage.impl;

import com.geniusver.util.objectstorage.Serializer;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * JacksonDeserializerTest
 *
 * @author GeniusV
 */
class JacksonDeserializerTest {
    @Test
    void deserialize() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        Serializer serializer = new JacksonSerializer();
        byte[] data = serializer.serialize(list);
        List res = new JacksonDeserializer().deserialize(data, List.class);
        System.out.println(res);
    }

}