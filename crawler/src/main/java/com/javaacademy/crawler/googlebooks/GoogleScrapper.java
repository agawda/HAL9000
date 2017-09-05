package com.javaacademy.crawler.googlebooks;

import com.javaacademy.crawler.common.BookAddingCallback;
import com.javaacademy.crawler.common.CustomCallback;
import com.javaacademy.crawler.common.RequestStatus;
import com.javaacademy.crawler.common.interfaces.Book;
import com.javaacademy.crawler.common.logger.AppLogger;
import com.javaacademy.crawler.googlebooks.controllers.Controller;
import com.javaacademy.crawler.googlebooks.model.GoogleBooksWrapper;
import com.javaacademy.crawler.googlebooks.model.TotalItemsWrapper;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Level;

import static com.javaacademy.crawler.common.booksender.BookSender.displayProgress;
import static com.javaacademy.crawler.common.booksender.BookSender.printOnConsole;
import static com.javaacademy.crawler.common.logger.AppLogger.DEFAULT_LEVEL;

public class GoogleScrapper {
    static int SLEEP_TIME = 2000;
    static int MAX_VALUE = 1_000; //Should not be greater than 1000
    private Set<Book> books = new HashSet<>();
    Set<BookAddingCallback> callbacks = new HashSet<>();
    boolean isLoopDone = false;
    Controller controller = new Controller();

    public void runScrapping() {
        Consumer<TotalItemsWrapper> consumer =
                totalItemsWrapper -> collectAllBooksFromGoogle(
                        totalItemsWrapper.getTotalItems()
                );
        getNumberOfBooksAndStartCollection(consumer);
    }

    void getNumberOfBooksAndStartCollection(Consumer<TotalItemsWrapper> consumer) {
        CustomCallback<TotalItemsWrapper> customCallback = new CustomCallback<>(consumer);
        controller.getHowManyBooksThereAre(customCallback);
    }

    void collectAllBooksFromGoogle(int numOfBooks) {
        int step = 40;
        printOnConsole("Scrapping books from google:\n");
        for (int i = 0; i < numOfBooks; i += step) {
            long progress = i * 100 / MAX_VALUE;
            displayProgress(progress);
            BookAddingCallback<GoogleBooksWrapper> bookItemBookAddingCallback =
                    new BookAddingCallback<>(books,
                            "Google Bookstore " + i + " - " + (i + step));
            callbacks.add(bookItemBookAddingCallback);
            int end = step;
            int nextStep = i + step;
            if (nextStep >= MAX_VALUE) {
                end = MAX_VALUE - i;
                getGoogleBooks(i, end, bookItemBookAddingCallback);
                break;
            }
            getGoogleBooks(i, end, bookItemBookAddingCallback);
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                AppLogger.logger.log(Level.WARNING, "Exception while waiting, ", e);
                Thread.currentThread().interrupt();
            }
        }
        displayProgress(100);
        AppLogger.logger.log(DEFAULT_LEVEL, "All callbacks done!");
        isLoopDone = true;
    }

    private void getGoogleBooks(int start, int end, BookAddingCallback<GoogleBooksWrapper> bookItemBookAddingCallback) {
        AppLogger.logger.log(DEFAULT_LEVEL, (String.format("start = %d end %d", start, end)));
        controller.getLimitedNumberBooksFromGoogle(bookItemBookAddingCallback, start, end);
    }

    public boolean areAllCallbacksDone() {
        boolean areCallbacksDone = false;
        if (isLoopDone) {
            AppLogger.logger.log(DEFAULT_LEVEL, "Callbacks loop done");
            areCallbacksDone = callbacks.stream().noneMatch(bookAddingCallback ->
                    bookAddingCallback.getRequestStatus() == RequestStatus.STARTED);
            AppLogger.logger.log(DEFAULT_LEVEL, "areCallbacksDone: " + areCallbacksDone);
        }
        return isLoopDone && areCallbacksDone;
    }

    public Set<Book> getBooks() {
        return books;
    }
}
