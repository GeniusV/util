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

package com.geniusver.persistence.example.order.domain.external;

import cn.hutool.core.text.StrFormatter;
import com.geniusver.persistence.example.order.domain.model.ProductId;

import java.util.ArrayList;
import java.util.List;

/**
 * ProductInfoFactory
 *
 * @author GeniusV
 */
public class ProductInfoFactory {
    public static List<ProductInfo> productInfoList(int n) {
        List<ProductInfo> productInfos = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            productInfos.add(new ProductInfo(
                    new ProductId(i + 1),
                    StrFormatter.format("product {}", i + 1),
                    100L + i
            ));
        }
        return productInfos;
    }
}