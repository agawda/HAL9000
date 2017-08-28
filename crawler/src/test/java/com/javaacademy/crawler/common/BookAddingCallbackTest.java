package com.javaacademy.crawler.common;

import com.javaacademy.crawler.common.interfaces.Book;
import com.javaacademy.crawler.common.interfaces.BooksWrapper;
import okhttp3.ResponseBody;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.javaacademy.crawler.common.RequestStatus.COMPLETED;
import static com.javaacademy.crawler.common.RequestStatus.ERROR;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class BookAddingCallbackTest {
    private String bookstoreName = "Test";
    private Set<Book> books = new HashSet<>();
    private BookAddingCallback<BooksWrapper> callback;

    @BeforeMethod
    public void initializeCallback() {
        callback = new BookAddingCallback<>(books, bookstoreName);
    }

    @Test
    public void testGetters() {
        assertEquals(callback.getBooks(), books);
        assertEquals(callback.getBookstoreName(), bookstoreName);
    }

    @Test
    public void testOnResponseSuccess() {
        BooksWrapper booksWrapper = mock(BooksWrapper.class);
        List<Book> booksList = new ArrayList<>();
        Book book = mock(Book.class);
        booksList.add(book);
        when(booksWrapper.getItems()).thenReturn(booksList);
        Response<BooksWrapper> response = Response.success(booksWrapper);
        callback.onResponse(null, response);
        assertEquals(callback.getRequestStatus(), COMPLETED);
        assertEquals(callback.getBooks(), booksList);
    }

    @Test
    public void testOnResponseError() {
        ResponseBody responseBody = mock(ResponseBody.class);
        Response<BooksWrapper> response = Response.error(500, responseBody);
        callback.onResponse(null, response);
        assertEquals(callback.getRequestStatus(), ERROR);
    }

    @Test
    public void testOnFailure() {
        Throwable throwable = new Throwable("Test");
        callback.onFailure(null, throwable);
        assertEquals(callback.getRequestStatus(), ERROR);
    }

    @Test
    public void testEquals() {
        callback = new BookAddingCallback<>(books, bookstoreName);
        BookAddingCallback<BooksWrapper> callback2 = new BookAddingCallback<>(books, bookstoreName);
        assertTrue(callback2.equals(callback));
        callback2.bookstoreName = "Test2";
        assertFalse(callback2.equals(callback));
    }

}