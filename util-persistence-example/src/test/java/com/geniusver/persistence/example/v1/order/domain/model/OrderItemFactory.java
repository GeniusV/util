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

import cn.hutool.core.text.StrFormatter;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * com.geniusver.persistence.example.v1.order.domain.model.OrderItemFactory
 *
 * @author GeniusV
 */
public class OrderItemFactory {
    public static OrderItem.OrderItemBuilder orderItem1() {
        return OrderItem.builder().orderItemId(new OrderItem.OrderItemId(1))
                .itemName("test orderItem item name 1")
                .productId(new ProductId(1))
                .quantity(1)
                .price(100L);
    }

    public static Stream<OrderItem.OrderItemBuilder> orderItems(int n) {
        return IntStream.range(0, n).mapToObj(i -> {
            return OrderItem.builder().orderItemId(new OrderItem.OrderItemId(i))
                    .itemName(StrFormatter.format("test orderItem item name {}", i))
                    .productId(new ProductId(i + 1))
                    .quantity(1)
                    .price(100L + n);
        });
    }
}
