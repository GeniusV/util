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

import cn.hutool.core.lang.Assert;
import com.geniusver.persistence.Entity;
import com.geniusver.persistence.example.common.Default;
import com.geniusver.persistence.example.order.domain.external.ProductInfo;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Order
 *
 * @author GeniusV
 */
@Slf4j
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Order implements Entity {
    private OrderId orderId;
    private UserId userId;
    private Long productId;
    private Long version;
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();
    @Builder.Default
    private Long price = 0L;

    public boolean validate() {
        Assert.notNull(userId);
        Assert.notNull(orderItems);
        Assert.notEmpty(orderItems);
        orderItems.forEach(OrderItem::validate);
        Assert.notNull(price);
        return true;
    }

    public Order orderItem(OrderItem item) {
        Assert.notNull(item);
        Assert.isTrue(item.validate());
        orderItems.add(item);
        calculatePrice();
        return this;
    }

    public Order orderItems(List<ItemInfo> inputItemInfos, List<ProductInfo> productInfos) {
        Assert.notEmpty(inputItemInfos);
        Assert.notEmpty(productInfos);
        inputItemInfos.forEach(ItemInfo::validate);

        Map<ProductId, OrderItem> existItemMap = this.orderItems.stream().collect(Collectors.toMap(OrderItem::getProductId, item -> item));

        increaseQuantityForExistingProduct(existItemMap, inputItemInfos);

        List<ItemInfo> newItemInfos = findNewItemInfos(existItemMap, inputItemInfos);

        // product id -> productInfo
        Map<ProductId, ProductInfo> productInfoMap = toMap(productInfos);

        // build orderItems based on product info and requested quantity
        List<OrderItem> newOrderItems = buildOrderItems(newItemInfos, productInfoMap);

        this.orderItems.addAll(newOrderItems);

        calculatePrice();

        return this;
    }

    public Order updateItemProductInfo(List<ProductInfo> productInfos) {
        Assert.notEmpty(productInfos);
        Map<ProductId, OrderItem> productIdOrderItemMap = this.orderItems.stream().collect(Collectors.toMap(OrderItem::getProductId, item -> item));
        productInfos.forEach(productInfo -> {
            OrderItem item = productIdOrderItemMap.get(productInfo.getProductId());
            if (item == null) {
                return;
            }
            item.updateProductInfo(productInfo);
        });
        calculatePrice();
        return this;
    }

    private List<ItemInfo> findNewItemInfos(Map<ProductId, OrderItem> existItemMap, List<ItemInfo> inputItemInfos) {
        return inputItemInfos.stream()
                .filter(itemInfo -> existItemMap.get(itemInfo.getProductId()) == null)
                .collect(Collectors.toList());
    }

    private void increaseQuantityForExistingProduct(Map<ProductId, OrderItem> existItemMap, List<ItemInfo> inputItemInfos) {
        inputItemInfos.forEach(itemInfo -> {
            OrderItem existItem = existItemMap.get(itemInfo.getProductId());
            if (existItem == null) {
                return;
            }
            existItem.updateQuantity(itemInfo.getQuantity() + 1);
        });
    }

    private List<ProductId> extractProductIds(List<ItemInfo> itemInfos) {
        return itemInfos.stream()
                .map(ItemInfo::getProductId)
                .collect(Collectors.toList());
    }

    private List<OrderItem> buildOrderItems(List<ItemInfo> itemInfos, Map<ProductId, ProductInfo> productInfoMap) {
        return itemInfos.stream().map(item -> {
            ProductInfo productInfo = productInfoMap.get(item.getProductId());
            Assert.notNull(productInfo, "product info for item {} not exist");
            OrderItem orderItem = OrderItem.builder()
                    .productId(item.productId)
                    .itemName(productInfo.getProductName())
                    .price(productInfo.getPrice())
                    .quantity(item.getQuantity())
                    .build();
            orderItem.validate();
            return orderItem;
        }).collect(Collectors.toList());
    }

    private Map<ProductId, ProductInfo> toMap(List<ProductInfo> productInfos) {
        return productInfos.stream()
                .collect(Collectors.toMap(ProductInfo::getProductId, productInfo -> productInfo));
    }

    public Order updateOrderItem(OrderItem item) {
        Assert.notNull(item);
        Assert.isTrue(item.validate());

        boolean removed = orderItems.removeIf(orderItem -> orderItem.getOrderItemId().equals(item.getOrderItemId()));
        Assert.isTrue(removed, "item: {} not exist in order {}", item, this);

        this.orderItem(item);
        return this;
    }

    public Order removeOrderItem(OrderItem.OrderItemId id) {
        Assert.notNull(id);
        boolean removed = orderItems.removeIf(orderItem -> orderItem.getOrderItemId().equals(id));
        Assert.isTrue(removed, "item: {} not exist in order {}", id, this);
        calculatePrice();
        return this;
    }

    public long calculatePrice() {
        this.price = orderItems.stream().mapToLong(OrderItem::calculatePrice).sum();
        return this.price;
    }

    public void updateUser(UserId userId) {
        Assert.notNull(userId);
        this.userId = userId;
    }

    public void updateVersion(long version) {
        Assert.isFalse(this.version != null && this.version > version, "version '{}' must > current version '{}'", version, this.version);
        this.version = version;
    }


    public OrderId getOrderId() {
        return orderId;
    }

    public UserId getUserId() {
        return userId;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public Long getPrice() {
        return price;
    }

    @Getter
    @ToString
    @EqualsAndHashCode
    public static class OrderId {
        public final long value;

        public OrderId() {
            this.value = -1;
        }

        @Default
        public OrderId(long value) {
            this.value = value;
        }
    }

    @Getter
    @EqualsAndHashCode
    @ToString
    @AllArgsConstructor
    public static class ItemInfo {
        private final ProductId productId;
        private final int quantity;

        public boolean validate() {
            Assert.isTrue(this.quantity > 0);
            return true;
        }
    }
}
