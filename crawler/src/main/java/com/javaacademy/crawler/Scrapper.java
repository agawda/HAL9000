package com.javaacademy.crawler;

import com.javaacademy.crawler.common.model.BookModel;

import java.util.Set;

public interface Scrapper {

    Set<BookModel> scrape();

    String getName();
}
