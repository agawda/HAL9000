package com.javaacademy.crawler.common.booksender;

import com.javaacademy.crawler.common.logger.AppLogger;
import com.javaacademy.crawler.common.model.BookModel;
import com.javaacademy.crawler.common.retrofit.BookServerEndpoint;
import com.javaacademy.crawler.common.retrofit.SendingRetrofit;
import org.testng.annotations.Test;
import retrofit2.Call;

import java.util.*;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class BookSenderTest {

    @Test
    public void testSendingBooks() {
        BookSender bookSender = new BookSender(getBookModelsMap().keySet());
        Call<String> serverResponse = mock(Call.class);
        bookSender.sendingRetrofit = getSendingRetrofitMock(serverResponse);
        BookSender.numberOfBooksSentAtOnce = 1;
        BookSender.sendingTimeInterval = 10;
        bookSender.sendBooksTo("IPADDRESS", "");
        verify(serverResponse, times(8)).enqueue(any());
    }

    @Test
    public void testMarkBooks() {
        AppLogger.initializeLogger();
        BookSender bookSender = new BookSender(getBookModelsMap().keySet());
        bookSender.booksToSend = getBookModelsMap();
        bookSender.markBooksAsSent(new ArrayList<>(bookSender.booksToSend.keySet()));
        assertFalse(bookSender.booksSendCounter.containsValue(0));
        assertFalse(bookSender.booksToSend.containsValue(false));
    }

    @Test
    public void testConstruct() {
        BookSender bookSender = new BookSender(getBookModelsMap().keySet());
        assertEquals(bookSender.booksToSend.size(), getBookModelsMap().keySet().size());
    }

    @Test
    public void validateBookAndAddToMapTestSamePrices() {
        Set<BookModel> books = new HashSet<>();
        double listPrice = 1.0;
        BookModel bookModel =   mock(BookModel.class);
        when(bookModel.getListPriceAmount()).thenReturn(listPrice);
        when(bookModel.getRetailPriceAmount()).thenReturn(listPrice);
        books.add(bookModel);
        BookSender bookSender = new BookSender(books);
        assertEquals(bookSender.booksToSend.size(), 0);
    }

    @Test
    public void validateBookAndAddToMapTestLowerPrices() {
        Set<BookModel> books = new HashSet<>();
        double listPrice = 1.0;
        double retailPrice = 2.0;
        BookModel bookModel =   mock(BookModel.class);
        when(bookModel.getListPriceAmount()).thenReturn(listPrice);
        when(bookModel.getRetailPriceAmount()).thenReturn(retailPrice);
        books.add(bookModel);
        BookSender bookSender = new BookSender(books);
        assertEquals(bookSender.booksToSend.size(), 0);
    }

    private SendingRetrofit getSendingRetrofitMock(Call<String> serverResponse) {
        SendingRetrofit sendingRetrofit = mock(SendingRetrofit.class);
        BookServerEndpoint endpoint = mock(BookServerEndpoint.class);
        when(sendingRetrofit.getBookBookServerEndpoint(any())).thenReturn(endpoint);
        when(endpoint.putBooksToServer(any())).thenReturn(serverResponse);
        return sendingRetrofit;
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