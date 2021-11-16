package com.geniusver.persistence.example.v2.domain.repo;

import com.geniusver.persistence.example.v2.domain.model.*;
import com.geniusver.persistence.example.v2.infra.OrderDo;
import com.geniusver.persistence.example.v2.infra.OrderItemDo;

import java.math.BigDecimal;

/**
 * com.geniusver.persistence.example.v2.domain.repo.TestOrderObjectFactory
 *
 * @author GeniusV
 */
public class OrderObjectFactory {
    public static OrderDo createOrderDo() {
        OrderDo orderDo = new OrderDo();
        orderDo.setId(1L);
        orderDo.setUserId(10L);
        orderDo.setProductId(1L);
        orderDo.setPrice(new BigDecimal("10.01"));
        orderDo.setCurrency("CNY");
        orderDo.setVersion(1L);
        return orderDo;
    }

    public static Order createOrder() {
        Order order = new Order();
        order.setId(new OrderId(1L));
        order.setUserId(new UserId(2L));
        order.setProductId(new ProductId(3L));
        order.setPrice(new Money("CNY", new BigDecimal("12.31")));
        return order;
    }

    public static OrderItemDo createOrderItemDo() {
        OrderItemDo orderItemDo = new OrderItemDo();
        orderItemDo.setId(1L);
        orderItemDo.setOrderId(2L);
        orderItemDo.setItemName("test");
        orderItemDo.setCurrency("CNY");
        orderItemDo.setPrice(new BigDecimal("1.90"));
        orderItemDo.setProductId(3L);
        orderItemDo.setQuantity(4);
        orderItemDo.setVersion(5L);
        return orderItemDo;
    }

    public static OrderItem createOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(new OrderItemId(1L));
        orderItem.setOrderId(new OrderId(6L));
        orderItem.setItemName("test");
        orderItem.setPrice(new Money("CNY", new BigDecimal("12.83")));
        orderItem.setProductId(new ProductId(2L));
        orderItem.setQuantity(5);
        return orderItem;
    }

    public static OrderItem createOrderItem(Long i) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(new OrderItemId(i));
        orderItem.setOrderId(new OrderId(i + 1));
        orderItem.setItemName("test-" + i);
        orderItem.setPrice(new Money("CNY", new BigDecimal("12.83")));
        orderItem.setProductId(new ProductId(i + 2));
        orderItem.setQuantity((int) (i + 3));
        return orderItem;
    }
}
