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

import cn.hutool.core.util.RandomUtil;
import com.geniusver.util.objectstorage.ObjectStorage;
import com.geniusver.util.objectstorage.ObjectWrapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LocalObjectStorageTest
 *
 * @author GeniusV
 */
@Disabled
class LocalObjectStorageTest {
    ObjectStorage storage = new LocalObjectStorage("D:\\java-projects\\util\\target",
            new JacksonSerializer(),
            new JacksonDeserializer());

    @Test
    void save() {

        List<Integer> list = Arrays.asList(1, 2, 3);
        storage.save("test", "1", list);
    }


    @Test
    void get() {
        List<Integer> res = storage.get("test", "1", List.class);
        System.out.println(res);
    }


    @Test
    void find() {
        List<ObjectWrapper<Map>> res = storage.find("test", ".*2.*", Map.class);
        res.forEach(ow -> {
            System.out.println(ow.getId());
        });
    }

    @Test
    void remove() {
        storage.remove("test", "2");
        System.out.println(storage.get("test", "2", Map.class));
    }

    @Test
    void saveBenchmark() {
        Map<String, String> dataMap = new HashMap<>();
        int n = 100;
        for (int i = 0; i < n; i++) {
            dataMap.put(RandomUtil.randomString(10), RandomUtil.randomString(10));
        }
        long t = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            storage.save("test", String.valueOf(i), dataMap);
        }
        System.out.println(System.currentTimeMillis() - t);
    }

    @Test
    void readBenchmark() {
        long t = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            storage.get("test", String.valueOf(i), Map.class);
        }
        System.out.println(System.currentTimeMillis() - t);
    }
}