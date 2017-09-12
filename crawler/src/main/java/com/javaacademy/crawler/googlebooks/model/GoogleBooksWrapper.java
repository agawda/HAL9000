package com.javaacademy.crawler.googlebooks.model;

import com.javaacademy.crawler.common.converters.GoogleBookConverter;
import com.javaacademy.crawler.common.interfaces.BooksWrapper;
import com.javaacademy.crawler.common.model.BookModel;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        if(items == null) {return new ArrayList<>();}
        return items.stream().map(googleBookConverter::convertToDto).collect(Collectors.toList());
    }
}
