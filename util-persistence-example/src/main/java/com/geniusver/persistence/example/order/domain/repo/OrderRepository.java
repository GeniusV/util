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

package com.geniusver.persistence.example.order.domain.repo;

import com.geniusver.persistence.example.order.domain.model.Order;
import com.geniusver.persistence.v1.Aggregate;
import com.geniusver.persistence.v1.Repository;

/**
 * OrderRepository
 *
 * @author GeniusV
 */
public interface OrderRepository extends Repository<Order.OrderId, Order> {
    long INIT_VERSION = 0L;

    Aggregate<Order> createAggregate(Order order);

    Aggregate<Order> find(Order.OrderId orderId);

    void save(Aggregate<Order> orderAggregate);

    void remove(Order.OrderId orderId);
}
