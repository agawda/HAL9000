package com.javaacademy.crawler.common.retrofit;

import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class SendingRetrofitTest {


    @Test
    public void createRetrofitInstance() {
        SendingRetrofit sendingRetrofit = new SendingRetrofit();
        BookServerEndpoint bookServerEndpoint =
                sendingRetrofit.getBookBookServerEndpoint(
                        "http://TestAddress");
        assertNotNull(bookServerEndpoint);
    }

}