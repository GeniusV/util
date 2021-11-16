package com.geniusver.persistence.example.v2.domain.repo;

import com.geniusver.persistence.example.v2.domain.model.Order;
import com.geniusver.persistence.example.v2.infra.OrderDo;
import org.junit.jupiter.api.Test;

/**
 * OrderDoConverterTest
 *
 * @author GeniusV
 */
class OrderDoConverterTest {

    @Test
    void toOrder() {
        OrderDo orderDo = OrderObjectFactory.createOrderDo();

        Order order = OrderDoConverter.INSTANCE.toOrder(orderDo);
        System.out.println(order);
    }

    @Test
    void toOrderDo() {
        Order order = OrderObjectFactory.createOrder();

        OrderDo orderDo = OrderDoConverter.INSTANCE.toOrderDo(order);
        System.out.println(orderDo);
    }

}