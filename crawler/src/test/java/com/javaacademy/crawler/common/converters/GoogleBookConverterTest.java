package com.javaacademy.crawler.common.converters;

import com.javaacademy.crawler.common.interfaces.Book;
import com.javaacademy.crawler.common.model.BookModel;
import com.javaacademy.crawler.googlebooks.model.BookItem;
import com.javaacademy.crawler.googlebooks.model.Isbn;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class GoogleBookConverterTest {

    @Test
    public void convertToDtosWithoutNullsTestException() {
        BookItem bookItem = new BookItem();
        GoogleBookConverter googleBookConverter = new GoogleBookConverter();
        Set<Book> bookItems = new HashSet<>();
        bookItems.add(bookItem);
        List<BookModel> convertedBooks = googleBookConverter.convertToDtosWithoutNulls(bookItems);
        assertEquals(convertedBooks.size(), 0);
    }

    @Test
    public void getIsbnTest() {
        GoogleBookConverter googleBookConverter = new GoogleBookConverter();
        List<Isbn> isbnList = new ArrayList<>();
        Isbn isbn = mock(Isbn.class);
        isbnList.add(isbn);
        when(isbn.getIdentifier()).thenReturn("c");
        Long result = googleBookConverter.getIsbn(isbnList);
        assertEquals(result, Long.valueOf(-1L));
    }

}