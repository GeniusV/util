package com.geniusver.persistence.example.v2.domain.repo;

import com.geniusver.persistence.example.v2.domain.model.Order;
import com.geniusver.persistence.example.v2.domain.model.OrderItem;
import com.geniusver.persistence.example.v2.domain.model.ProductId;
import com.geniusver.persistence.v2.Aggregate;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * OrderRepositoryTest
 *
 * @author GeniusV
 */
class OrderRepositoryTest {

    @Test
    void find() {

    }

    @Test
    void save() {
        OrderRepository orderRepository = new OrderRepository(new FakeOrderDao(), new FakeOrderItemDao());
        Order order = OrderObjectFactory.createOrder();
        List<OrderItem> orderItems = LongStream.range(0, 10)
                .mapToObj(OrderObjectFactory::createOrderItem)
                .collect(Collectors.toList());
        order.setOrderItems(orderItems);

        Aggregate<Order> aggregate = new Aggregate<>(order);
        orderRepository.save(aggregate);

        order.setProductId(new ProductId(12893L));
        orderRepository.save(aggregate);

        //// TODO: 11/16/2021 old new pair to copy version
    }
}