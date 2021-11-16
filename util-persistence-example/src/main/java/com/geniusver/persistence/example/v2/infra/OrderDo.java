package com.geniusver.persistence.example.v2.infra;

import lombok.Data;

/**
 * OrderDo
 *
 * @author GeniusV
 */
@Data
public class OrderDo {
    private Long id;
    private String itemName;
    private Long price;
    private Long productId;
    private Integer quantity;
    private Long version;
}
