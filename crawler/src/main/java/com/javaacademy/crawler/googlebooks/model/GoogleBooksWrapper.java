package com.javaacademy.crawler.googlebooks.model;

import com.javaacademy.crawler.common.converters.GoogleBookConverter;
import com.javaacademy.crawler.common.interfaces.BooksWrapper;
import com.javaacademy.crawler.common.logger.AppLogger;
import com.javaacademy.crawler.common.model.BookModel;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static com.javaacademy.crawler.common.logger.AppLogger.DEFAULT_LEVEL;

/**
 * @author devas
 * @since 24.08.17
 */
@EqualsAndHashCode
@ToString
public class GoogleBooksWrapper implements BooksWrapper {
    List<BookItem> items;

    @Override
    public List<BookModel> getItems(GoogleBookConverter googleBookConverter) {
        if (items == null) {
            return new ArrayList<>();
        }
        List<BookModel> models = new ArrayList<>();
        for (BookItem bookItem : items) {
            try {
                models.add(googleBookConverter.convertToDto(bookItem));
            } catch (NullPointerException e) {
                AppLogger.logger.log(DEFAULT_LEVEL, "Exception while converting bookItem to BookModel");
            }
        }
        return models;
    }
}
