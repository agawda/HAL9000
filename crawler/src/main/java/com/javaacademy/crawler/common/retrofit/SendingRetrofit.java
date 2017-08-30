package com.javaacademy.crawler.common.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.javaacademy.crawler.common.logger.AppLogger;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.javaacademy.crawler.common.logger.AppLogger.DEFAULT_LEVEL;

public class SendingRetrofit {

    public BookServerEndpoint getBookBookServerEndpoint(String hostAddress) {
        Retrofit retrofit = createRetrofitInstance(hostAddress);
        return retrofit.create(BookServerEndpoint.class);
    }

    private Retrofit createRetrofitInstance(String hostAddress) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(s ->
                AppLogger.logger.log(DEFAULT_LEVEL, s));
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(hostAddress)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
    }
}
