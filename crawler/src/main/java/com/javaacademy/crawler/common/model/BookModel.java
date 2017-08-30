package com.javaacademy.crawler.common.model;

import com.javaacademy.crawler.common.interfaces.Book;
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
public class BookModel implements Book {
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
