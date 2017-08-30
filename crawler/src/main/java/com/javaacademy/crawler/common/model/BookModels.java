package com.javaacademy.crawler.common.model;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class BookModels {
    List<BookModel> bookDtos;

    public boolean isEmpty() {
        return bookDtos.isEmpty();
    }

    public List<BookModel> getBookDtos() {
        return bookDtos;
    }
}
