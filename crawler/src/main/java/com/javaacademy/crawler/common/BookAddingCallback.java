package com.javaacademy.crawler.common;

import com.javaacademy.crawler.common.interfaces.Book;
import com.javaacademy.crawler.common.interfaces.BooksWrapper;
import lombok.Data;
import lombok.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Set;

@Data
public class  BookAddingCallback<T extends BooksWrapper> implements Callback<T> {
    @NonNull
    private Set<Book> books;
    @NonNull
    private String bookstoreName;
    private RequestStatus requestStatus = RequestStatus.STARTED;

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            BooksWrapper bw = response.body();
            System.out.println("bw = " + bw.getItems());
            books.addAll(bw.getItems());
            requestStatus = RequestStatus.COMPLETED;
        } else {
            System.err.println("error " + response.code());
            requestStatus = RequestStatus.ERROR;
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable throwable) {
        System.err.println("There was an error while obtaining items: "
                + throwable.getMessage());
        requestStatus = RequestStatus.ERROR;
    }
}
