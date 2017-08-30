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
import java.util.logging.Level;

import static com.javaacademy.crawler.common.logger.AppLogger.DEFAULT_LEVEL;
import static com.javaacademy.crawler.common.logger.AppLogger.logger;

public class BookSender {
    private Map<BookModel, Boolean> booksToSend; // a map which holds info about whether the book was successfully sent to server
    private Map<BookModel, Integer> booksSendCounter;

    public BookSender(Set<Book> books) {
        GoogleBookConverter googleBookConverter = new GoogleBookConverter();
        this.booksToSend = new HashMap<>();
        this.booksSendCounter = new HashMap<>();
        books.forEach(bookModel ->
                booksToSend.put(googleBookConverter.convertToDto((BookItem) bookModel), false));
        books.forEach(bookModel ->
                booksSendCounter.put(googleBookConverter.convertToDto((BookItem) bookModel), 0));
    }

    private void sendBooksTo(String serverIp, int numberOfBooks) {
        BookServerEndpoint endpoint = new SendingRetrofit().getBookBookServerEndpoint(serverIp);
        BookModels bookModels = selectBooksToSend(numberOfBooks);
        if (bookModels.isEmpty()) {
            return;
        }
        AppLogger.logger.log(DEFAULT_LEVEL, "Sending books to server: " + bookModels.toString());
        Call<String> serverResponse = endpoint.putBooksToServer(bookModels);
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
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.log(Level.WARNING, "Interrupted waiting when sending books to book server: ", e);
                Thread.currentThread().interrupt();
            }
            long progress = (booksToSend.values().stream().filter(aBoolean -> aBoolean).count()
                    * 100) / booksToSend.size();
            displayProgress(progress);
        }
    }

    private Consumer<String> createSuccessfulRequestConsumer(List<BookModel> processedBooks) {
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
        logger.log(DEFAULT_LEVEL, "Number of sent books before update = " + booksToSend.values().stream().filter(aBoolean -> aBoolean).count());
        for (BookModel bookModel : sentBooks) {
            booksToSend.put(bookModel, true);
            booksSendCounter.merge(bookModel, 0, (integer, integer2) -> integer + 1);
        }
        logger.log(DEFAULT_LEVEL, "Number of sent books after update = " + booksToSend.values().stream().filter(aBoolean -> aBoolean).count());
        logger.log(DEFAULT_LEVEL, "How many times books were sent: " +booksSendCounter.values());
    }

    private boolean areAllBooksSent() {
        boolean areAllBooksSent = booksToSend.values().stream().allMatch(aBoolean -> aBoolean);
        logger.log(DEFAULT_LEVEL, "Are all books sent: " + areAllBooksSent);
        return areAllBooksSent;
    }

    public static void displayProgress(long progress) {
        if(progress > 100) {return;}
        char c;
        System.out.print("[");
        for (int i = 0; i <= 100; i++) {
            c = i > progress ? '-' : '+';
            System.out.print(c + " ");
        }
        System.out.println("]");
    }
}
