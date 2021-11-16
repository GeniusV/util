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
        OrderDo orderDo = new OrderDo();
        orderDo.setId(1L);
        orderDo.setItemName("test");
        orderDo.setProductId(1L);
        orderDo.setQuantity(2);
        orderDo.setVersion(1L);

        Order order = OrderDoConverter.INSTANCE.toOrder(orderDo);
        System.out.println(order);
    }

    @Test
    void toOrderDo() {
    }
}