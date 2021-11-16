package com.geniusver.persistence.example.v2.domain.model;


import lombok.Data;

/**
 * OrderItem
 *
 * @author GeniusV
 */
@Data
public class OrderItem {
    private OrderItemId orderItemId;
    private String itemName;
    private Long price;
    private ProductId productId;
    private Integer quantity;
    private Long version = null;
}
