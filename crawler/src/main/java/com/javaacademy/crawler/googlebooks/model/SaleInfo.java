package com.javaacademy.crawler.googlebooks.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author devas
 * @since 24.08.17
 */
@EqualsAndHashCode
@ToString
public class SaleInfo {
    private String saleability;
    private Price listPrice; // normal price
    private Price retailPrice; // price with discount
}
