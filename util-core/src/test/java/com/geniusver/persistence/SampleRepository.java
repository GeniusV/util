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

import com.geniusver.persistence.deepcompare.DataObjectUtil;
import com.geniusver.persistence.v1.Aggregate;
import com.geniusver.persistence.v1.AggregateFactory;
import com.geniusver.persistence.v1.ChangedEntity;

import java.util.Collection;

/**
 * SampleRepository
 *
 * @author GeniusV
 */
public class SampleRepository {
    public Aggregate<SampleEntity> find(String id) {
        return AggregateFactory.createVersionalAggregate(new SampleEntity(), 1);
    }

    public String save(Aggregate<SampleEntity> aggregate) {
        if (aggregate.isNew()) {
            // insert all and get id
            return aggregate.getRoot().getId();
        }

        if (aggregate.isChanged()) {
            SampleEntity changeField = DataObjectUtil.getDelta(aggregate.getRootSnapshot(), aggregate.getRoot(), "children");
            // update not null field

            Collection<SampleEntity> newChildren = aggregate.findNewEntitiesById(SampleEntity::getChildren, SampleEntity::getId);
            newChildren.forEach(entity -> {
                // insert each entity
            });

            Collection<ChangedEntity<SampleEntity>> changedChildren = aggregate.findChangedEntitiesWithOldValues(SampleEntity::getChildren, SampleEntity::getId);
            for (ChangedEntity<SampleEntity> changeOldNewEntity : changedChildren) {
                SampleEntity changedValues = DataObjectUtil.getDelta(changeOldNewEntity.getOldEntity(), changeOldNewEntity.getNewEntity());
                // update not null values
            }

            Collection<SampleEntity> removedEntities = aggregate.findRemovedEntities(SampleEntity::getChildren, SampleEntity::getId);
            removedEntities.forEach(entity -> {
                // delete children
            });
        }

        // set aggreate root id
        return aggregate.getRoot().getId();
    }

    public void delete(String id) {
        // delete
    }
}
