package com.javaacademy.crawler.googlebooks.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author devas
 * @since 24.08.17
 */
@EqualsAndHashCode
@Getter
public class Price {
     double amount;
     String currencyCode;
}
