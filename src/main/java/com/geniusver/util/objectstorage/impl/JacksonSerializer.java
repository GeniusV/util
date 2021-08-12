package com.geniusver.util.objectstorage.impl;

import cn.hutool.core.text.StrFormatter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geniusver.util.objectstorage.Serializer;

/**
 * JacksonSerializer
 *
 * @author GeniusV
 */
public class JacksonSerializer implements Serializer {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(Object obj) {
        try {
            return objectMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(StrFormatter.format("Failed to serialize object {}", obj));
        }
    }
}
