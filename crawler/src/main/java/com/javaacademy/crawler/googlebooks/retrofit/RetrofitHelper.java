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

    public GoogleBookEndpoint getGoogleBooksEndpoint() {
        Retrofit retrofit = createRetrofitInstance(BASE_GOOGLE_URL);
        return retrofit.create(GoogleBookEndpoint.class);
    }

    private Retrofit createRetrofitInstance(String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
