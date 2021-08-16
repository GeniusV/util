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

package com.geniusver.persistence.example.order.domain.service;

import com.geniusver.persistence.example.order.domain.external.ProductInfoFactory;
import com.geniusver.persistence.example.order.domain.external.ProductInfoService;
import com.geniusver.persistence.example.order.domain.external.UserService;
import com.geniusver.persistence.example.order.domain.model.Order;
import com.geniusver.persistence.example.order.domain.model.OrderFactory;
import com.geniusver.persistence.example.order.domain.model.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * OrderServiceTest
 *
 * @author GeniusV
 */
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    ProductInfoService productInfoService;

    @Mock
    UserService userService;

    @InjectMocks
    OrderService orderService;

    @Test
    void createOrder() {
        UserId userId = new UserId(1L);

        when(userService.isUserExists(argThat(id -> id.getValue() == 1L)))
                .thenReturn(true);

        Order order = orderService.createOrder(userId);

        assertEquals(1L, order.getUserId().getValue());
        assertTrue(order.getOrderItems().isEmpty());
    }

    @Test
    void createOrderUserNotExists() {
        UserId userId = new UserId(0L);

        // user id will mismatch, use lenient
        lenient().when(userService.isUserExists(argThat(id -> id.getValue() == 1L)))
                .thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(userId));
    }

    @Test
    void createOrderUserIdInvalid() {
        UserId userId = new UserId(-1L);

        assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(null));
        assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(userId));
    }

    @Test
    void orderItems() {
        Order order = OrderFactory.emptyOrder().build();
        List<Order.ItemInfo> itemInfos = Arrays.asList(OrderFactory.itemInfo(1));

        when(productInfoService.getProductInfos(any()))
                .thenReturn(ProductInfoFactory.productInfoList(1));

        order = orderService.orderItems(order, itemInfos);

        verify(productInfoService).getProductInfos(argThat(list -> {
            assertEquals(1, list.size());
            assertEquals(1L, list.get(0).getValue());
            return true;
        }));
        assertEquals(100L, order.getPrice());
    }

    @Test
    void orderItemsInvalid() {
        Order order = OrderFactory.emptyOrder().build();
        List<Order.ItemInfo> itemInfos = OrderFactory.itemInfos(1);

        assertThrows(IllegalArgumentException.class, () -> orderService.orderItems(null, itemInfos));
        assertThrows(IllegalArgumentException.class, () -> orderService.orderItems(order, null));
        assertThrows(IllegalArgumentException.class, () -> orderService.orderItems(order, new ArrayList<>()));
    }


}