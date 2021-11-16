package com.geniusver.persistence.example.v2.domain.repo;

import com.geniusver.persistence.example.v2.domain.model.*;
import com.geniusver.persistence.example.v2.infra.OrderItemDo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

/**
 * OrderItemDoConverterTest
 *
 * @author GeniusV
 */
class OrderItemDoConverterTest {

    @Test
    void toOrderItem() {
        OrderItemDo orderItemDo = new OrderItemDo();
        orderItemDo.setId(1L);
        orderItemDo.setOrderId(2L);
        orderItemDo.setItemName("test");
        orderItemDo.setCurrency("CNY");
        orderItemDo.setPrice(new BigDecimal("1.90"));
        orderItemDo.setProductId(3L);
        orderItemDo.setQuantity(4);
        orderItemDo.setVersion(5L);

        OrderItem orderItem = OrderItemDoConverter.INSTANCE.toOrderItem(orderItemDo);
        System.out.println(orderItem);
    }

    @Test
    void toOrderItemDo() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(new OrderItemId(1L));
        orderItem.setOrderId(new OrderId(6L));
        orderItem.setItemName("test");
        orderItem.setPrice(new Money("CNY", new BigDecimal("12.83")));
        orderItem.setProductId(new ProductId(2L));
        orderItem.setQuantity(5);

        OrderItemDo orderItemDo = OrderItemDoConverter.INSTANCE.toOrderItemDo(orderItem);
        System.out.println(orderItemDo);
    }
}