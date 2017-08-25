package com.javaacademy.crawler.googlebooks.model;

import com.javaacademy.crawler.common.interfaces.Book;
import com.javaacademy.crawler.common.interfaces.BooksWrapper;
import lombok.Data;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author devas
 * @since 24.08.17
 */
@Data
public class GoogleBooksWrapper implements BooksWrapper {
    private int totalItems;
    private List<BookItem> items;

    @Override
    public List<Book> getItems() {
        return items.stream().map((Function<BookItem, Book>) bookItem -> bookItem).collect(Collectors.toList());
    }
}
