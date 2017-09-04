package com.javaacademy.crawler.googlebooks.controllers;

import com.javaacademy.crawler.googlebooks.model.GoogleBooksWrapper;
import com.javaacademy.crawler.googlebooks.model.TotalItemsWrapper;
import com.javaacademy.crawler.googlebooks.retrofit.RetrofitHelper;
import com.javaacademy.crawler.googlebooks.dao.GoogleBookEndpoint;
import retrofit2.Call;
import retrofit2.Callback;

import java.io.*;
import java.util.Properties;

/**
 * @author devas
 * @author mprtcz
 * @since 24.08.17
 */
public class Controller {

    private static final String BASE_GOOGLE_URL = "volumes?q=-&printType=books&filter=ebooks&orderBy=newest";
    private static String GOOGLE_KEY;

    public Controller() {
        try (FileInputStream in = getPropertyFile()) {
            Properties properties = new Properties();
            properties.load(in);
            GOOGLE_KEY = properties.getProperty("GoogleKey");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FileInputStream getPropertyFile() throws FileNotFoundException {
        return new FileInputStream(getClass().getClassLoader().getResource("key.txt").getFile());
    }

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
        return BASE_GOOGLE_URL + "&fields=totalItems" + GOOGLE_KEY;
    }

    private String generateGoogleRangedUrl(int start, int end) {
        return BASE_GOOGLE_URL + "&startIndex=" + start + "&maxResults=" + end + GOOGLE_KEY;
    }
}
