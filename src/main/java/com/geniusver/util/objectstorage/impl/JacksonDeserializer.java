package com.geniusver.util.objectstorage.impl;

import cn.hutool.core.text.StrFormatter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geniusver.util.objectstorage.Deserializer;
import com.geniusver.util.objectstorage.Serializer;

import java.io.IOException;

/**
 * JacksonSerializer
 *
 * @author GeniusV
 */
public class JacksonDeserializer implements Deserializer {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        try {
            return objectMapper.readValue(data, clazz);
        } catch (IOException e) {
            throw new IllegalStateException(StrFormatter.format("Failed to parse {} to {}", new String(data), clazz.getName()));
        }
    }
}
