package com.javaacademy.crawler.googlebooks.dao;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author devas
 * @since 24.08.17
 */
@Data
public class GoogleBooksDao {
    private int totalItems;
    private String title;
    private String subtitle;
    private List<String> authors;
    private Map<String, Integer> industryIdentifiers;
    private List<String> categories; // genre
    private String smallThumbnail;
    private String canonicalVolumeLink;
    private String saleability;
    private double listPriceAmount;
    private String listPriceCurrencyCode;
    private double retailPriceAmount;
    private String retailPriceCurrencyCode;
}
