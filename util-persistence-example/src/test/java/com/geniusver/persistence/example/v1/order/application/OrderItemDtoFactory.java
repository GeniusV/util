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

import cn.hutool.core.text.StrFormatter;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * OrderDtoFactory
 *
 * @author GeniusV
 */
public class OrderItemDtoFactory {
    public static OrderItemDto orderItemDto1() {
        OrderItemDto dto = new OrderItemDto();
        dto.setOrderItemId(1L);
        dto.setItemName("test orderItem item name 1");
        dto.setPrice(100L);
        dto.setQuantity(1);
        return dto;
    }

    public static Stream<OrderItemDto> orderItemDtos(int n) {
        return IntStream.range(0, n).mapToObj(i -> {
            OrderItemDto dto = new OrderItemDto();
            dto.setOrderItemId((long) i);
            dto.setItemName(StrFormatter.format("test orderItem item name {}", i));
            dto.setPrice(100L + i);
            dto.setQuantity(1);
            return dto;
        });
    }
}
