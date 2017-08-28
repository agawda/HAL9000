package com.javaacademy.crawler.googlebooks.dao;

import com.javaacademy.crawler.googlebooks.model.GoogleBooksWrapper;
import com.javaacademy.crawler.googlebooks.model.TotalItemsWrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * @author devas
 * @author mprtcz
 * @since 24.08.17
 */
public interface GoogleBookEndpoint {

    @GET()
    Call<GoogleBooksWrapper> getFortyGoogleBooks(@Url String url);
    @GET()
    Call<TotalItemsWrapper> getNumberOfGoogleBooks(@Url String url);
}
