package com.javaacademy.crawler.common.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SendingRetrofit {

    public BookServerEndpoint getBookBookServerEndpoint(String hostAddress) {
        Retrofit retrofit = createRetrofitInstance(hostAddress);
        return retrofit.create(BookServerEndpoint.class);
    }

    private Retrofit createRetrofitInstance(String hostAddress) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(System.out::println);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        return new Retrofit.Builder()
                .baseUrl(hostAddress)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }
}
