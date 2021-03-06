package com.geniusver.persistence.v2;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

/**
 * DataObjectContextTest
 *
 * @author GeniusV
 */
class DataObjectContextTest {


    @Test
    void put() {
        DataObjectContext context = new DataObjectContext();
        DataA dataA = TestDataFactory.createDataA(1L);
        DataB dataB = TestDataFactory.createDataB(1L);

        context.put(dataA, DataA.class, DataA::getId)
                .put(dataB, DataB.class, DataB::getId);

        DataA dataA1 = context.get(1L, DataA.class);
        DataB dataB1 = context.get(1L, DataB.class);

        assertEquals(dataA, dataA1);
        assertEquals(dataB, dataB1);
    }

    @Test
    void putAll() {
        DataObjectContext context = new DataObjectContext();
        List<DataA> dataAList = LongStream.range(0, 10)
                .mapToObj(TestDataFactory::createDataA)
                .collect(Collectors.toList());
        List<DataB> dataBList = LongStream.range(0, 10)
                .mapToObj(TestDataFactory::createDataB)
                .collect(Collectors.toList());

        context.put(dataAList, DataA.class, DataA::getId)
                .put((dataBList), DataB.class, DataB::getId);

        assertIterableEquals(dataAList, context.getAll(DataA.class));
        assertIterableEquals(dataBList, context.getAll(DataB.class));
    }


}