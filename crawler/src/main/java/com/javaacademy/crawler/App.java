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
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static com.javaacademy.crawler.common.logger.AppLogger.DEFAULT_LEVEL;
import static com.javaacademy.crawler.common.logger.AppLogger.logger;
import static com.javaacademy.crawler.common.util.CrawlerUtils.sleepFor;

/**
 * @author devas
 * @author mprtcz
 * @since 24.08.17
 */
public class App {
    private static Map<String, Long> crawlersSendBooksStats = new HashMap<>();
    private static String serverIpAddress;

    public static void main(String[] args) {
        AppLogger.initializeLogger();
        serverIpAddress = loadIpAddress();
        if (serverIpAddress.equals("")) return;

        Set<BookModel> bonitoBooks = new BonitoScrapper().scrape();
        sendScrappedBooks(bonitoBooks, "Bonito");

        Set<BookModel> gandalfBooks = new GandalfScrapper().scrape();
        sendScrappedBooks(gandalfBooks, "Gandalf");

        Set<BookModel> matrasBooks = new MatrasScrapper().scrape();
        sendScrappedBooks(matrasBooks, "Matras");

        Set<BookModel> czytamBooks = new CzytamScrapper().scrape();
        sendScrappedBooks(czytamBooks, "Czytam");

        Set<Book> googleBooks = runGoogleScrapper();
        sendScrappedBooks(googleBooks, "Google", new GoogleBookConverter());
    }

    private static void sendScrappedBooks(Set<BookModel> scrappedBooks, String bookStoreName) {
        BookSender bookSender = new BookSender(scrappedBooks);
        sendBookSAndLogInfo(bookSender, bookStoreName);
    }

    private static void sendScrappedBooks(Set<Book> scrappedBooks, String bookStoreName,GoogleBookConverter googleBookConverter ) {
        BookSender bookSender = new BookSender(scrappedBooks, googleBookConverter);
        sendBookSAndLogInfo(bookSender, bookStoreName);
    }

    private static void sendBookSAndLogInfo(BookSender bookSender, String bookStoreName) {
        long numberOfSentBooks = bookSender.sendBooksTo(serverIpAddress, bookStoreName);
        crawlersSendBooksStats.put(bookStoreName, numberOfSentBooks);
    }

    private static Set<Book> runGoogleScrapper() {
        GoogleScrapper googleScrapper = new GoogleScrapper();
        googleScrapper.runScrapping();

        while (!googleScrapper.areAllCallbacksDone()) {
            sleepFor(6000, "");
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
