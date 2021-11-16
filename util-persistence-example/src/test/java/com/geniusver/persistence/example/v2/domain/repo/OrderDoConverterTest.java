package com.geniusver.persistence.example.v2.domain.repo;

import com.geniusver.persistence.example.v2.domain.model.*;
import com.geniusver.persistence.example.v2.infra.OrderDo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

/**
 * OrderDoConverterTest
 *
 * @author GeniusV
 */
class OrderDoConverterTest {

    @Test
    void toOrder() {
        OrderDo orderDo = new OrderDo();
        orderDo.setId(1L);
        orderDo.setUserId(10L);
        orderDo.setProductId(1L);
        orderDo.setPrice(new BigDecimal("10.01"));
        orderDo.setCurrency("CNY");
        orderDo.setVersion(1L);

        Order order = OrderDoConverter.INSTANCE.toOrder(orderDo);
        System.out.println(order);
    }

    @Test
    void toOrderDo() {
        Order order = new Order();
        order.setId(new OrderId(1L));
        order.setUserId(new UserId(2L));
        order.setProductId(new ProductId(3L));
        order.setPrice(new Money("CNY", new BigDecimal("12.31")));

        OrderDo orderDo = OrderDoConverter.INSTANCE.toOrderDo(order);
        System.out.println(orderDo);
    }
}