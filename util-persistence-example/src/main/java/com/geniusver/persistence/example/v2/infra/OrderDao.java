package com.geniusver.persistence.example.v2.infra;

/**
 * OrderDao
 *
 * @author GeniusV
 */
public interface OrderDao {
    OrderDo query(Long value);

    OrderDo insert(OrderDo orderDo);

    OrderDo update(OrderDo orderDo);
}
