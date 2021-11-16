package com.geniusver.persistence.example.v2.domain.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Money
 *
 * @author GeniusV
 */
@Data
public class Money {
    private final String currency;
    private final BigDecimal amount;
}
