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
import static com.javaacademy.crawler.common.logger.AppLogger.DEFAULT_LEVEL;

public class GoogleScrapper {
    private static final int SLEEP_TIME = 2000;
    private static final int MAX_VALUE = 1_000; //Should not be greater than 1000
    private Set<Book> books = new HashSet<>();
    private Set<BookAddingCallback> callbacks = new HashSet<>();
    private boolean isLoopDone = false;

    public void runScrapping() {
        getNumberOfBooksAndStartCollection();
    }

    private void getNumberOfBooksAndStartCollection() {
        Consumer<TotalItemsWrapper> consumer =
                totalItemsWrapper -> collectAllBooksFromGoogle(
                        totalItemsWrapper.getTotalItems()
                );
        CustomCallback<TotalItemsWrapper> customCallback = new CustomCallback<>(consumer);
        new Controller().getHowManyBooksThereAre(customCallback);
    }

    private void collectAllBooksFromGoogle(int numOfBooks) {
        int step = 40;
        for (int i = 0; i < numOfBooks; i += step) {
            long progress = i * 100 / MAX_VALUE;
            displayProgress(progress);
            BookAddingCallback<GoogleBooksWrapper> bookItemBookAddingCallback =
                    new BookAddingCallback<>(books, "Google Bookstore");
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
                AppLogger.logger.log(Level.WARNING, "Exception while waaiting, ", e);
                Thread.currentThread().interrupt();
            }
        }
        AppLogger.logger.log(DEFAULT_LEVEL, "All callbacks done!");
        isLoopDone = true;
    }

    private void getGoogleBooks(int start, int end, BookAddingCallback<GoogleBooksWrapper> bookItemBookAddingCallback) {
        AppLogger.logger.log(DEFAULT_LEVEL, (String.format("start = %d end %d", start, end)));
        new Controller().getLimitedNumberBooksFromGoogle(bookItemBookAddingCallback, start, end);
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
