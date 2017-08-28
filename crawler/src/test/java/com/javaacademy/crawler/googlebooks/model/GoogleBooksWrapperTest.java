package com.javaacademy.crawler.googlebooks.model;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Test
public class GoogleBooksWrapperTest {

    public void testGetItems() {
        List<BookItem> books = new ArrayList<>();
        books.add(new BookItem());
        books.add(new BookItem());
        GoogleBooksWrapper googleBooksWrapper = new GoogleBooksWrapper();
        googleBooksWrapper.items = books;
        assertEquals(googleBooksWrapper.getItems().size(), 2);
    }

    public void testToString() {
        GoogleBooksWrapper googleBooksWrapper = new GoogleBooksWrapper();
        googleBooksWrapper.items = new ArrayList<>();
        assertEquals(googleBooksWrapper.toString(), "GoogleBooksWrapper(items=[])");
    }

    public void testEquals() {
        GoogleBooksWrapper googleBooksWrapper = new GoogleBooksWrapper();
        googleBooksWrapper.items = new ArrayList<>();
        GoogleBooksWrapper googleBooksWrapper1 = new GoogleBooksWrapper();
        googleBooksWrapper1.items = new ArrayList<>();
        assertTrue(googleBooksWrapper.equals(googleBooksWrapper1));
        googleBooksWrapper.items.add(new BookItem());
        assertFalse(googleBooksWrapper.equals(googleBooksWrapper1));
    }



}