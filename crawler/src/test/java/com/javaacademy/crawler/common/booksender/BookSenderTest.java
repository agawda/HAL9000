package com.javaacademy.crawler.common.booksender;

import com.javaacademy.crawler.common.converters.GoogleBookConverter;
import com.javaacademy.crawler.common.interfaces.Book;
import com.javaacademy.crawler.common.logger.AppLogger;
import com.javaacademy.crawler.common.model.BookModel;
import com.javaacademy.crawler.common.retrofit.BookServerEndpoint;
import com.javaacademy.crawler.common.retrofit.SendingRetrofit;
import com.javaacademy.crawler.googlebooks.model.BookItem;
import org.testng.annotations.Test;
import retrofit2.Call;

import java.util.*;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertFalse;

public class BookSenderTest {

    @Test
    public void testSendingBooks() {
        BookSender bookSender = new BookSender(getBooks(), getConverterMock());
        Call<String> serverResponse = mock(Call.class);
        bookSender.sendingRetrofit = getSendingRetrofitMock(serverResponse);
        BookSender.numberOfBooksSentAtOnce = 1;
        bookSender.sendBooksTo("IPADDRESS");
        verify(serverResponse, times(2)).enqueue(any());
    }

    @Test
    public void testMarkBooks() {
        AppLogger.initializeLogger();
        BookSender bookSender = new BookSender(getBooks(), getConverterMock());
        bookSender.booksToSend = getBookModelsMap();
        bookSender.markBooksAsSent(new ArrayList<>(bookSender.booksToSend.keySet()));
        assertFalse(bookSender.booksSendCounter.containsValue(0));
        assertFalse(bookSender.booksToSend.containsValue(false));
    }

    private GoogleBookConverter getConverterMock() {
        GoogleBookConverter googleBookConverter = mock(GoogleBookConverter.class);
        when(googleBookConverter.convertToDto(any())).thenReturn(mock(BookModel.class));
        return googleBookConverter;
    }

    private SendingRetrofit getSendingRetrofitMock(Call<String> serverResponse) {
        SendingRetrofit sendingRetrofit = mock(SendingRetrofit.class);
        BookServerEndpoint endpoint = mock(BookServerEndpoint.class);
        when(sendingRetrofit.getBookBookServerEndpoint(any())).thenReturn(endpoint);
        when(endpoint.putBooksToServer(any())).thenReturn(serverResponse);
        return sendingRetrofit;
    }

    private Set<Book> getBooks() {
        Set<Book> books = new HashSet<>();
        BookItem bookModel = mock(BookItem.class);
        BookItem bookModel2 = mock(BookItem.class);
        BookItem bookModel3 = mock(BookItem.class);
        BookItem bookModel4 = mock(BookItem.class);
        books.add(bookModel);
        books.add(bookModel2);
        books.add(bookModel3);
        books.add(bookModel4);
        return books;
    }

    private Map<BookModel, Boolean> getBookModelsMap() {
        Map<BookModel, Boolean> books = new HashMap<>();
        BookModel bookModel =   mock(BookModel.class);
        BookModel bookModel2 =  mock(BookModel.class);
        BookModel bookModel3 =  mock(BookModel.class);
        BookModel bookModel4 =  mock(BookModel.class);
        books.put(bookModel, false);
        books.put(bookModel2, false);
        books.put(bookModel3, false);
        books.put(bookModel4, false);
        return books;
    }
}