package com.geniusver.persistence.example.v2.domain;

import com.geniusver.persistence.example.v2.infra.OrderDao;
import com.geniusver.persistence.example.v2.infra.OrderDo;
import com.geniusver.persistence.example.v2.infra.OrderItemDao;
import com.geniusver.persistence.example.v2.infra.OrderItemDo;
import com.geniusver.persistence.v2.Aggregate;
import com.geniusver.persistence.v2.CompareResult;
import com.geniusver.persistence.v2.CompareUtil;
import com.geniusver.persistence.v2.Repository;

import java.util.Collections;
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

        aggregate.getOldDataObjectStore().put(orderDo, OrderDo.class, null);
        aggregate.getOldDataObjectStore().put(orderItemDoList, OrderItemDo.class, null);

        // construct entity
        return null;
    }

    @Override
    public void save(Aggregate<Order> aggregate) {
        // convert entity to do
        OrderDo orderDo = null;
        List<OrderItemDo> orderItemDoList;

        CompareResult<OrderDo> orderDoResult = CompareUtil.compare(aggregate.getOldDataObjectStore().getList(OrderDo.class),
                Collections.singletonList(orderDo),
                OrderDo::getId);

        List<OrderDo> toInsertList = orderDoResult.toInsertList();
        List<OrderDo> toUpdateList = orderDoResult.toUpdateList();

        //update or insert

//        CompareResult<OrderDo> orderItemDoList = CompareUtil.compare(aggregate.getOldDataObjectStore().getList(OrderItemDo.class),
//                Arrays.asList(orderDo),
//                obj -> obj.getId());
    }
}
