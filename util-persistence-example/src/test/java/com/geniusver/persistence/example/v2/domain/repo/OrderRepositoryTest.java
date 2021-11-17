package com.geniusver.persistence.example.v2.domain.repo;

import cn.hutool.core.util.RandomUtil;
import com.geniusver.concurrent.ConcurrentTester;
import com.geniusver.persistence.example.v2.domain.model.Order;
import com.geniusver.persistence.example.v2.domain.model.OrderId;
import com.geniusver.persistence.example.v2.domain.model.OrderItem;
import com.geniusver.persistence.example.v2.domain.model.ProductId;
import com.geniusver.persistence.v2.Aggregate;
import com.geniusver.util.Metrics;
import org.junit.jupiter.api.Disabled;
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

        aggregate.getRoot().getOrderItems().get(3).setQuantity(900);
        orderRepository.save(aggregate);
    }

    /**
     * thread 1 order num 1 order item 5 qps 320000
     * thread 1 order num 1 order item 10 qps 240000
     * thread 1 order num 1 order item 25 qps 100000
     * thread 1 order num 1 order item 50 qps 60000
     * thread 1 order num 1 order item 100 qps 32000
     */
    @Disabled
    @Test
    void benchmark() {
        int orderNum = 1;
        int orderItemNum = 100;
        FakeOrderDao orderDao = new FakeOrderDao();
        orderDao.setPrintLogs(false);
        FakeOrderItemDao orderItemDao = new FakeOrderItemDao();
        orderItemDao.setPrintLogs(false);
        OrderRepository orderRepository = new OrderRepository(orderDao, orderItemDao);


        LongStream.range(0, orderNum).mapToObj(i -> {
            Order order = OrderObjectFactory.createOrder(i);
            List<OrderItem> orderItems = LongStream.range(i * orderItemNum, (i + 1) * orderItemNum)
                    .mapToObj(OrderObjectFactory::createOrderItem)
                    .peek(item -> item.setOrderId(order.getId()))
                    .collect(Collectors.toList());
            order.setOrderItems(orderItems);
            return new Aggregate(order);
        }).forEach(orderRepository::save);

        Metrics metrics = new Metrics("order repo");
        metrics.start();

        new ConcurrentTester().addWorker(1, () -> {
            while (true) {
                OrderId id = new OrderId(RandomUtil.randomLong(0, orderNum));
                Aggregate<Order> orderAggregate = orderRepository.find(id);
                orderAggregate.getRoot().getOrderItems().get(RandomUtil.randomInt(0, orderItemNum)).setItemName(RandomUtil.randomString(10));
                orderRepository.save(orderAggregate);
                metrics.count();
            }
        }).start();
    }
}