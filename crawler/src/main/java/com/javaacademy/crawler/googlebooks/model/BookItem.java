package com.javaacademy.crawler.googlebooks.model;

import com.javaacademy.crawler.common.interfaces.Book;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author devas
 * @since 24.08.17
 */
@EqualsAndHashCode
@ToString
@Getter
public class BookItem implements Book {
    VolumeInfo volumeInfo;
    SaleInfo saleInfo;
}
