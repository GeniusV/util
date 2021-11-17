package com.geniusver.persistence.example.v2.infra;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

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
    private String currency;
    private BigDecimal price;
    private Long productId;
    private Integer quantity;
    @EqualsAndHashCode.Exclude
    private Long version;
}
