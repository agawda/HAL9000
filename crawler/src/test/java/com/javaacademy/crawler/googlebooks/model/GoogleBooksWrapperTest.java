package com.javaacademy.crawler.googlebooks.model;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class GoogleBooksWrapperTest {
    @Test
    public void testGetItems() throws Exception {
        List<BookItem> books = new ArrayList<>();
        books.add(new BookItem());
        books.add(new BookItem());
        GoogleBooksWrapper googleBooksWrapper = new GoogleBooksWrapper();
        googleBooksWrapper.items = books;
        assertEquals(googleBooksWrapper.getItems().size(), 2);
    }

    @Test
    public void testToString() throws Exception {
        GoogleBooksWrapper googleBooksWrapper = new GoogleBooksWrapper();
        googleBooksWrapper.items = new ArrayList<>();
        assertEquals(googleBooksWrapper.toString(), "GoogleBooksWrapper(totalItems=0, items=[])");
    }

}