package com.javaacademy.crawler.common.booksender;

import com.javaacademy.crawler.common.CustomCallback;
import com.javaacademy.crawler.common.converters.GoogleBookConverter;
import com.javaacademy.crawler.common.interfaces.Book;
import com.javaacademy.crawler.common.logger.AppLogger;
import com.javaacademy.crawler.common.model.BookDtos;
import com.javaacademy.crawler.common.model.BookModel;
import com.javaacademy.crawler.common.retrofit.BookServerEndpoint;
import com.javaacademy.crawler.common.retrofit.BookServerResponse;
import com.javaacademy.crawler.common.retrofit.SendingRetrofit;
import com.javaacademy.crawler.googlebooks.model.BookItem;
import retrofit2.Call;

import java.util.*;

import static com.javaacademy.crawler.common.logger.AppLogger.DEFAULT_LEVEL;

public class BookSender {
    private Map<BookModel, Boolean> booksToSend; // a map which holds info about whether the book was successfully sent to server

    public BookSender(Set<Book> books) {
        GoogleBookConverter googleBookConverter = new GoogleBookConverter();
        this.booksToSend = new HashMap<>();
        books.forEach(bookModel ->
                booksToSend.put(googleBookConverter.convertToDto((BookItem) bookModel), false));
    }

    public void sendBooksTo(String serverIp) {
        BookServerEndpoint endpoint =
                new SendingRetrofit().getBookBookServerEndpoint(serverIp);
        BookDtos bookDtos = selectBooksToSend(20);
        if (bookDtos.isEmpty()) {
            return;
        }
        AppLogger.logger.log(DEFAULT_LEVEL, "Sending books to server: " + bookDtos.toString());
        Call<BookServerResponse> serverResponse =
                endpoint.putBooksToServer(bookDtos);
        serverResponse.enqueue(new CustomCallback<>(
                bookServerResponse -> AppLogger.logger.log(DEFAULT_LEVEL, "Request completed successfully")));

    }

    private BookDtos selectBooksToSend(int numberOfBooks) {
        List<BookModel> unsentBooks = new ArrayList<>();
        booksToSend.forEach((bookModel, aBoolean) -> {
            if (!aBoolean) {
                unsentBooks.add(bookModel);
            }
        });
        return new BookDtos(getSublist(unsentBooks, numberOfBooks));
    }

    private List<BookModel> getSublist(List<BookModel> list, int numberOfElements) {
        if (numberOfElements > list.size()) {
            numberOfElements = list.size();
        }
        return list.subList(0, numberOfElements);
    }
}
