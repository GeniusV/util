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