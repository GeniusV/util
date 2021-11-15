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

package com.geniusver.persistence.example.v1.order.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * OrderItemTest
 *
 * @author GeniusV
 */
public class OrderItemTest {

    @Test
    void isValid() {
        OrderItem orderItem1 = OrderItemFactory.orderItem1()
                .build();

        assertTrue(orderItem1.validate());
    }

    @Test
    void isValidFailed() {
        assertThrows(IllegalArgumentException.class, () -> OrderItemFactory.orderItem1()
                .itemName(null)
                .build().validate());
        assertThrows(IllegalArgumentException.class, () -> OrderItemFactory.orderItem1()
                .itemName("")
                .build().validate());
        assertThrows(IllegalArgumentException.class, () -> OrderItemFactory.orderItem1()
                .quantity(-1)
                .build().validate());
        assertThrows(IllegalArgumentException.class, () -> OrderItemFactory.orderItem1()
                .price(-1L)
                .build().validate());
    }

    @Test
    void updateQuantity() {
        OrderItem orderItem1 = OrderItemFactory.orderItem1()
                .build();

        orderItem1.updateQuantity(2);

        assertEquals(2, orderItem1.getQuantity());
    }

    @Test
    void updateQuantityMinus() {
        OrderItem orderItem1 = OrderItemFactory.orderItem1()
                .build();

        assertThrows(IllegalArgumentException.class, () -> orderItem1.updateQuantity(-1));
    }

    @Test
    void calculatePrice() {
        OrderItem orderItem1 = OrderItemFactory.orderItem1()
                .build();

        long res = orderItem1.calculatePrice();

        assertEquals(100, res);
    }

    @Test
    void updateVersion() {
        OrderItem orderItem = OrderItemFactory.orderItem1().build();

        orderItem.updateVersion(1);

        assertEquals(1, orderItem.getVersion());
    }

    @Test
    void updateVersionFailed() {
        OrderItem orderItem = OrderItemFactory.orderItem1().version(1L).build();

        assertThrows(IllegalArgumentException.class, () -> orderItem.updateVersion(0));
    }

}