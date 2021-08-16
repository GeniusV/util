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

package com.geniusver.persistence.example.order.infrastructure.repo;


import com.geniusver.persistence.Aggregate;
import com.geniusver.persistence.AggregateFactory;
import com.geniusver.persistence.example.order.domain.model.Order;
import com.geniusver.persistence.example.order.domain.model.OrderItem;
import com.geniusver.persistence.example.order.domain.repo.OrderRepository;

import java.util.Collection;

/**
 * OrderRepository
 *
 * @author GeniusV
 */
public class OrderRepositoryImpl implements OrderRepository {
    @Override
    public Aggregate<Order> createAggregate(Order order) {
        return AggregateFactory.createAggregate(order);
    }

    @Override
    public Aggregate<Order> find(Order.OrderId orderId) {
        return null;
    }

    @Override
    public void save(Aggregate<Order> aggregate) {
        if (aggregate.isNew()) {
            doInsertOrder(aggregate.getRoot());
            doCreateOrderItems(aggregate.getRoot().getOrderItems());
            return;
        }

        if (aggregate.isChanged()) {
            doUpdateOrder(aggregate.getRoot());

            Collection<OrderItem> newOrderItems = aggregate.findNewEntitiesById(Order::getOrderItems, OrderItem::getOrderItemId);
            doCreateOrderItems(newOrderItems);

            Collection<OrderItem> changedOrderItems = aggregate.findChangedEntities(Order::getOrderItems, OrderItem::getOrderItemId);
            doUpdateOrderItems(changedOrderItems);
        }
    }

    private void doInsertOrder(Order order) {
        // convert to DO
        // do update sql
        // update version
    }

    private void doUpdateOrder(Order order) {
        // convert to DO
        // do update sql
        // update version
    }

    private void doCreateOrderItems(Collection<OrderItem> orderItems) {
        // convert to DO
        // do update sql
        // update version
    }

    private void doUpdateOrderItems(Collection<OrderItem> orderItems) {
        // convert to DO
        // do update sql
        // update version
    }

    @Override
    public void remove(Order.OrderId orderId) {

    }
}
