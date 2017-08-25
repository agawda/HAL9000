package com.javaacademy.crawler;

import com.javaacademy.crawler.googlebooks.controllers.Controller;
import com.javaacademy.crawler.googlebooks.model.BookItem;
import com.javaacademy.crawler.googlebooks.model.GoogleBooksWrapper;
import com.javaacademy.crawler.googlebooks.model.TotalItemsWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.HashSet;
import java.util.Set;

/**
 * @author devas
 * @author mprtcz
 * @since 24.08.17
 */
public class App {
    private static final int SLEEP_TIME = 5000;
    private static final int MAX_VALUE = 1000;

    public static void main(String[] args) {
        getNumberOfBooks();
    }

    private static void getNumberOfBooks() {
        new Controller().getHowManyBooksThereAre(new Callback<TotalItemsWrapper>() {
            @Override
            public void onResponse(Call<TotalItemsWrapper> call, Response<TotalItemsWrapper> response) {
                if (response.isSuccessful()) {
                    int totalItems = response.body().getTotalItems();
                    collectAllBooksFromGoogle(totalItems);
                }
            }

            @Override
            public void onFailure(Call<TotalItemsWrapper> call, Throwable throwable) {
                System.err.println("There was an error while obtaining total items from google: "
                        + throwable.getMessage());
            }
        });
    }

    private static void collectAllBooksFromGoogle(int numOfBooks) {
        int step = 40;
        Set<BookItem> books = new HashSet<>();
        for (int i = 0; i < numOfBooks; i += step) {
            System.out.println("i = " + i);
            int end = step;
            int nextStep = i + step;
            if (nextStep >= MAX_VALUE) {
                end = MAX_VALUE - i;
                getGoogleBooks(i, end, books);
                break;
            }
            getGoogleBooks(i, end, books);
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Breaking the loop");
    }

    private static void getGoogleBooks(int start, int end, Set<BookItem> list) {
        System.out.println(String.format("start = %d end %d list.size() %d ", start, end, list.size()));
        new Controller().getLimitedNumberBooksFromGoogle(new Callback<GoogleBooksWrapper>() {
            @Override
            public void onResponse(Call<GoogleBooksWrapper> call, Response<GoogleBooksWrapper> response) {
                if (response.isSuccessful()) {
                    list.addAll(response.body().getItems());
                } else {
                    System.err.println("error " + response.code());
                }
            }

            @Override
            public void onFailure(Call<GoogleBooksWrapper> call, Throwable throwable) {
                System.err.println("Error while getting books from google: " + throwable.getMessage());
            }
        }, start, end);
    }
}
