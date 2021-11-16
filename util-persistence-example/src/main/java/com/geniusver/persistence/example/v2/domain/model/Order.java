package com.geniusver.persistence.example.v2.domain.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Order
 *
 * @author GeniusV
 */
@Data
public class Order {
    private OrderId id;
    private UserId userId;
    private ProductId productId;
    private List<OrderItem> orderItems = new ArrayList<>();
    private Money price;
}
