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
        assertIterableEquals(Collections.singletonList(updatedData), compare.toUpdateList());
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
        assertIterableEquals(Collections.singletonList(updatedData), compare.toUpdateList());
    }
}