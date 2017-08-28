package com.javaacademy.crawler.common;

import com.javaacademy.crawler.common.logger.AppLogger;
import lombok.Data;
import lombok.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.function.Consumer;
import java.util.logging.Level;

@Data
public class CustomCallback<T> implements Callback<T> {
    @NonNull
    Consumer<T> consumer;

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.isSuccessful()) {
            consumer.accept(response.body());
        } else {
            AppLogger.logger.log(Level.WARNING, "error code " + response.code() +
                    ", message = " +response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable throwable) {
        AppLogger.logger.log(Level.WARNING, "There was an error while making a custom retrofit call: "
                + throwable.getMessage());
    }
}
