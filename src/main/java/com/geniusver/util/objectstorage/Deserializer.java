package com.geniusver.util.objectstorage;

/**
 * Deserializer
 *
 * @author GeniusV
 */
public interface Deserializer {
    <T> T deserialize(byte[] data, Class<T> clazz);
}
