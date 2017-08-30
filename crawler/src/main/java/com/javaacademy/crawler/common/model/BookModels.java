package com.javaacademy.crawler.common.model;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@ToString
public class BookModels {
    List<BookModel> bookDtos;

    public boolean isEmpty() {
        return bookDtos.isEmpty();
    }

    public List<BookModel> getBookDtos() {
        return bookDtos;
    }
}
