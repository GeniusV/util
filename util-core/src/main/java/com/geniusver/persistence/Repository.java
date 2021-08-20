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
 * BasicRepository
 *
 * @author GeniusV
 */
public interface Repository<ID, E extends Entity> {
    Aggregate<E> createAggregate(E entity);

    Aggregate<E> find(ID id);

    void save(Aggregate<E> aggregate);

    void remove(ID id);
}
