package com.javaacademy.crawler.googlebooks.controllers;

import com.javaacademy.crawler.googlebooks.model.GoogleBooksWrapper;
import com.javaacademy.crawler.googlebooks.retrofit.RetrofitHelper;
import com.javaacademy.crawler.googlebooks.dao.GoogleBookEndpoint;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * @author devas
 * @author mprtcz
 * @since 24.08.17
 */
public class Controller {

    public void getFortyBooksFromGoogle(Callback<GoogleBooksWrapper> callback) {
        GoogleBookEndpoint endpoint = new RetrofitHelper().getGoogleBooksEndpoint(0, 40);
        Call<GoogleBooksWrapper> fortyGoogleBooks = endpoint.getFortyGoogleBooks();
        fortyGoogleBooks.enqueue(callback);
    }
}
