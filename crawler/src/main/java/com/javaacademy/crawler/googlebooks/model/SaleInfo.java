package com.javaacademy.crawler.googlebooks.model;

import lombok.Data;

/**
 * @author devas
 * @since 24.08.17
 */
@Data
public class SaleInfo {
    private String saleability;
    private Price listPrice; // normal price
    private Price retailPrice; // price with discount
}
