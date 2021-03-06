package com.javaacademy.crawler.common;

import com.javaacademy.crawler.common.interfaces.BooksWrapper;
import okhttp3.ResponseBody;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import retrofit2.Call;
import retrofit2.Response;

import java.util.function.Consumer;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class CustomCallbackTest {
    private Consumer consumer;
    private CustomCallback<BooksWrapper> customCallback;
    private Response<BooksWrapper> response;

    @BeforeClass
    public void beforeClass() {
        consumer = mock(Consumer.class);
        BooksWrapper booksWrapper = mock(BooksWrapper.class);
        response = Response.success(booksWrapper);
        customCallback = new CustomCallback<>(consumer);
    }

    @Test
    public void testOnResponseSuccess() {
        Consumer consumerSuccess = mock(Consumer.class);
        BooksWrapper booksWrapperSucces = mock(BooksWrapper.class);
        Response<BooksWrapper> responseSuccess = Response.success(booksWrapperSucces);
        CustomCallback<BooksWrapper> customCallbackSuccess = new CustomCallback<>(consumerSuccess);
        customCallbackSuccess.onResponse(null, responseSuccess);
        verify(consumerSuccess, times(1)).accept(any());
    }

    @Test
    public void testOnResponseError() {
        ResponseBody responseBody = mock(ResponseBody.class);
        response = Response.error(500, responseBody);
        customCallback.onResponse(null, response);
        verify(consumer, times(0)).accept(any());
    }

    @Test
    public void testOnFailure() {
        Throwable throwable = new Throwable("Test");
        Call call = mock(Call.class);
        customCallback.onFailure(call, throwable);
        verify(consumer, times(0)).accept(any());
    }

    @Test
    public void testEqualsAndGetter() {
        CustomCallback<BooksWrapper> callback2 = new CustomCallback<>(consumer);
        assertTrue(callback2.equals(customCallback));
        Consumer consumer2 = mock(Consumer.class);
        callback2 = new CustomCallback<>(consumer2);
        assertEquals(callback2.getConsumer(), consumer2);
        assertFalse(customCallback.equals(callback2));
    }

}