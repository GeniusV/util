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

package com.geniusver.persistence.v1;

import com.geniusver.persistence.DeepCopier;
import com.geniusver.persistence.deepcompare.JavaUtilDeepComparator;
import com.geniusver.persistence.impl.KryoDeepCopier;

/**
 * The aggregate factory will create the aggregate.
 *
 * @author meixuesong
 * @author GeniusV
 */
public class AggregateFactory {
    private static DeepCopier copier = new KryoDeepCopier();

    private AggregateFactory() {
        throw new IllegalStateException("A factory class, please use static method");
    }

    /**
     * The factory method.
     *
     * @param <R>     The type of aggregate root
     * @param <V>     Version Type
     * @param root    The aggregate root
     * @param version The version of aggregate root, if root is new, set to null
     * @return the aggregate object
     */
    public static <R extends Entity, V> VersionalAggregate<R, V> createVersionalAggregate(R root, V version) {
        return new VersionalAggregate<>(root, version, copier, new JavaUtilDeepComparator());
    }


    public static <R extends Entity> Aggregate<R> createAggregate(R root) {
        return new Aggregate<>(root, copier, new JavaUtilDeepComparator());
    }

    /**
     * set deep copier.
     *
     * @param copier the deepcopier object
     */
    public static void setCopier(DeepCopier copier) {
        AggregateFactory.copier = copier;
    }
}
