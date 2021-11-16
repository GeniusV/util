package com.geniusver.persistence.example.v2.infra;

import java.util.List;

/**
 * OrderItemDao
 *
 * @author GeniusV
 */
public interface OrderItemDao {
    List<OrderItemDo> queryByOrderId(Long value);

    OrderItemDo insert(OrderItemDo orderItemDo);

    OrderItemDo update(OrderItemDo orderItemDo);
}
