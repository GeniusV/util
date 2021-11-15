/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2021 the original author or authors.
 */

package com.geniusver.persistence.example.v1.order.application;

import com.geniusver.persistence.example.v1.order.domain.model.*;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * OrderDtoConverterTest
 *
 * @author GeniusV
 */
class OrderDtoConverterTest {
    @Test
    void toOrderDto() {
        Order order = OrderFactory.emptyOrder()
                .orderItems(OrderItemFactory.orderItems(10)
                        .map(OrderItem.OrderItemBuilder::build)
                        .collect(Collectors.toList()))
                .build();
        OrderDto orderDto = OrderDtoConverter.INSTANCE.toOrderDTO(order);

        assertEquals(0L, orderDto.getOrderId());
        assertEquals(0L, orderDto.getUserId());
        assertEquals(10, orderDto.getOrderItems().size());
    }

    @Test
    void toOrder() {
        OrderDto orderDto = OrderDtoFactory.emptyOrderDto();
        orderDto.setOrderItems(OrderItemDtoFactory.orderItemDtos(10).collect(Collectors.toList()));

        Order order = OrderDtoConverter.INSTANCE.toOrder(orderDto);

        assertEquals(new Order.OrderId(0L), order.getOrderId());
        assertEquals(new UserId(0L), order.getUserId());
        assertEquals(10, order.getOrderItems().size());
    }

    @Test
    void toOrderNullItems() {
        OrderDto orderDto = OrderDtoFactory.emptyOrderDto();

        Order order = OrderDtoConverter.INSTANCE.toOrder(orderDto);

        assertEquals(new Order.OrderId(0L), order.getOrderId());
        assertEquals(new UserId(0L), order.getUserId());
        assertEquals(0, order.getOrderItems().size());
    }
}