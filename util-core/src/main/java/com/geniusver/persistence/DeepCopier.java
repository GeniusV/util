package com.geniusver.persistence;

/**
 * DeepCopier is used to deep copy object. Aggregate use it to create aggregate root snapshot.
 *
 * @author meixuesong
 * @author GeniusV
 */
public interface DeepCopier {
    /**
     * deep copy object
     *
     * @param object the object to be copy
     * @param <T>    the type
     * @return the new instance copy from object
     */
    <T> T copy(T object);
}
