package com.geniusver.persistence.v2;

/**
 * Aggregate
 *
 * @author GeniusV
 */
public class Aggregate<R> {
    protected R root;
    protected DataObjectContext dataObjectContext = new DataObjectContext();

    public Aggregate() {
    }

    public Aggregate(R root) {
        this.root = root;
    }

    public R getRoot() {
        return root;
    }

    public void setRoot(R root) {
        this.root = root;
    }

    public DataObjectContext getDataObjectContext() {
        return dataObjectContext;
    }


}
