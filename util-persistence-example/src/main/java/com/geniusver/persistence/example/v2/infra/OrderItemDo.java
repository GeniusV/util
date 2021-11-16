package com.geniusver.persistence.example.v2.infra;

import com.geniusver.persistence.example.v1.order.domain.model.ProductId;
import lombok.Data;

/**
 * OrderItemDo
 *
 * @author GeniusV
 */
@Data
public class OrderItemDo {
    private Long id;
    private Long orderId;
    private String itemName;
    private Long price;
    private ProductId productId;
    private Integer quantity;
    private Long version;
}
