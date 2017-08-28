package com.javaacademy.crawler.googlebooks.retrofit;

import com.javaacademy.crawler.googlebooks.dao.GoogleBookEndpoint;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class RetrofitHelperTest {

    @Test
    public void testGettingGoogleBooksEndpoint() {
        RetrofitHelper retrofitHelper = new RetrofitHelper();
        GoogleBookEndpoint endpoint = retrofitHelper.getGoogleBooksEndpoint();
        assertNotNull(endpoint);
    }

}