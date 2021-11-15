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

package com.geniusver.persistence.example.v1.order.application.impl;


import com.geniusver.persistence.example.v1.order.application.ItemDetail;
import com.geniusver.persistence.example.v1.order.application.NewOrderCommand;
import com.geniusver.persistence.example.v1.order.application.OrderDto;
import com.geniusver.persistence.example.v1.order.domain.model.Order;
import com.geniusver.persistence.example.v1.order.domain.model.OrderFactory;
import com.geniusver.persistence.example.v1.order.domain.model.UserId;
import com.geniusver.persistence.example.v1.order.domain.repo.OrderRepository;
import com.geniusver.persistence.example.v1.order.domain.service.OrderService;
import com.geniusver.persistence.v1.Aggregate;
import com.geniusver.persistence.v1.AggregateFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Arrays;

import static com.geniusver.persistence.example.v1.order.application.OrderDtoFactory.emptyOrderDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * OrderApplicationImplTest
 *
 * @author GeniusV
 */
@ExtendWith(MockitoExtension.class)
class OrderApplicationImplTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    OrderService orderService;

    @InjectMocks
    OrderApplicationImpl orderApplication;

    @Test
    void getOrder() {
        assertNotNull(orderRepository);
        assertNotNull(orderApplication);
        // TODO: 6/17/2021
    }

    @Test
    void createOrder() {
        // prepare data
        NewOrderCommand command = new NewOrderCommand();
        command.setUserId(1L);
        command.setItemDetailList(Arrays.asList(new ItemDetail(1L, 1),
                new ItemDetail(2L, 1)));

        Order order = OrderFactory.emptyOrder().build();
        when(orderService.createOrder(any()))
                .thenReturn(order);
        when(orderService.orderItems(any(), any())).thenReturn(order);
        when(orderRepository.createAggregate(any()))
                .thenAnswer((Answer<Aggregate>) invocationOnMock ->
                        AggregateFactory.createAggregate(invocationOnMock.getArgument(0)));

        OrderDto result = orderApplication.createOrder(command);

        verify(orderService).createOrder(argThat(userId -> userId.equals(new UserId(1L))));
        verify(orderService).orderItems(any(), argThat(items -> {
            assertTrue(items.containsAll(OrderFactory.itemInfos(2)));
            return true;
        }));
        verify(orderRepository).save(any(Aggregate.class));
        assertNotNull(result);
    }

    @Test
    void createOrderGiveNull() {
        assertThrows(IllegalArgumentException.class,
                () -> orderApplication.createOrder(null));
    }

    @Test
    void createOrderNullOrderItem() {
        // prepare data
        OrderDto orderDto = emptyOrderDto();
        NewOrderCommand command = new NewOrderCommand();
        command.setUserId(1L);
        command.setItemDetailList(null);

        assertThrows(IllegalArgumentException.class, () -> orderApplication.createOrder(command));
    }


    @Test
    void updateOrder() {
    }
}