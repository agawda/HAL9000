package com.javaacademy.crawler.googlebooks.controllers;

import com.javaacademy.crawler.googlebooks.dao.GoogleBookEndpoint;
import com.javaacademy.crawler.googlebooks.retrofit.RetrofitHelper;
import org.testng.annotations.Test;
import retrofit2.Call;
import retrofit2.Callback;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertNotNull;

@Test
public class ControllerTest {

    public void getLimitedNumberBooksFromGoogleTest() {
        RetrofitHelper retrofitHelper = mock(RetrofitHelper.class);
        GoogleBookEndpoint endpoint = mock(GoogleBookEndpoint.class);
        Callback callback = mock(Callback.class);
        Call call = mock(Call.class);
        getControllerInstance(retrofitHelper, endpoint, call).getLimitedNumberBooksFromGoogle(callback, 1, 1);
        verify(endpoint, times(1)).getFortyGoogleBooks(any());
        verify(retrofitHelper, times(1)).getGoogleBooksEndpoint();
        verify(call, times(1)).enqueue(callback);
    }

    private Controller getControllerInstance(RetrofitHelper retrofitHelper,GoogleBookEndpoint endpoint, Call call) {
        Controller controller = new Controller();
        when(endpoint.getFortyGoogleBooks(any())).thenReturn(call);
        when(endpoint.getNumberOfGoogleBooks(any())).thenReturn(call);
        when(retrofitHelper.getGoogleBooksEndpoint()).thenReturn(endpoint);
        controller.retrofitHelper = retrofitHelper;
        return controller;
    }

    public void getHowManyBooksThereAreTest() {
        RetrofitHelper retrofitHelper = mock(RetrofitHelper.class);
        GoogleBookEndpoint endpoint = mock(GoogleBookEndpoint.class);
        Callback callback = mock(Callback.class);
        Call call = mock(Call.class);
        getControllerInstance(retrofitHelper, endpoint, call).getHowManyBooksThereAre(callback);
        verify(endpoint, times(1)).getNumberOfGoogleBooks(any());
        verify(retrofitHelper, times(1)).getGoogleBooksEndpoint();
        verify(call, times(1)).enqueue(callback);
    }

    public void loadPropertiesFailTest() {
        Controller.keyfileName = "test";
        Controller controller = new Controller();
        assertNotNull(controller);
    }

}