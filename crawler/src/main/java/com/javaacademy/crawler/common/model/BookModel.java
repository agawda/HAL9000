package com.javaacademy.crawler.common.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author devas
 * @since 24.08.17
 */
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class BookModel {
    Long industryIdentifier;
    String title;
    String subtitle;
    List<String> authors;
    List<String> categories;
    String smallThumbnail;
    String canonicalVolumeLink;
    String saleability;
    double listPriceAmount;
    String listPriceCurrencyCode;
    double retailPriceAmount;
    String retailPriceCurrencyCode;
}
