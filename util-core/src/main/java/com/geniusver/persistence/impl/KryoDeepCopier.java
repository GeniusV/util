/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2021 the original author or authors.
 */

package com.geniusver.persistence.impl;


import com.esotericsoftware.kryo.Kryo;
import com.geniusver.persistence.DeepCopier;

/**
 * KyroDeepCopier
 *
 * @author GeniusV
 */
public class KryoDeepCopier implements DeepCopier {
    private static final ThreadLocal<Kryo> kryo = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.setRegistrationRequired(false);
        return kryo;
    });

    private final DeepCopier fallbackDeepCopier = new SerializableDeepCopier();


    /**
     * deep copy object
     *
     * @param object the object to be copy
     * @return the new instance copy from object
     */
    @Override
    public <T> T copy(T object) {
        try {
            return kryo.get().copy(object);
        } catch (Exception e) {
            e.printStackTrace();
            return fallbackDeepCopier.copy(object);
        }
    }
}
