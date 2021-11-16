package com.geniusver.persistence.v2;

import cn.hutool.core.lang.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * DataObjectStore
 *
 * @author GeniusV
 */

// TODO: 11/15/2021
public class DataObjectContext {
    private Map<Class, ClassBin> classBinMap = new HashMap<>();

    public <T> T get(Object id, Class<T> type) {
        return null;
    }

    public <T> List<T> getList(Class<T> type) {
        return null;
    }


    public <T> void put(Object object, Class<T> clazz, Function<T, Object> idFunction) {
        Assert.notNull(object);
        Assert.notNull(clazz);
        Assert.notNull(idFunction);

        // if object is collection, for each save
    }

    private void put(Object object, Class clazz, Object id) {
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

