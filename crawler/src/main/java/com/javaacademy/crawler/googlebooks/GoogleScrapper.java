package com.javaacademy.crawler.googlebooks;

import com.javaacademy.crawler.Scrapper;
import com.javaacademy.crawler.common.BookAddingCallback;
import com.javaacademy.crawler.common.CustomCallback;
import com.javaacademy.crawler.common.RequestStatus;
import com.javaacademy.crawler.common.converters.GoogleBookConverter;
import com.javaacademy.crawler.common.interfaces.Book;
import com.javaacademy.crawler.common.logger.AppLogger;
import com.javaacademy.crawler.common.model.BookModel;
import com.javaacademy.crawler.googlebooks.controllers.Controller;
import com.javaacademy.crawler.googlebooks.model.GoogleBooksWrapper;
import com.javaacademy.crawler.googlebooks.model.TotalItemsWrapper;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import static com.javaacademy.crawler.common.booksender.BookSender.displayProgress;
import static com.javaacademy.crawler.common.booksender.BookSender.printOnConsole;
import static com.javaacademy.crawler.common.logger.AppLogger.*;
import static com.javaacademy.crawler.common.util.CrawlerUtils.sleepFor;

public class GoogleScrapper implements Scrapper {
    static int sleepTime = 2000;
    static int maxValue = 1_000; //Should not be greater than 1000
    static int completionWaitingInterval = 6000;
    Set<Book> books = new HashSet<>();
    Set<BookAddingCallback> callbacks = new HashSet<>();
    boolean isLoopDone = false;
    Controller controller = new Controller();
    private long googleScrappingStartTime;

    public Set<BookModel> scrape() {
        Consumer<TotalItemsWrapper> consumer =
                totalItemsWrapper -> collectAllBooksFromGoogle(
                        totalItemsWrapper.getTotalItems()
                );
        getNumberOfBooksAndStartCollection(consumer);
        Set<Book> scrappedBooks =  waitForCallbacksToComplete(completionWaitingInterval);
        return new GoogleBookConverter().convertToDtosWithoutNulls(scrappedBooks);
    }

    @Override
    public String getName() {
        return "Google";
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
            displayProgress(i, maxValue);
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
        displayProgress(100, 100);
        AppLogger.logger.log(DEFAULT_LEVEL, "All callbacks done!");
        isLoopDone = true;
    }

    private void getGoogleBooks(int start, int end, BookAddingCallback<GoogleBooksWrapper> bookItemBookAddingCallback) {
        AppLogger.logger.log(DEFAULT_LEVEL, (String.format("start = %d end %d", start, end)));
        controller.getLimitedNumberBooksFromGoogle(bookItemBookAddingCallback, start, end);
    }

    public boolean areAllCallbacksDone() {
        boolean areCallbacksDone = false;
        System.out.println("is loop done " +isLoopDone);
        if (isLoopDone) {
            AppLogger.logger.log(DEFAULT_LEVEL, "Callbacks loop done");
            areCallbacksDone = callbacks.stream().noneMatch(bookAddingCallback -> bookAddingCallback.getRequestStatus() == RequestStatus.STARTED);
            AppLogger.logger.log(DEFAULT_LEVEL, "areCallbacksDone: " + areCallbacksDone);
            if (areCallbacksDone) {
                logScrappingInfo("Google Market", googleScrappingStartTime, books.size());
            }
        }
        return isLoopDone && areCallbacksDone;
    }

    public Set<Book> getBooks() {
        return books;
    }

    private Set<Book> waitForCallbacksToComplete(int sleepTimeMillis) {
        while (!areAllCallbacksDone()) {
            sleepFor(sleepTimeMillis, "");
        }

        Set<Book> books = getBooks();
        AppLogger.logger.log(DEFAULT_LEVEL, "All the books collected size is: " + books.size());
        return books;
    }
}
