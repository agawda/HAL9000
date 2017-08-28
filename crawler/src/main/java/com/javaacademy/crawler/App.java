package com.javaacademy.crawler;

import com.javaacademy.crawler.common.interfaces.Book;
import com.javaacademy.crawler.common.logger.AppLogger;
import com.javaacademy.crawler.googlebooks.GoogleScrapper;

import java.util.Set;
import java.util.logging.Level;

/**
 * @author devas
 * @author mprtcz
 * @since 24.08.17
 */
public class App {

    public static void main(String[] args) {
        AppLogger.initializeLogger();
        GoogleScrapper googleScrapper = new GoogleScrapper();
        googleScrapper.runScrapping();

        while (!googleScrapper.areAllCallbacksDone()) {
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                AppLogger.logger.log(Level.WARNING,"Exception while waiting", e);
                        Thread.currentThread().interrupt();
            }
        }

        Set<Book> books = googleScrapper.getBooks();
        System.out.println("All the books collected size is: " + books.size());
    }
}
