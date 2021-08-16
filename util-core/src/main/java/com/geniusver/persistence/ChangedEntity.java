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

package com.geniusver.persistence;

/**
 * ChangedEntity
 *
 * @author meixuesong
 * @author GeniusV
 */
public class ChangedEntity<T> {

    private final T oldEntity;
    private final T newEntity;

    public ChangedEntity(T oldEntity, T newEntity) {

        this.oldEntity = oldEntity;
        this.newEntity = newEntity;
    }

    public T getOldEntity() {
        return oldEntity;
    }

    public T getNewEntity() {
        return newEntity;
    }
}
