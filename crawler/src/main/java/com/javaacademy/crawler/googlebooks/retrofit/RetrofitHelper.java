package com.javaacademy.crawler.googlebooks.retrofit;

import com.javaacademy.crawler.googlebooks.dao.GoogleBookEndpoint;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author devas
 * @author mprtcz
 * @since 24.08.17
 */
public class RetrofitHelper {

    private static final String BASE_GOOGLE_URL = "https://www.googleapis.com/books/v1/";

    public GoogleBookEndpoint getGoogleBooksEndpoint(int start, int end) {
        Retrofit retrofit = createRetrofitInstance(generateGoogleRangedUrl(start, end));
        return retrofit.create(GoogleBookEndpoint.class);
    }

    private Retrofit createRetrofitInstance(String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private String generateGoogleRangedUrl(int start, int end) {
        return BASE_GOOGLE_URL
                .replace("startIndex=", "startIndex=" + start)
                .replace("maxResults=", "maxResults=" + end);
    }
}
