package com.javaacademy.crawler.jsoup;

import com.javaacademy.crawler.Scrapper;
import com.javaacademy.crawler.common.model.BookModel;
import org.jsoup.nodes.Document;

import java.util.*;

import static com.javaacademy.crawler.common.booksender.BookSender.displayProgress;

/**
 * @author devas
 * @since 04.09.17
 */
abstract class JsoupBookScrapper implements Scrapper {

    private JsoupConnector jsoupConnector = new JsoupConnector();
    private boolean shouldDataBeScrapped = true;
    int pageStartIndex = 0;
    int pageEndIndex = 5;
    String scrapperName;
    String baseUrl;
    String promosUrl;
    Set<BookModel> bookModels = new HashSet<>();

    @Override
    public String getName() {
        return scrapperName;
    }

    @Override
    public Set<BookModel> getScrappedBooks() {
        return bookModels;
    }

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

    abstract Set<String> getLinksFromGrid();

    Set<BookModel> parseSingleGrid() {
        Set<BookModel> bookModels = new HashSet<>();
        Set<String> linksFromGrRid = getLinksFromGrid();
        int i = 1;
        for (String link : linksFromGrRid) {
            connect(link);
            BookModel bookModel = parseSinglePage(link);
            bookModels.add(bookModel);
            displayProgress(i++, linksFromGrRid.size(), '░');
        }
        return bookModels;
    }

    BookModel parseSinglePage(String link) {
        if (!shouldDataBeScrapped) return new BookModel();
        BookModel bookModel = new BookModel.Builder(
                getIndustryIdentifier(),
                getTitle(),
                getAuthors(),
                getCategories(),
                link,
                getSmallThumbnail(),
                getListPrice(),
                getRetailPrice()
        ).build();
        List<String> strings = Arrays.asList(bookModel.getTitle().split("(\\. )|(! )", 2));
        Iterator<String> iterator = strings.iterator();
        if (iterator.hasNext()) {
            bookModel.setTitle(iterator.next());
        }
        if (iterator.hasNext()) {
            bookModel.setSubtitle(iterator.next());
        }
        return bookModel;
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
}
