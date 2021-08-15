/*
 * Copyright 2021 GeniusV
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.geniusver.util.objectstorage.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.StrFormatter;
import com.geniusver.util.objectstorage.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        try {
            File file = getFile(topic, id);
            if (!file.exists()) {
                return null;
            }
            byte[] data = FileUtil.readBytes(file);
            return deserializer.deserialize(data, clazz);
        } catch (Exception e) {
            throw new ObjectStorageException(StrFormatter.format("Failed to get topic '{}', id '{}' as {}",
                    topic, id, clazz.getName()), e);
        }
    }

    private File getFile(String topic, String id) {
        return Paths.get(this.basePath, topic, id + ".json").toFile();
    }

    @Override
    public <T> List<ObjectWrapper<T>> find(String topic, String idPattern, Class<T> clazz) {
        Assert.notEmpty(topic, "topic cannot be empty");
        Assert.notEmpty(idPattern, "idPattern cannot be empty");
        Assert.notNull(clazz, "class cannot be empty");

        Pattern pattern = Pattern.compile(idPattern);
        File[] files = new File(basePath, topic).listFiles((dir, name) -> {
            String id = name.replace(".json", "");
            return pattern.matcher(id).matches();
        });
        if (files == null) {
            return new ArrayList<>();
        }
        List<ObjectWrapper<T>> res = Arrays.stream(files).map(file -> {
            String id = file.getName().replace(".json", "");
            T obj = get(topic, id, clazz);
            return new ObjectWrapper<T>(topic, id, obj);
        }).collect(Collectors.toList());

        return res;
    }

    @Override
    public void save(String topic, String id, Object obj) {
        Assert.notEmpty(topic, "topic cannot be empty");
        Assert.notEmpty(id, "id cannot be empty");
        Assert.notNull(obj, "obj cannot be empty");
        try {
            File file = getFile(topic, id);
            byte[] data = serializer.serialize(obj);
            FileUtil.writeBytes(data, file);
        } catch (Exception e) {
            throw new ObjectStorageException(StrFormatter.format("Failed to save topic '{}', id '{}'", topic, id), e);
        }
    }

    @Override
    public void remove(String topic, String id) {
        Assert.notEmpty(topic, "topic cannot be empty");
        Assert.notEmpty(id, "id cannot be empty");
        File file = getFile(topic, id);
        if (file.exists()) {
            FileUtil.del(file);
        }
    }
}
