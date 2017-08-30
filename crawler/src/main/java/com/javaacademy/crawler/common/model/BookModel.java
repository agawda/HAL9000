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
    String subtitle;
    String title;
    List<String> categories;
    List<String> authors;
    String canonicalVolumeLink;
    String saleability;
    String smallThumbnail;
    double listPriceAmount;
    double retailPriceAmount;
    String listPriceCurrencyCode;
    String retailPriceCurrencyCode;
}
