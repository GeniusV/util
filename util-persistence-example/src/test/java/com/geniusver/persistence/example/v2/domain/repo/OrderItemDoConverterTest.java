package com.geniusver.persistence.example.v2.domain.repo;

import com.geniusver.persistence.example.v2.domain.model.OrderItem;
import com.geniusver.persistence.example.v2.infra.OrderItemDo;
import org.junit.jupiter.api.Test;

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

}