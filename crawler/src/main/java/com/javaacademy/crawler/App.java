package com.javaacademy.crawler;

import com.javaacademy.crawler.common.booksender.BookSender;
import com.javaacademy.crawler.common.logger.AppLogger;
import com.javaacademy.crawler.common.model.BookModel;
import com.javaacademy.crawler.googlebooks.GoogleScrapper;
import com.javaacademy.crawler.jsoup.BonitoScrapper;
import com.javaacademy.crawler.jsoup.CzytamScrapper;
import com.javaacademy.crawler.jsoup.GandalfScrapper;
import com.javaacademy.crawler.jsoup.MatrasScrapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.javaacademy.crawler.common.logger.AppLogger.DEFAULT_LEVEL;
import static com.javaacademy.crawler.common.logger.AppLogger.logger;

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
        App app = new App();
        Set<Scrapper> scrappers = new HashSet<>();
        scrappers.add(new BonitoScrapper());
        scrappers.add(new GandalfScrapper());
        scrappers.add(new MatrasScrapper());
        scrappers.add(new CzytamScrapper());
        scrappers.add(new GoogleScrapper());
        app.runApp(scrappers);
    }

    void runApp(Set<Scrapper> scrappers) {
        for (Scrapper scrapper : scrappers) {
            Set<BookModel> books = scrapper.scrape();
            sendScrappedBooks(books, scrapper.getName());
        }
    }

    private void sendScrappedBooks(Set<BookModel> scrappedBooks, String bookStoreName) {
        BookSender bookSender = new BookSender(scrappedBooks);
        sendBookSAndLogInfo(bookSender, bookStoreName);
    }

    private void sendBookSAndLogInfo(BookSender bookSender, String bookStoreName) {
        long numberOfSentBooks = bookSender.sendBooksTo(serverIpAddress, bookStoreName);
        crawlersSendBooksStats.put(bookStoreName, numberOfSentBooks);
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
