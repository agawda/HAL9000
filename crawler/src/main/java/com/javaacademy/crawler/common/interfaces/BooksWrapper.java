package com.javaacademy.crawler.common.interfaces;

import com.javaacademy.crawler.common.converters.GoogleBookConverter;
import com.javaacademy.crawler.common.model.BookModel;

import java.util.List;

public interface BooksWrapper {

    List<BookModel> getItems(GoogleBookConverter googleBookConverter);
}
