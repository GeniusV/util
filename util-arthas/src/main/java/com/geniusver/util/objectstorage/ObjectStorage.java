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
