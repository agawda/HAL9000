package com.javaacademy.crawler.jsoup;

import com.javaacademy.crawler.common.logger.AppLogger;
import com.javaacademy.crawler.common.model.BookModel;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;

/**
 * @author devas
 * @since 04.09.17
 */
abstract class JsoupBookScrapper {

    private JsoupConnector jsoupConnector = new JsoupConnector();

    void connect(String link) {
        jsoupConnector.connect(link);
    }

    Document getDoc() {
        return jsoupConnector.getDoc();
    }

    public abstract Set<BookModel> scrape();

    abstract BookModel parseSinglePage(String link);

    abstract Long getIndustryIdentifier();

    abstract String getTitle();

    abstract List<String> getAuthors();

    abstract List<String> getCategories();

    abstract String getSmallThumbnail();

    @SuppressWarnings("Duplicates")
    abstract double getListPrice();

    @SuppressWarnings("Duplicates")
    abstract double getRetailPrice();

    abstract String getLink(Element element);

    static long parseIsbn(String s) {
        long isbn = 0;
        try {
            isbn = Long.parseLong(s);
        } catch (NumberFormatException e) {
            AppLogger.logger.log(Level.WARNING, "Exception while parsing ISBN, ", e);
            return new Random().nextLong();
        }
        return isbn;
    }

    static double parsePrice(String s) {
        double price = 0;
        try {
            price = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            AppLogger.logger.log(Level.WARNING, "Exception while parsing price, ", e);
            return 0;
        }
        return price;
    }
}
