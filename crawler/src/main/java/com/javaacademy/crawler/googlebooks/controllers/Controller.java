package com.javaacademy.crawler.googlebooks.controllers;

import com.javaacademy.crawler.common.logger.AppLogger;
import com.javaacademy.crawler.googlebooks.dao.GoogleBookEndpoint;
import com.javaacademy.crawler.googlebooks.model.GoogleBooksWrapper;
import com.javaacademy.crawler.googlebooks.model.TotalItemsWrapper;
import com.javaacademy.crawler.googlebooks.retrofit.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;

/**
 * @author devas
 * @author mprtcz
 * @since 24.08.17
 */
public class Controller {

    private static final String BASE_GOOGLE_URL = "volumes?q=-&printType=books&filter=ebooks&orderBy=newest";
    private String googleKey;

    public Controller() {
        try (InputStream in = getPropertyFile()) {
            Properties properties = new Properties();
            properties.load(in);
            googleKey = properties.getProperty("GoogleKey");
        } catch (IOException | NullPointerException e) {
            AppLogger.logger.log(Level.WARNING, "Could not find file", e);
        }
    }

    private InputStream getPropertyFile() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        return classloader.getResourceAsStream("key.txt");
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
        return BASE_GOOGLE_URL + "&fields=totalItems" + googleKey;
    }

    private String generateGoogleRangedUrl(int start, int end) {
        return BASE_GOOGLE_URL + "&startIndex=" + start + "&maxResults=" + end + googleKey;
    }
}
