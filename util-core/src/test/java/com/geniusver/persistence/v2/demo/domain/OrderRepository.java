package com.geniusver.persistence.v2.demo.domain;

import com.geniusver.persistence.v2.Aggregate;
import com.geniusver.persistence.v2.Repository;

/**
 * OrderRepository
 *
 * @author GeniusV
 */
public class OrderRepository implements Repository<Long, Order> {
    @Override
    public Aggregate<Order> find(Long id) {
        return null;
    }

    @Override
    public void save(Aggregate<Order> aggregate) {

    }
}
