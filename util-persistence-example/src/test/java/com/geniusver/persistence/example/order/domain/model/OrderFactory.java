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

package com.geniusver.persistence.example.order.domain.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * com.geniusver.persistence.example.order.domain.model.OrderFactory
 *
 * @author GeniusV
 */
public class OrderFactory {
    public static Order.OrderBuilder emptyOrder() {
        return Order.builder().orderId(new Order.OrderId(0))
                .userId(new UserId(0))
                .version(0L);
    }

    public static Order.ItemInfo itemInfo(int id) {
        return new Order.ItemInfo(new ProductId(id), 1);
    }

    public static List<Order.ItemInfo> itemInfos(int n) {
        return IntStream.range(0, n)
                .mapToObj(i -> itemInfo(i + 1))
                .collect(Collectors.toList());
    }
}
