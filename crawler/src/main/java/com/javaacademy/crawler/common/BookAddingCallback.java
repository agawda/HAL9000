package com.javaacademy.crawler.common;

import com.javaacademy.crawler.common.interfaces.Book;
import com.javaacademy.crawler.common.interfaces.BooksWrapper;
import com.javaacademy.crawler.common.logger.AppLogger;
import lombok.Data;
import lombok.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Set;
import java.util.logging.Level;

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
            books.addAll(bw.getItems());
            requestStatus = RequestStatus.COMPLETED;
        } else {
            AppLogger.logger.log(Level.WARNING, "error " + response.code());
            requestStatus = RequestStatus.ERROR;
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable throwable) {
        AppLogger.logger.log(Level.WARNING, "There was an error while obtaining items: "
                + throwable.getMessage());
        requestStatus = RequestStatus.ERROR;
    }
}
