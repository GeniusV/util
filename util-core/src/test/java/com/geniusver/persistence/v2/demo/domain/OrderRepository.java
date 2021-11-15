package com.geniusver.persistence.v2.demo.domain;

import com.geniusver.persistence.v2.Aggregate;
import com.geniusver.persistence.v2.Repository;
import com.geniusver.persistence.v2.demo.infra.OrderDao;
import com.geniusver.persistence.v2.demo.infra.OrderDo;
import com.geniusver.persistence.v2.demo.infra.OrderItemDao;
import com.geniusver.persistence.v2.demo.infra.OrderItemDo;

import java.util.List;

/**
 * OrderRepository
 *
 * @author GeniusV
 */
public class OrderRepository implements Repository<OrderId, Order> {
    private OrderDao orderDao;
    private OrderItemDao orderItemDao;

    @Override
    public Aggregate<Order> find(OrderId id) {
        Aggregate<Order> aggregate = new Aggregate();

        OrderDo orderDo = orderDao.query(id.value);
        List<OrderItemDo> orderItemDoList = orderItemDao.queryByOrderId(id.value);

        return null;
    }

    @Override
    public void save(Aggregate<Order> aggregate) {

    }
}
