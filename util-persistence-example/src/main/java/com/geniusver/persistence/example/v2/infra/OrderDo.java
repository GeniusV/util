package com.geniusver.persistence.example.v2.infra;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * OrderDo
 *
 * @author GeniusV
 */
@Data
public class OrderDo {
    private Long id;
    private Long userId;
    private BigDecimal price;
    private String currency;
    private Long productId;
    @EqualsAndHashCode.Exclude
    private Long version;
}
