package com.javaacademy.crawler.common.booksender;

import com.javaacademy.crawler.common.CustomCallback;
import com.javaacademy.crawler.common.converters.GoogleBookConverter;
import com.javaacademy.crawler.common.interfaces.Book;
import com.javaacademy.crawler.common.logger.AppLogger;
import com.javaacademy.crawler.common.model.BookModel;
import com.javaacademy.crawler.common.model.BookModels;
import com.javaacademy.crawler.common.retrofit.BookServerEndpoint;
import com.javaacademy.crawler.common.retrofit.SendingRetrofit;
import com.javaacademy.crawler.googlebooks.model.BookItem;
import retrofit2.Call;

import java.util.*;
import java.util.function.Consumer;

import static com.javaacademy.crawler.common.logger.AppLogger.DEFAULT_LEVEL;

public class BookSender {
    private Map<BookModel, Boolean> booksToSend; // a map which holds info about whether the book was successfully sent to server

    public BookSender(Set<Book> books) {
        GoogleBookConverter googleBookConverter = new GoogleBookConverter();
        this.booksToSend = new HashMap<>();
        books.forEach(bookModel ->
                booksToSend.put(googleBookConverter.convertToDto((BookItem) bookModel), false));
    }

    private void sendBooksTo(String serverIp, int numberOfBooks) {
        BookServerEndpoint endpoint = new SendingRetrofit().getBookBookServerEndpoint(serverIp);
        BookModels bookModels = selectBooksToSend(numberOfBooks);
        if (bookModels.isEmpty()) {
            return;
        }
        AppLogger.logger.log(DEFAULT_LEVEL, "Sending books to server: " + bookModels.toString());
        Call<Object> serverResponse = endpoint.putBooksToServer(bookModels);
        serverResponse.enqueue(new CustomCallback<>(createSuccessfulRequestConsumer(bookModels.getBookDtos())));
    }

    public void sendBooksTo(String serverIp) {
        int numberOfBooksSentAtOnce = 20;
        int maxNumberOfTries = booksToSend.size() * 2 / numberOfBooksSentAtOnce;
        for (int i = 0; i < maxNumberOfTries; i++) {
            if (areAllBooksSent()) {
                break;
            }
            sendBooksTo(serverIp, numberOfBooksSentAtOnce);
        }
    }

    private Consumer<Object> createSuccessfulRequestConsumer(List<BookModel> processedBooks) {
        return bookServerResponse -> markBooksAsSent(processedBooks);
    }

    private BookModels selectBooksToSend(int numberOfBooks) {
        List<BookModel> unsentBooks = new ArrayList<>();
        booksToSend.forEach((bookModel, aBoolean) -> {
            if (!aBoolean) {
                unsentBooks.add(bookModel);
            }
        });
        return new BookModels(getSublist(unsentBooks, numberOfBooks));
    }

    private List<BookModel> getSublist(List<BookModel> list, int numberOfElements) {
        int actualNumberOfElements = numberOfElements;
        if (numberOfElements > list.size()) {
            actualNumberOfElements = list.size();
        }
        return list.subList(0, actualNumberOfElements);
    }

    private void markBooksAsSent(List<BookModel> sentBooks) {
        for (BookModel bookModel : sentBooks) {
            booksToSend.put(bookModel, true);
        }
    }

    private boolean areAllBooksSent() {
        return booksToSend.values().stream().anyMatch(aBoolean -> aBoolean);
    }
}
