package com.geniusver.util.objectstorage;

import java.util.List;

/**
 * ObjectStorage
 *
 * @author GeniusV
 */
public interface ObjectStorage {

    <T> T get(String topic, String id, Class<T> clazz);

    <T> List<ObjectWrapper<T>> find(String topic, String idPattern, Class<T> clazz);

    void save(String topic, String id, Object obj);

    void remove(String topic, String id);
}
