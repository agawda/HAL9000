package com.javaacademy.crawler.common.converters;

import com.javaacademy.crawler.common.interfaces.Book;
import com.javaacademy.crawler.common.model.BookModel;
import com.javaacademy.crawler.googlebooks.model.BookItem;
import com.javaacademy.crawler.googlebooks.model.Isbn;
import org.mockito.Matchers;
import org.testng.Assert;
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
        Set<BookModel> convertedBooks = googleBookConverter.convertToDtosWithoutNulls(bookItems);
        assertEquals(convertedBooks.size(), 0);
    }

    @Test
    public void getIsbnTest() {
        GoogleBookConverter googleBookConverter = new GoogleBookConverter();
        List<Isbn> isbnList = new ArrayList<>();
        Isbn isbn = mock(Isbn.class);
        isbnList.add(isbn);
        when(isbn.getIdentifier()).thenReturn("123123123123");
        Long result = googleBookConverter.getIsbn(isbnList);
        Assert.assertNotNull(result);
    }

}