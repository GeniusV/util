package com.geniusver.persistence.v2;

/**
 * Aggregate
 *
 * @author GeniusV
 */
public class Aggregate<R> {
    protected R rootEntity;
    protected DataObjectStore oldDataObjectStore;

    public R getRootEntity() {
        return rootEntity;
    }

    public DataObjectStore getOldDataObjectStore() {
        return oldDataObjectStore;
    }
}
