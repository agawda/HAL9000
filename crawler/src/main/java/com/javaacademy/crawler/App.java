package com.javaacademy.crawler;

import com.javaacademy.crawler.common.booksender.BookSender;
import com.javaacademy.crawler.common.interfaces.Book;
import com.javaacademy.crawler.common.logger.AppLogger;
import com.javaacademy.crawler.googlebooks.GoogleScrapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

import static com.javaacademy.crawler.common.logger.AppLogger.DEFAULT_LEVEL;
import static com.javaacademy.crawler.common.logger.AppLogger.logger;

/**
 * @author devas
 * @author mprtcz
 * @since 24.08.17
 */
public class App {

    public static void main(String[] args) {
        AppLogger.initializeLogger();
        String serverIpAddress = loadIpAddress();
        GoogleScrapper googleScrapper = new GoogleScrapper();
        googleScrapper.runScrapping();

        while (!googleScrapper.areAllCallbacksDone()) {
            try {
                AppLogger.logger.log(DEFAULT_LEVEL, "Callbacks not done, waiting...");
                Thread.sleep(6000);
                AppLogger.logger.log(DEFAULT_LEVEL, "googleScrapper.areAllCallbacksDone(): "
                        +googleScrapper.areAllCallbacksDone());

            } catch (InterruptedException e) {
                AppLogger.logger.log(Level.WARNING,"Exception while waiting", e);
                        Thread.currentThread().interrupt();
            }
        }

        Set<Book> books = googleScrapper.getBooks();
        AppLogger.logger.log(DEFAULT_LEVEL, "All the books collected size is: " + books.size());

        BookSender bookSender = new BookSender(books);
        bookSender.sendBooksTo(serverIpAddress);
    }

    private static String loadIpAddress() {
        Properties properties = new Properties();
        String filePath = "file:src/main/resources/ipaddress.properties";

        try (InputStream input = new FileInputStream(filePath)) {
            properties.load(input);
            return properties.getProperty("ip.address");
        } catch (IOException e) {
            logger.log(DEFAULT_LEVEL, "Ip address file not found");
        }
        return "";
    }
}
