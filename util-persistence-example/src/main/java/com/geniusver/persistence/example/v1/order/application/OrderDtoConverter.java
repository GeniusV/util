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

import com.geniusver.persistence.example.v1.order.domain.model.Order;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * OrderAssembler
 *
 * @author GeniusV
 */
@Mapper(uses = OrderItemDtoConverter.class)
public interface OrderDtoConverter {
    OrderDtoConverter INSTANCE = Mappers.getMapper(OrderDtoConverter.class);

    @Mapping(source = "orderId.value", target = "orderId")
    @Mapping(source = "userId.value", target = "userId")
    @Mapping(source = "orderItems", target = "orderItems")
    OrderDto toOrderDTO(Order order);

    @InheritInverseConfiguration
    Order toOrder(OrderDto orderDTO);
}
