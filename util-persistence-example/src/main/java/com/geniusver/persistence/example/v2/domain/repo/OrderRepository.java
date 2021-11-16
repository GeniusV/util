package com.geniusver.persistence.example.v2.domain.repo;

import com.geniusver.persistence.example.v2.domain.model.Order;
import com.geniusver.persistence.example.v2.domain.model.OrderId;
import com.geniusver.persistence.example.v2.domain.model.OrderItem;
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
import java.util.stream.Collectors;

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

        aggregate.getDataObjectContext().put(orderDo, OrderDo.class, OrderDo::getId);
        aggregate.getDataObjectContext().put(orderItemDoList, OrderItemDo.class, OrderItemDo::getId);

        Order order = OrderDoConverter.INSTANCE.toOrder(orderDo);
        List<OrderItem> orderItems = orderItemDoList.stream().map(OrderItemDoConverter.INSTANCE::toOrderItem)
                .collect(Collectors.toList());
        order.setOrderItems(orderItems);

        aggregate.setRoot(order);
        return aggregate;
    }

    @Override
    public void save(Aggregate<Order> aggregate) {
        Order order = aggregate.getRoot();

        OrderDo orderDo = OrderDoConverter.INSTANCE.toOrderDo(order);
        List<OrderItemDo> orderItemDoList = order.getOrderItems().stream()
                .map(OrderItemDoConverter.INSTANCE::toOrderItemDo)
                .collect(Collectors.toList());

        compareAndSaveOrderDo(aggregate, orderDo);

        compareAndSaveOrderItemDo(aggregate, orderItemDoList);
    }

    private void compareAndSaveOrderItemDo(Aggregate<Order> aggregate, List<OrderItemDo> orderItemDoList) {
        CompareResult<OrderItemDo> orderItemDoCompareResult = CompareUtil.compare(aggregate.getDataObjectContext().getAll(OrderItemDo.class),
                orderItemDoList,
                OrderItemDo::getId);

        orderItemDoCompareResult.toInsertList().forEach(toInsert -> {
            OrderItemDo inserted = orderItemDao.insert(toInsert);
            aggregate.getDataObjectContext().put(inserted, OrderItemDo.class, OrderItemDo::getId);
        });
        orderItemDoCompareResult.toUpdateList().forEach(toInsert -> {
            OrderItemDo updated = orderItemDao.update(toInsert);
            aggregate.getDataObjectContext().put(updated, OrderItemDo.class, OrderItemDo::getId);
        });
    }

    private void compareAndSaveOrderDo(Aggregate<Order> aggregate, OrderDo orderDo) {
        CompareResult<OrderDo> orderDoResult = CompareUtil.compare(aggregate.getDataObjectContext().getAll(OrderDo.class),
                Collections.singletonList(orderDo),
                OrderDo::getId);

        // Insert or update, then store new data objects with new version
        orderDoResult.toInsertList().forEach(obj -> {
            OrderDo inserted = orderDao.insert(obj);
            aggregate.getDataObjectContext().put(inserted, OrderDo.class, OrderDo::getId);
        });
        orderDoResult.toUpdateList().forEach(obj -> {
            OrderDo updated = orderDao.update(obj);
            aggregate.getDataObjectContext().put(updated, OrderDo.class, OrderDo::getId);
        });
    }

    public OrderRepository(OrderDao orderDao, OrderItemDao orderItemDao) {
        this.orderDao = orderDao;
        this.orderItemDao = orderItemDao;
    }
}
