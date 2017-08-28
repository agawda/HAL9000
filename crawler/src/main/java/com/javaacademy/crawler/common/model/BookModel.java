package com.javaacademy.crawler.common.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @author devas
 * @since 24.08.17
 */
@EqualsAndHashCode
@ToString
@Getter
public class BookModel {
    int totalItems;
    String title;
    String subtitle;
    List<String> authors;
    Map<String, Integer> industryIdentifiers;
    List<String> categories; // genre
    String smallThumbnail;
    String canonicalVolumeLink;
    String saleability;
    double listPriceAmount;
    String listPriceCurrencyCode;
    double retailPriceAmount;
    String retailPriceCurrencyCode;
}
