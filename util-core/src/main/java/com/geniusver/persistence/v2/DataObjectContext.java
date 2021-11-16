package com.geniusver.persistence.v2;

import cn.hutool.core.lang.Assert;

import java.util.*;
import java.util.function.Function;

/**
 * DataObjectStore
 *
 * @author GeniusV
 */
public class DataObjectContext {
    private Map<Class, ClassBin> classBinMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T get(Object id, Class<T> type) {
        return (T) Optional.ofNullable(classBinMap.get(type))
                .map(classBin -> classBin.get(id))
                .orElse(null);
    }

    @SuppressWarnings("unchecked")
    public <T> Collection<T> getAll(Class<T> type) {
        return Optional.ofNullable(classBinMap.get(type))
                .map(ClassBin::getObjects)
                .orElse(Collections.emptyList());
    }

    public <T> DataObjectContext put(T dataObject, Class<T> clazz, Function<T, Object> idFunction) {
        Assert.notNull(dataObject, "data object cannot be null");
        Assert.notNull(clazz, "class cannot be null");
        Assert.notNull(idFunction, "id function cannot be null");

        put(dataObject, clazz, idFunction.apply(dataObject));
        return this;
    }

    public <T> DataObjectContext put(Collection<T> objectList, Class<T> clazz, Function<T, Object> idFunction) {
        Assert.notNull(objectList, "data object list cannot be null");
        Assert.notNull(clazz, "class cannot be null");
        Assert.notNull(idFunction, "id function cannot be null");
        objectList.forEach(dataObject -> put(dataObject, clazz, idFunction.apply(dataObject)));
        return this;
    }

    private void put(Object dataObject, Class clazz, Object id) {
        Assert.notNull(dataObject, "data object cannot be null");
        Assert.notNull(clazz, "class cannot be null");
        Assert.notNull(id, "id cannot be null");

        ClassBin classBin = classBinMap.get(clazz);
        if (classBin == null) {
            classBin = new ClassBin(clazz);
            classBinMap.put(clazz, classBin);
        }

        classBin.put(id, dataObject);
    }


    private class ClassBin<T> {
        private Class<T> type;
        private Map<Object, T> objectMap = new HashMap<>();

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

        public Collection<T> getObjects() {
            return objectMap.values();
        }
    }
}

