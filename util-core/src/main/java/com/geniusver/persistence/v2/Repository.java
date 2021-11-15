package com.geniusver.persistence.v2;

/**
 * Repository
 *
 * @author GeniusV
 */
public interface Repository<ID, R> {

    Aggregate<R> find(ID id);

    void save(Aggregate<R> aggregate);
}
