package com.javaacademy.crawler.googlebooks.model;

import lombok.Data;

/**
 * @author devas
 * @since 24.08.17
 */
@Data
public class BookItem {
    private VolumeInfo volumeInfo;
    private SaleInfo saleInfo;
}
