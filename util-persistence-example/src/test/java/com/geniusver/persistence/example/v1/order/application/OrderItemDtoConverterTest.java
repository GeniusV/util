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

import com.geniusver.persistence.example.v1.order.domain.model.OrderItem;
import com.geniusver.persistence.example.v1.order.domain.model.OrderItemFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * OrderItemDtoConverterTest
 *
 * @author GeniusV
 */
class OrderItemDtoConverterTest {

    @Test
    void toOrderDto() {
        OrderItem orderItem = OrderItemFactory.orderItem1()
                .build();

        OrderItemDto orderItemDto = OrderItemDtoConverter.INSTANCE.toOrderItemDto(orderItem);

        assertEquals(1L, orderItemDto.getOrderItemId());
        assertEquals("test orderItem item name 1", orderItemDto.getItemName());
        assertEquals(1, orderItemDto.getQuantity());
        assertEquals(100L, orderItemDto.getPrice());
    }

    @Test
    void testToOrderDto() {
        OrderItemDto orderItemDto = OrderItemDtoFactory.orderItemDto1();

        OrderItem orderitem = OrderItemDtoConverter.INSTANCE.toOrderItem(orderItemDto);

        assertEquals(new OrderItem.OrderItemId(1L), orderitem.getOrderItemId());
        assertEquals("test orderItem item name 1", orderitem.getItemName());
        assertEquals(1, orderitem.getQuantity());
        assertEquals(100L, orderitem.getPrice());
    }
}