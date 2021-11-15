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

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.geniusver.persistence.example.v1.common.Default;
import com.geniusver.persistence.example.v1.order.domain.external.ProductInfo;
import lombok.*;

/**
 * OrderItem
 *
 * @author GeniusV
 */

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class OrderItem {
    private OrderItemId orderItemId;
    private String itemName;
    private Long price;
    private ProductId productId;
    private Integer quantity;
    private Long version = null;

    public boolean validate() {
        Assert.isTrue(StrUtil.isNotBlank(this.itemName));
        Assert.isTrue(this.quantity > 0);
        Assert.isTrue(this.price > 0);
        Assert.notNull(productId);
        return true;
    }

    public void updateQuantity(int quantity) {
        Assert.isTrue(quantity > 0);
        this.quantity = quantity;
    }

    public long calculatePrice() {
        return price * quantity;
    }

    public void updateProductInfo(ProductInfo productInfo) {
        Assert.notNull(productInfo);
        Assert.isTrue(productInfo.getProductId().equals(this.productId));

        this.itemName = productInfo.getProductName();
        this.price = productInfo.getPrice();
    }

    public void updateVersion(long version) {
        Assert.isFalse(this.version != null && this.version > version, "version '{}' must > current version '{}'", version, this.version);
        this.version = version;
    }


    @Getter
    @ToString
    @EqualsAndHashCode
    public static class OrderItemId {
        public final long value;

        public OrderItemId() {
            this.value = -1;
        }

        @Default
        public OrderItemId(long value) {
            this.value = value;
        }
    }
}
