package com.geniusver.persistence.v2;

/**
 * Aggregate
 *
 * @author GeniusV
 */
public class Aggregate<R> {
    protected R rootEntity;
    protected DataObjectContext dataObjectContext;

    public R getRootEntity() {
        return rootEntity;
    }

    public DataObjectContext getDataObjectContext() {
        return dataObjectContext;
    }
}
