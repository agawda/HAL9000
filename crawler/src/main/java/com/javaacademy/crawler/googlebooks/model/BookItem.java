package com.javaacademy.crawler.googlebooks.model;

import com.javaacademy.crawler.common.interfaces.Book;
import lombok.Data;

/**
 * @author devas
 * @since 24.08.17
 */
@Data
public class BookItem implements Book {
    private VolumeInfo volumeInfo;
    private SaleInfo saleInfo;
}
