package com.geniusver.persistence.example.v2.domain.repo;

import com.geniusver.persistence.example.v2.domain.model.OrderItem;
import com.geniusver.persistence.example.v2.infra.OrderItemDo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * OrderItemDoConverterTest
 *
 * @author GeniusV
 */
class OrderItemDoConverterTest {

    @Test
    void toOrderItem() {
        OrderItemDo orderItemDo = OrderObjectFactory.createOrderItemDo();

        OrderItem orderItem = OrderItemDoConverter.INSTANCE.toOrderItem(orderItemDo);
        System.out.println(orderItem);
    }

    @Test
    void toOrderItemDo() {
        OrderItem orderItem = OrderObjectFactory.createOrderItem();

        OrderItemDo orderItemDo = OrderItemDoConverter.INSTANCE.toOrderItemDo(orderItem);
        System.out.println(orderItemDo);
    }

    @Test
    void equalsTest() {
        OrderItemDo orderItemDo1 = OrderObjectFactory.createOrderItemDo();
        OrderItemDo orderItemDo2 = OrderObjectFactory.createOrderItemDo();
        orderItemDo2.setVersion(null);
        assertEquals(orderItemDo1, orderItemDo2);
    }
}