package com.javaacademy.crawler.googlebooks.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author devas
 * @since 24.08.17
 */
@EqualsAndHashCode
@ToString
@Getter
public class SaleInfo {
    String saleability;
    Price listPrice; // normal price
    Price retailPrice; // price with discount
}
