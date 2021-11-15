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


import com.geniusver.persistence.DeepComparator;
import com.geniusver.persistence.DeepCopier;

public class VersionalAggregate<R extends Entity, V> extends Aggregate<R> {
    protected V version;

    VersionalAggregate(R root, V version, DeepCopier copier, DeepComparator deepComparator) {
        super(root, copier, deepComparator);
        this.version = version;
    }

    public V getVersion() {
        return version;
    }

    public void setVersion(V version) {
        this.version = version;
    }

    /**
     * Whether it is a new aggregate.
     *
     * @return true if it's new
     */
    @Override
    public boolean isNew() {
        return this.version == null;
    }
}
