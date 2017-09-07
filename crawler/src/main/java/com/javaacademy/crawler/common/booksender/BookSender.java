package com.javaacademy.crawler.common.booksender;

import com.javaacademy.crawler.common.CustomCallback;
import com.javaacademy.crawler.common.converters.GoogleBookConverter;
import com.javaacademy.crawler.common.interfaces.Book;
import com.javaacademy.crawler.common.logger.AppLogger;
import com.javaacademy.crawler.common.model.BookModel;
import com.javaacademy.crawler.common.model.BookModels;
import com.javaacademy.crawler.common.retrofit.BookServerEndpoint;
import com.javaacademy.crawler.common.retrofit.SendingRetrofit;
import retrofit2.Call;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static com.javaacademy.crawler.common.logger.AppLogger.*;
import static com.javaacademy.crawler.common.util.CrawlerUtils.sleepFor;

public class BookSender {

    Map<BookModel, Boolean> booksToSend; // a map which holds info about whether the book was successfully sent to server
    Map<BookModel, Integer> booksSendCounter;
    static int numberOfBooksSentAtOnce = 20;
    SendingRetrofit sendingRetrofit = new SendingRetrofit();

    public BookSender(Set<Book> books, GoogleBookConverter googleBookConverter) {
        this.booksToSend = new HashMap<>();
        this.booksSendCounter = new HashMap<>();
        List<BookModel> bookModels = googleBookConverter.convertToDtosWithoutNulls(books);
        bookModels.forEach(bookItem ->
                booksToSend.put(bookItem, false));
    }

    public BookSender(Set<BookModel> books) {
        this.booksToSend = new HashMap<>();
        this.booksSendCounter = new HashMap<>();
        books.forEach(bookItem -> booksToSend.put(bookItem, false));
    }

    private void sendBooksTo(String serverIp, int numberOfBooks) {
        BookServerEndpoint endpoint = sendingRetrofit.getBookBookServerEndpoint(serverIp);
        BookModels bookModels = selectBooksToSend(numberOfBooks);
        if (bookModels.isEmpty()) {
            return;
        }
        AppLogger.logger.log(DEFAULT_LEVEL, "Sending books to server: " + bookModels.toString());
        Call<String> serverResponse = endpoint.putBooksToServer(bookModels);
        serverResponse.enqueue(new CustomCallback<>(createSuccessfulRequestConsumer(bookModels.getBookDtos())));
    }

    public void sendBooksTo(String serverIp, String bookstoreName) {
        AppLogger.logger.log(DEFAULT_LEVEL, "Sending books to server from " + bookstoreName);
        int maxNumberOfTries = booksToSend.size() * 2 / numberOfBooksSentAtOnce;
        printOnConsole("Sending scrapped books to server from " + bookstoreName + ", number: " + booksToSend.size() + ":\n");
        long millisWhenStaredSending = System.nanoTime();
        for (int i = 0; i < maxNumberOfTries; i++) {
            if (areAllBooksSent()) {
                break;
            }
            sendBooksTo(serverIp, numberOfBooksSentAtOnce);
            sleepFor(1000, "when sending books to book server");
            displayProgress(booksToSend.values().stream().filter(aBoolean -> aBoolean).count(), booksToSend.size());
        }
        long numberOfSentBooks = booksToSend.values().stream().filter(aBoolean -> aBoolean).count();
        statistics.info("Sending books scrapped from " + bookstoreName + " to server complete, took: "
                + TimeUnit.SECONDS.convert((System.nanoTime() - millisWhenStaredSending), TimeUnit.NANOSECONDS) + "s");
        logAndAddStat("Total books sent: " + numberOfSentBooks);
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

    void markBooksAsSent(List<BookModel> sentBooks) {
        for (BookModel bookModel : sentBooks) {
            booksToSend.put(bookModel, true);
            booksSendCounter.merge(bookModel, 1, (integer, integer2) -> integer + 1);
        }
        logger.log(DEFAULT_LEVEL, "How many times books were sent: " + booksSendCounter.values());
    }

    boolean areAllBooksSent() {
        boolean areAllBooksSent = booksToSend.values().stream().allMatch(aBoolean -> aBoolean);
        logger.log(DEFAULT_LEVEL, "Are all books sent: " + areAllBooksSent);
        return areAllBooksSent;
    }

    public static void displayProgress(long loopIndex, long maxLooppIndex) {
        long progress = loopIndex * 100 / maxLooppIndex;
        if (progress > 100) {
            return;
        }
        char c;
        printOnConsole("[");
        for (int i = 0; i <= 100; i++) {
            c = i > progress ? '-' : '|';
            printOnConsole(Character.toString(c));
        }
        printOnConsole("] " + progress + "%\n");
    }

    public static void printOnConsole(String s) {
        System.out.print(s);
    }
}
