package com.geniusver.persistence.example.v2.domain.repo;

import cn.hutool.core.text.StrFormatter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * AbstractHashMapDao
 *
 * @author GeniusV
 */
public class GeneralHashMapDao<ID, T> {
    protected boolean printLogs;
    protected ConcurrentHashMap<ID, T> map = new ConcurrentHashMap<>();
    protected Consumer<T> beforeInsert;
    protected BiConsumer<T, GeneralHashMapDao<ID, T>> beforeUpdate;
    protected Function<T, ID> getId;
    protected String name;

    public GeneralHashMapDao(boolean printLogs,
                             Consumer<T> beforeInsert,
                             BiConsumer<T, GeneralHashMapDao<ID, T>> beforeUpdate,
                             Function<T, ID> getId,
                             String name) {
        this.printLogs = printLogs;
        this.beforeInsert = beforeInsert;
        this.beforeUpdate = beforeUpdate;
        this.getId = getId;
        this.name = name;
    }

    public T query(ID id) {
        return map.get(id);
    }

    public T insert(T dataObject) {
        if (beforeInsert != null) {
            beforeInsert.accept(dataObject);
        }

        ID id = getId.apply(dataObject);

        if (printLogs) {
            System.out.println(StrFormatter.format("{}: insert {} -> {}", name, id, dataObject));
        }

        map.put(id, dataObject);
        return dataObject;
    }

    public T update(T dataObject) {
        ID id = getId.apply(dataObject);
        T oldObject = map.get(id);

        if (oldObject == null) {
            throw new RuntimeException(StrFormatter.format("data object not found for id '{}'", id));
        }

        if (beforeUpdate != null) {
            beforeUpdate.accept(dataObject, this);
        }

        System.out.println(StrFormatter.format("OrderDao: update {} -> {}", id, dataObject));
        map.put(id, dataObject);
        return dataObject;
    }

    public boolean isPrintLogs() {
        return printLogs;
    }

    public void setPrintLogs(boolean printLogs) {
        this.printLogs = printLogs;
    }

    public ConcurrentHashMap<ID, T> getMap() {
        return map;
    }

    public void setMap(ConcurrentHashMap<ID, T> map) {
        this.map = map;
    }

    public Consumer<T> getBeforeInsert() {
        return beforeInsert;
    }

    public void setBeforeInsert(Consumer<T> beforeInsert) {
        this.beforeInsert = beforeInsert;
    }

    public BiConsumer<T, GeneralHashMapDao<ID, T>> getBeforeUpdate() {
        return beforeUpdate;
    }

    public void setBeforeUpdate(BiConsumer<T, GeneralHashMapDao<ID, T>> beforeUpdate) {
        this.beforeUpdate = beforeUpdate;
    }

    public Function<T, ID> getGetId() {
        return getId;
    }

    public void setGetId(Function<T, ID> getId) {
        this.getId = getId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
