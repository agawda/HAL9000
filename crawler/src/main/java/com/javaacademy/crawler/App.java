package com.javaacademy.crawler;

import com.javaacademy.crawler.googlebooks.controllers.Controller;
import com.javaacademy.crawler.googlebooks.model.GoogleBooksWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author devas
 * @author mprtcz
 * @since 24.08.17
 */
public class App {

    public static void main(String[] args) {
        new Controller().getFortyBooksFromGoogle(new Callback<GoogleBooksWrapper>() {
            @Override
            public void onResponse(Call<GoogleBooksWrapper> call, Response<GoogleBooksWrapper> response) {
                if(response.isSuccessful()) {
                    System.out.println(response.body());
                } else {
                    System.out.println("error");
                }
            }

            @Override
            public void onFailure(Call<GoogleBooksWrapper> call, Throwable throwable) {
                System.out.println("error");
            }
        });
    }
}
