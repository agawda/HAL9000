package com.javaacademy.crawler.googlebooks.model;

import lombok.Data;

import java.util.List;

/**
 * @author devas
 * @since 24.08.17
 */
@Data
public class GoogleBooksWrapper {
    private int totalItems;
    private List<BookItem> items;
}
