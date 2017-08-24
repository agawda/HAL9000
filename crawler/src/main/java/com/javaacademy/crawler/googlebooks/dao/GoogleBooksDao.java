package com.javaacademy.crawler.googlebooks.dao;

import com.javaacademy.crawler.googlebooks.model.ImageLinks;
import com.javaacademy.crawler.googlebooks.model.Isbn;

import java.util.List;

/**
 * @author devas
 * @since 24.08.17
 */
public class GoogleBooksDao {

    private int totalItems;
    private String title;
    private String subtitle;
    private List<String> authors;
    private List<Isbn> industryIdentifiers;
    private List<String> categories; // genre
    private String smallThumbnail;
    private String canonicalVolumeLink;
    private String saleability;
    private double listPriceAmount;
    private String listPriceCurrencyCode;
    private double retailPriceAmount;
    private String retailPriceCurrencyCode;
}
