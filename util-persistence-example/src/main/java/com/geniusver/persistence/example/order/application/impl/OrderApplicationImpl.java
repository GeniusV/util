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

package com.geniusver.persistence.example.order.application.impl;


import cn.hutool.core.lang.Assert;
import com.geniusver.persistence.example.order.application.*;
import com.geniusver.persistence.example.order.domain.model.Order;
import com.geniusver.persistence.example.order.domain.model.ProductId;
import com.geniusver.persistence.example.order.domain.model.UserId;
import com.geniusver.persistence.example.order.domain.repo.OrderRepository;
import com.geniusver.persistence.example.order.domain.service.OrderService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * OrderApplication
 *
 * @author GeniusV
 */
public class OrderApplicationImpl implements OrderApplication {
    private OrderRepository orderRepository;
    private OrderService orderService;

    @Override
    public OrderDto getOrder(long orderId) {
        return null;
    }

    @Override
    public OrderDto createOrder(NewOrderCommand command) {
        Assert.notNull(command);
        Assert.notEmpty(command.getItemDetailList());
        Assert.notNull(command.getUserId());

        Order order = orderService.createOrder(new UserId(command.getUserId()));
        order = orderService.orderItems(order, getOrderInfos(command));

        orderRepository.save(orderRepository.createAggregate(order));
        return OrderDtoConverter.INSTANCE.toOrderDTO(order);
    }

    private List<Order.ItemInfo> getOrderInfos(NewOrderCommand command) {
        return command.getItemDetailList().stream()
                .map(detail -> new Order.ItemInfo(new ProductId(detail.getProductId()), detail.getQuantity()))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto updateOrder(UpdateOrderCommand command) {
        return null;
    }
}
