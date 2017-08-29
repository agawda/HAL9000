package com.javaacademy.crawler.common.model;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@ToString
public class BookDtos {
    List<BookModel> bookDtos;

    public boolean isEmpty() {
        return bookDtos.isEmpty();
    }
}
