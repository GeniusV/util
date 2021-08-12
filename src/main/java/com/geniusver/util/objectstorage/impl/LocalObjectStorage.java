package com.geniusver.util.objectstorage.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import com.geniusver.util.objectstorage.Deserializer;
import com.geniusver.util.objectstorage.ObjectStorage;
import com.geniusver.util.objectstorage.ObjectWrapper;
import com.geniusver.util.objectstorage.Serializer;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

/**
 * LocalObjectStorage
 *
 * @author GeniusV
 */
public class LocalObjectStorage implements ObjectStorage {
    private String basePath;
    private Serializer serializer;
    private Deserializer deserializer;

    public LocalObjectStorage(String basePath, Serializer serializer, Deserializer deserializer) {
        this.basePath = basePath;
        this.serializer = serializer;
        this.deserializer = deserializer;
        init();
    }

    private void init() {
        Assert.notEmpty(this.basePath, "basic path cannot be empty");
        Assert.notNull(serializer, "serializer cannot be null");
        Assert.notNull(deserializer, "deserializer cannot be empty");
        Assert.isFalse(FileUtil.exist(this.basePath) && FileUtil.isFile(this.basePath), "{} cannot be used as basic path", this.basePath);

        this.basePath = FileUtil.normalize(this.basePath);

        if (!FileUtil.exist(this.basePath)) {
            FileUtil.mkdir(basePath);
        }
    }

    @Override
    public <T> T get(String topic, String id, Class<T> clazz) {
        Assert.notEmpty(topic, "topic cannot be empty");
        Assert.notEmpty(id, "id cannot be empty");
        Assert.notNull(clazz, "class cannot be empty");
        File file = getFile(topic, id);
        if (!file.exists()) {
            return null;
        }
        byte[] data = FileUtil.readBytes(file);
        return deserializer.deserialize(data, clazz);
    }

    private File getFile(String topic, String id) {
        return Paths.get(this.basePath, topic, id + ".json").toFile();
    }

    @Override
    public <T> List<ObjectWrapper<T>> find(String topic, String idPattern, Class<T> clazz) {
        Assert.notEmpty(topic, "topic cannot be empty");
        Assert.notEmpty(idPattern, "idPattern cannot be empty");
        Assert.notNull(clazz, "class cannot be empty");
        return null;
    }

    @Override
    public void save(String topic, String id, Object obj) {
        Assert.notEmpty(topic, "topic cannot be empty");
        Assert.notEmpty(id, "id cannot be empty");
        Assert.notNull(obj, "obj cannot be empty");
        File file = getFile(topic, id);
        byte[] data = serializer.serialize(obj);
        FileUtil.writeBytes(data, file);
    }

    @Override
    public void remove(String topic, String id) {
        Assert.notEmpty(topic, "topic cannot be empty");
        Assert.notEmpty(id, "id cannot be empty");
    }
}
