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

package com.geniusver.persistence.v2;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

/**
 * CompareUtilTest
 *
 * @author GeniusV
 */
class CompareUtilTest {

    @Test
    void compare() {
        List<DataA> oldList = LongStream.range(0, 10)
                .mapToObj(TestDataFactory::createDataA)
                .collect(Collectors.toList());
        List<DataA> newList = LongStream.range(0, 10)
                .mapToObj(TestDataFactory::createDataA)
                .collect(Collectors.toList());

        DataA updatedData = newList.get(5);
        updatedData.setData1("Updated Data");

        DataA newData = TestDataFactory.createDataA(100L);
        newList.add(newData);

        CompareResult<DataA> compare = CompareUtil.compare(oldList, newList, DataA::getId);
        assertIterableEquals(Collections.singletonList(newData), compare.toInsertList());
        assertIterableEquals(Collections.singletonList(updatedData), compare.toUpdateList().stream()
                .map(OldNew::getNewObject)
                .collect(Collectors.toList()));
    }

    @Test
    void compareWithUpdateOnly() {
        List<DataA> oldList = LongStream.range(0, 10)
                .mapToObj(TestDataFactory::createDataA)
                .collect(Collectors.toList());
        List<DataA> newList = LongStream.range(0, 10)
                .mapToObj(TestDataFactory::createDataA)
                .collect(Collectors.toList());

        DataA updatedData = newList.get(5);
        updatedData.setData1("Updated Data");

        CompareResult<DataA> compare = CompareUtil.compare(oldList, newList, DataA::getId);
        assertIterableEquals(Collections.emptyList(), compare.toInsertList());
        assertIterableEquals(Collections.singletonList(updatedData), compare.toUpdateList().stream()
                .map(OldNew::getNewObject)
                .collect(Collectors.toList()));
    }
}