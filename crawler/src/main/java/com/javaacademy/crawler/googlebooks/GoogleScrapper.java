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
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static com.javaacademy.crawler.common.booksender.BookSender.displayProgress;
import static com.javaacademy.crawler.common.booksender.BookSender.printOnConsole;
import static com.javaacademy.crawler.common.logger.AppLogger.DEFAULT_LEVEL;
import static com.javaacademy.crawler.common.logger.AppLogger.statistics;
import static com.javaacademy.crawler.common.util.CrawlerUtils.sleepFor;

public class GoogleScrapper {
    static int sleepTime = 2000;
    static int maxValue = 1_000; //Should not be greater than 1000
    Set<Book> books = new HashSet<>();
    Set<BookAddingCallback> callbacks = new HashSet<>();
    boolean isLoopDone = false;
    Controller controller = new Controller();
    private long googleScrappingStartTime;

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
        statistics.info("Google books scrapping started");
        googleScrappingStartTime = System.nanoTime();
        printOnConsole("Scrapping books from google:\n");
        for (int i = 0; i < numOfBooks; i += step) {
            long progress = i * 100 / maxValue;
            displayProgress(progress);
            BookAddingCallback<GoogleBooksWrapper> bookItemBookAddingCallback =
                    new BookAddingCallback<>(books,
                            "Google Bookstore " + i + " - " + (i + step));
            callbacks.add(bookItemBookAddingCallback);
            int end = step;
            int nextStep = i + step;
            if (nextStep >= maxValue) {
                end = maxValue - i;
                getGoogleBooks(i, end, bookItemBookAddingCallback);
                break;
            }
            getGoogleBooks(i, end, bookItemBookAddingCallback);
            sleepFor(sleepTime, "");
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
            areCallbacksDone = callbacks.stream().noneMatch(bookAddingCallback -> bookAddingCallback.getRequestStatus() == RequestStatus.STARTED);
            AppLogger.logger.log(DEFAULT_LEVEL, "areCallbacksDone: " + areCallbacksDone);
            if (areCallbacksDone) {
                statistics.info("Google scrapping complete, took: " + TimeUnit.SECONDS.convert((System.nanoTime() - googleScrappingStartTime), TimeUnit.NANOSECONDS) +"s");
                statistics.info("Books scrapped from Google REST API: " + books.size());
            }
        }
        return isLoopDone && areCallbacksDone;
    }

    public Set<Book> getBooks() {
        return books;
    }
}
