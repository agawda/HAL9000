package com.javaacademy.crawler.googlebooks.controllers;

import com.javaacademy.crawler.googlebooks.model.GoogleBooksWrapper;
import com.javaacademy.crawler.googlebooks.model.TotalItemsWrapper;
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

    private static final String BASE_GOOGLE_URL = "volumes?q=-&printType=books&filter=ebooks&orderBy=newest";

    public void getLimitedNumberBooksFromGoogle(Callback<GoogleBooksWrapper> callback, int start, int end) {
        GoogleBookEndpoint endpoint = new RetrofitHelper().getGoogleBooksEndpoint();
        Call<GoogleBooksWrapper> fortyGoogleBooks = endpoint.getFortyGoogleBooks(generateGoogleRangedUrl(start, end));
        fortyGoogleBooks.enqueue(callback);
    }

    public void getHowManyBooksThereAre(Callback<TotalItemsWrapper> callback) {
        GoogleBookEndpoint endpoint = new RetrofitHelper().getGoogleBooksEndpoint();
        Call<TotalItemsWrapper> fortyGoogleBooks = endpoint.getNumberOfGoogleBooks(getGoogleNumberOfBooksUrl());
        fortyGoogleBooks.enqueue(callback);
    }

    private String getGoogleNumberOfBooksUrl() {
        return BASE_GOOGLE_URL + "&fields=totalItems";
    }

    private String generateGoogleRangedUrl(int start, int end) {
        return BASE_GOOGLE_URL + "&startIndex=" + start + "&maxResults=" + end;
    }
}
