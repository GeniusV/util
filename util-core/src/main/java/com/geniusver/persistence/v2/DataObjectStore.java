package com.geniusver.persistence.v2;

import cn.hutool.core.lang.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * DataObjectStore
 *
 * @author GeniusV
 */
public class DataObjectStore {
    private Map<Class, ClassBin> classBinMap = new HashMap<>();

    public void put(Object object, Class clazz, Object id) {
        Assert.notNull(object);
        Assert.notNull(clazz);
        Assert.notNull(id);
    }


    private class ClassBin<T> {
        private Class<T> type;
        private Map<Object, T> objectMap;

        public ClassBin(Class<T> type) {
            Assert.notNull(type);
            this.type = type;
        }

        public void put(Object id, T object) {
            Assert.notNull(id);
            Assert.notNull(object);
            Assert.isInstanceOf(type, object);

            objectMap.put(id, object);
        }

        public T get(Object id) {
            Assert.notNull(id);
            return objectMap.get(id);
        }
    }
}

