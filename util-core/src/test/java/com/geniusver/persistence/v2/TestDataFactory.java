package com.geniusver.persistence.v2;

/**
 * com.geniusver.persistence.v2.TestDataFactory
 *
 * @author GeniusV
 */
public class TestDataFactory {
    public static DataA createDataA(Long id) {
        DataA dataA = new DataA();
        dataA.setId(id);
        dataA.setData1("dataA-" + id);
        dataA.setVersion(1L);
        return dataA;
    }

    public static DataB createDataB(Long id) {
        DataB dataB = new DataB();
        dataB.setId(id);
        dataB.setData2("dataB-" + id);
        dataB.setVersion(1L);
        return dataB;
    }
}
