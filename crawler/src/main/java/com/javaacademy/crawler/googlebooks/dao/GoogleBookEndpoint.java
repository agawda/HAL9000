package com.javaacademy.crawler.googlebooks.dao;

import com.javaacademy.crawler.googlebooks.model.GoogleBooksWrapper;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author devas
 * @author mprtcz
 * @since 24.08.17
 */
public interface GoogleBookEndpoint {

    String parameters = "volumes?q=-&printType=books&filter=ebooks&orderBy=newest&startIndex=0&maxResults=40";

    @GET(parameters)
    Call<GoogleBooksWrapper> getFortyGoogleBooks();
}
