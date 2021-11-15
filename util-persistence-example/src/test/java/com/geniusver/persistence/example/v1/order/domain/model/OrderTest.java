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

import com.geniusver.persistence.example.v1.order.domain.external.ProductInfoFactory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static com.geniusver.persistence.example.v1.order.domain.model.OrderFactory.emptyOrder;
import static com.geniusver.persistence.example.v1.order.domain.model.OrderFactory.itemInfos;
import static com.geniusver.persistence.example.v1.order.domain.model.OrderItemFactory.orderItem1;
import static org.junit.jupiter.api.Assertions.*;

/**
 * OrderTest
 *
 * @author GeniusV
 */
class OrderTest {
    @Test
    void isValid() {
        Order order = emptyOrder()
                .orderItems(new ArrayList<>(Arrays.asList(orderItem1().build())))
                .build();
        boolean res = order.validate();
        assertTrue(res);
    }

    @Test
    void isValidFailed() {
        assertThrows(IllegalArgumentException.class, () -> emptyOrder()
                .orderItems(null)
                .build().validate());
        assertThrows(IllegalArgumentException.class, () -> emptyOrder()
                .userId(null)
                .build().validate());
        assertThrows(IllegalArgumentException.class, () -> emptyOrder()
                .price(null)
                .build().validate());
    }

    @Test
    void isValidWithNullOrderItems() {
        Order order = emptyOrder().orderItems(null).build();

        assertThrows(IllegalArgumentException.class, order::validate);
    }


    @Test
    void isValidWithNullUser() {
        Order order = emptyOrder().userId(null).build();
        assertThrows(IllegalArgumentException.class, order::validate);
    }

    @Test
    void isValidWithNullPrice() {
        Order order = emptyOrder().price(null).build();
        assertThrows(IllegalArgumentException.class, order::validate);
    }

    @Test
    void orderItem() {
        Order order = emptyOrder().build();

        order.orderItem(orderItem1().build());

        assertEquals(1, order.getOrderItems().size());
        assertEquals(100, order.getPrice());
    }

    @Test
    void orderItems() {
        Order order = emptyOrder().build();

        order.orderItems(itemInfos(2), ProductInfoFactory.productInfoList(2));

        assertEquals(2, order.getOrderItems().size());
        assertEquals(201, order.getPrice());
    }

    @Test
    void orderSameItemTwice() {
        Order order = emptyOrder()
                .orderItems(new ArrayList<>(Arrays.asList(orderItem1().build())))
                .build();
        order = new EntityLogger().enhance(order);

        order.orderItems(itemInfos(1), ProductInfoFactory.productInfoList(5));

        assertEquals(1, order.getOrderItems().size());
        assertEquals(2, order.getOrderItems().get(0).getQuantity());
        assertEquals(200, order.getPrice());
    }

    @Test
    void orderItemWithExistingItems() {
        Order order = emptyOrder()
                .orderItems(new ArrayList<>(Arrays.asList(orderItem1().build())))
                .build();

        order.orderItems(itemInfos(2, 3), ProductInfoFactory.productInfoList(5));

        assertEquals(2, order.getOrderItems().size());
        assertEquals(202, order.getPrice());
    }

    @Test
    void orderItemsWithEmptyList() {
        Order order = emptyOrder()
                .orderItems(new ArrayList<>(Arrays.asList(orderItem1().build())))
                .build();

        assertThrows(IllegalArgumentException.class, () ->
                order.orderItems(new ArrayList<>(), ProductInfoFactory.productInfoList(2)));

    }


    @Test
    void orderItemsWithInvalidQuantity() {
        Order order = emptyOrder().build();

        assertThrows(IllegalArgumentException.class, () ->
                order.orderItems(Arrays.asList(
                        new Order.ItemInfo(new ProductId(1L), -1),
                        new Order.ItemInfo(new ProductId(2L), 1)), ProductInfoFactory.productInfoList(2)));


        assertThrows(IllegalArgumentException.class, () ->
                order.orderItems(Arrays.asList(
                        new Order.ItemInfo(new ProductId(1L), 0),
                        new Order.ItemInfo(new ProductId(2L), 1)), ProductInfoFactory.productInfoList(2)));
    }

    @Test
    void orderItemWithNull() {
        Order order = emptyOrder().build();
        assertThrows(IllegalArgumentException.class, () -> order.orderItem(null));
    }

    @Test
    void orderItemWithZeroQuantity() {
        Order order = emptyOrder().build();

        assertThrows(IllegalArgumentException.class, () -> order.orderItem(orderItem1().quantity(0).build()));
    }

    @Test
    void updateOrderItem() {
        Order order = emptyOrder()
                .orderItems(new ArrayList<>(Arrays.asList(orderItem1().build())))
                .build();
        OrderItem updateItem = orderItem1()
                .itemName("name 2")
                .quantity(2)
                .build();

        order.updateOrderItem(updateItem);

        assertEquals(1, order.getOrderItems().size());
        assertEquals("name 2", order.getOrderItems().get(0).getItemName());
        assertEquals(200, order.getPrice());
    }

    @Test
    void updateOrderItemNotExist() {
        Order order = emptyOrder()
                .orderItems(new ArrayList<>(Arrays.asList(orderItem1().build())))
                .build();
        OrderItem updateItem = orderItem1()
                .orderItemId(new OrderItem.OrderItemId(-1))
                .itemName("name 2")
                .build();

        assertThrows(IllegalArgumentException.class,
                () -> order.updateOrderItem(updateItem));
    }

    @Test
    void updateVersion() {
        Order order = emptyOrder().build();

        order.updateVersion(1);

        assertEquals(1, order.getVersion());
    }

    @Test
    void updateVersionFailed() {
        Order order = emptyOrder().version(1L).build();

        assertThrows(IllegalArgumentException.class, () -> order.updateVersion(0));
    }

    @Test
    void updateUser() {
        Order order = emptyOrder().build();

        order.updateUser(new UserId(1000));

        assertEquals(1000, order.getUserId().getValue());
    }

    @Test
    void updateUserFailed() {
        Order order = emptyOrder().build();

        assertThrows(IllegalArgumentException.class, () -> order.updateUser(null));
    }
}