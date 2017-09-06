package com.javaacademy.crawler;

import com.javaacademy.crawler.common.booksender.BookSender;
import com.javaacademy.crawler.common.converters.GoogleBookConverter;
import com.javaacademy.crawler.common.interfaces.Book;
import com.javaacademy.crawler.common.logger.AppLogger;
import com.javaacademy.crawler.common.model.BookModel;
import com.javaacademy.crawler.googlebooks.GoogleScrapper;
import com.javaacademy.crawler.jsoup.BonitoScrapper;
import com.javaacademy.crawler.jsoup.CzytamScrapper;
import com.javaacademy.crawler.jsoup.GandalfScrapper;
import com.javaacademy.crawler.jsoup.MatrasScrapper;

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
        if (serverIpAddress.equals("")) return;

        Set<BookModel> bonitoBooks = new BonitoScrapper().scrape();
        BookSender bonitoBookSender = new BookSender(bonitoBooks);
        bonitoBookSender.sendBooksTo(serverIpAddress, "Bonito");

        Set<BookModel> gandalfBooks = new GandalfScrapper().scrape();
        BookSender gandalfBooksSender = new BookSender(gandalfBooks);
        gandalfBooksSender.sendBooksTo(serverIpAddress, "Gandalf");

        Set<BookModel> matrasBooks = new MatrasScrapper().scrape();
        BookSender matrasBooksSender = new BookSender(matrasBooks);
        matrasBooksSender.sendBooksTo(serverIpAddress, "Matras");

        Set<BookModel> matrasBooks = new MatrasScrapper().scrape();
        BookSender matrasBooksSender = new BookSender(matrasBooks);
        matrasBooksSender.sendBooksTo(serverIpAddress);

        Set<BookModel> czytamBooks = new CzytamScrapper().scrape();
        BookSender czytamBooksSender = new BookSender(czytamBooks);
        czytamBooksSender.sendBooksTo(serverIpAddress);

        Set<Book> googleBooks = runGoogleScrapper();
        BookSender googleBookSender = new BookSender(googleBooks, new GoogleBookConverter());
        googleBookSender.sendBooksTo(serverIpAddress, "Google");
    }

    private static Set<Book> runGoogleScrapper() {
        GoogleScrapper googleScrapper = new GoogleScrapper();
        googleScrapper.runScrapping();

        while (!googleScrapper.areAllCallbacksDone()) {
            try {
                AppLogger.logger.log(DEFAULT_LEVEL, "Callbacks not done, waiting...");
                Thread.sleep(6000);
                AppLogger.logger.log(DEFAULT_LEVEL, "googleScrapper.areAllCallbacksDone(): "
                        + googleScrapper.areAllCallbacksDone());

            } catch (InterruptedException e) {
                AppLogger.logger.log(Level.WARNING, "Exception while waiting", e);
                Thread.currentThread().interrupt();
            }
        }

        Set<Book> books = googleScrapper.getBooks();
        AppLogger.logger.log(DEFAULT_LEVEL, "All the books collected size is: " + books.size());
        return books;
    }

    private static String loadIpAddress() {
        Properties properties = new Properties();
        String filePath = "ipaddress.properties";
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        try (InputStream input = classloader.getResourceAsStream(filePath)) {
            properties.load(input);
            return properties.getProperty("ip.address");
        } catch (IOException e) {
            logger.log(DEFAULT_LEVEL, "Ip address file not found");
        }
        return "";
    }
}
