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

import cn.hutool.core.lang.Assert;
import com.geniusver.persistence.example.order.domain.external.ProductInfoService;
import com.geniusver.persistence.example.order.domain.external.UserService;
import com.geniusver.persistence.example.order.domain.model.Order;
import com.geniusver.persistence.example.order.domain.model.UserId;

import java.util.List;

/**
 * OrderService
 *
 * @author GeniusV
 */
public class OrderService {
    private ProductInfoService productInfoService;
    private UserService userService;

    public Order createOrder(UserId userId) {
        Assert.notNull(userId);
        userId.validate(userService);
        return Order.builder()
                .userId(userId)
                .build();
    }

    public Order orderItems(Order order, List<Order.ItemInfo> items) {
        Assert.notNull(order);
        Assert.notEmpty(items);
        return order.orderItems(items, productInfoService);
    }
}
