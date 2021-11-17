package com.geniusver.persistence.v2;

/**
 * OldNew
 *
 * @author GeniusV
 */
public class OldNew<T> {
    private final T oldObject;
    private final T newObject;

    public OldNew(T oldObject, T newObject) {
        this.oldObject = oldObject;
        this.newObject = newObject;
    }

    public T getOldObject() {
        return oldObject;
    }

    public T getNewObject() {
        return newObject;
    }
}
