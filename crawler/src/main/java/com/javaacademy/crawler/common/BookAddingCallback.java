package com.javaacademy.crawler.common;

import com.javaacademy.crawler.common.converters.GoogleBookConverter;
import com.javaacademy.crawler.common.interfaces.BooksWrapper;
import com.javaacademy.crawler.common.logger.AppLogger;
import com.javaacademy.crawler.common.model.BookModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Set;
import java.util.logging.Level;

import static com.javaacademy.crawler.common.logger.AppLogger.DEFAULT_LEVEL;

@Getter
@EqualsAndHashCode
public class  BookAddingCallback<T extends BooksWrapper> implements Callback<T> {
    private Set<BookModel> books;
    String bookstoreName;
    private RequestStatus requestStatus = RequestStatus.STARTED;

    public BookAddingCallback(Set<BookModel> books, String bookstoreName) {
        this.books = books;
        this.bookstoreName = bookstoreName;
    }

    public RequestStatus getRequestStatus() {
        AppLogger.logger.log(DEFAULT_LEVEL, "Checking callback's status: " +requestStatus);
        return requestStatus;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            try {
                BooksWrapper bw = response.body();
                books.addAll(bw.getItems(new GoogleBookConverter()));
                AppLogger.logger.log(DEFAULT_LEVEL, "Callback completed");
                requestStatus = RequestStatus.COMPLETED;
            } catch (Exception e) {
                AppLogger.logger.log(Level.WARNING, "Exception during response conversion: ", e);

            }
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
