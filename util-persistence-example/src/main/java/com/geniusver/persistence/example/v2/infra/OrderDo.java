package com.geniusver.persistence.example.v2.infra;

import lombok.Data;

import java.math.BigDecimal;

/**
 * OrderDo
 *
 * @author GeniusV
 */
@Data
public class OrderDo {
    private Long id;
    private String itemName;
    private BigDecimal price;
    private String currency;
    private Long productId;
    private Integer quantity;
    private Long version;
}
