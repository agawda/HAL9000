package com.javaacademy.crawler.googlebooks.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author devas
 * @since 24.08.17
 */
@EqualsAndHashCode
@ToString
public class Price {
    private double amount;
    private String currencyCode;
}
