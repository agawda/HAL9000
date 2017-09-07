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
    private boolean shouldDataBeScrapped = true;
    int pageStartIndex = 0;
    int pageEndIndex = 5;
    String scrapperName;
    String BASE_URL;
    String PROMOS_URL;

    static long parseIsbn(String s) {
        long isbn;
        try {
            isbn = Long.parseLong(s);
        } catch (NumberFormatException e) {
            return new Random().nextLong();
        }
        return isbn;
    }

    static double parsePrice(String s) {
        double price;
        try {
            price = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return 0;
        }
        return price;
    }

    void setPageEndIndex(int pageEndIndex) {
        this.pageEndIndex = pageEndIndex;
    }

    void setShouldDataBeScrapped(boolean shouldDataBeScrapped) {
        this.shouldDataBeScrapped = shouldDataBeScrapped;
    }

    void connect(String link) {
        jsoupConnector.connect(link);
    }

    Document getDoc() {
        return jsoupConnector.getDoc();
    }

    public abstract Set<BookModel> scrape();

    abstract Set<BookModel> parseSingleGrid();

    BookModel parseSinglePage(String link) {
        if (!shouldDataBeScrapped) return new BookModel();
        return new BookModel.Builder(
                getIndustryIdentifier(),
                getTitle(),
                getAuthors(),
                getCategories(),
                link,
                getSmallThumbnail(),
                getListPrice(),
                getRetailPrice()
        ).build();
    }

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
}
