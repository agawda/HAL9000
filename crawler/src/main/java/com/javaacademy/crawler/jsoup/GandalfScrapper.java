package com.javaacademy.crawler.jsoup;

import com.javaacademy.crawler.common.logger.AppLogger;
import com.javaacademy.crawler.common.model.BookModel;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.stream.Collectors;

import static com.javaacademy.crawler.common.logger.AppLogger.DEFAULT_LEVEL;

/**
 * @author devas
 * @since 04.09.17
 */
public class GandalfScrapper extends JsoupBookScrapper {

    private static String GANDALF_BASE_URL = "http://www.gandalf.com.pl";
    private static String GANDALF_PROMOS_URL = GANDALF_BASE_URL + "/promocje/";
    private int pagesToScrap = 10;

    void setPagesToScrap(int pagesToScrap) {
        this.pagesToScrap = pagesToScrap;
    }

    @Override
    public Set<BookModel> scrape() {
        AppLogger.logger.log(DEFAULT_LEVEL, "Scrapping books from Gandalf");
        Set<BookModel> bookModels = new HashSet<>();
        for(int i = 0; i < pagesToScrap; i++) {
            connect(GANDALF_PROMOS_URL + i + "/");
            bookModels.addAll(parseSingleGrid());
        }
        return bookModels;
    }

    private Set<BookModel> parseSingleGrid() {
        Elements elements = getDoc().select("div.prod p.h2 > a");
        Set<String> sublinks = new HashSet<>(elements.eachAttr("href"));
        Set<String> links = new HashSet<>(sublinks.stream().map(GANDALF_BASE_URL::concat).collect(Collectors.toSet()));
        Set<BookModel> bookModels = new HashSet<>();
        for (String link : links) {
            connect(link);
            BookModel bookModel = parseSinglePage(link);
            bookModels.add(bookModel);
        }
        return bookModels;
    }

    @Override
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

    @Override
    Long getIndustryIdentifier() {
        Elements elements = getDoc().select("td[itemprop=isbn]");
        return elements.isEmpty() ? new Random().nextLong() :
                parseIsbn(elements.text().replace("-", ""));
    }

    @Override
    String getTitle() {
        Elements elements = getDoc().select(".gallthumb > img");
        return elements.isEmpty() ? "" : elements.attr("alt");
    }

    @Override
    List<String> getAuthors() {
        Elements elements = getDoc().select(".persons a");
        return elements.isEmpty() ? Collections.singletonList("") : elements.eachText();
    }

    @Override
    List<String> getCategories() {
        Elements elements = getDoc().select(".product_categories > a");
        return elements.isEmpty() ? Collections.singletonList("") : elements.eachText();
    }

    @Override
    String getSmallThumbnail() {
        Elements elements = getDoc().select(".gallthumb > img");
        return elements.isEmpty() ? "" : GANDALF_BASE_URL + elements.attr("src");
    }

    @Override
    double getListPrice() {
        Elements elements = getDoc().select(".old_price");
        return elements.isEmpty() ? 0 : parsePrice(elements.text().replaceAll(",", "."));
    }

    @Override
    double getRetailPrice() {
        Elements elements = getDoc().select(".new_price_big > span");
        return elements.isEmpty() ? 0 :
                parsePrice(elements.text().replaceAll("[a-ż]", "").replaceAll(",", "."));
    }

    @Override
    String getLink(Element element) {
        return GANDALF_BASE_URL + element.select("a").attr("href");
    }
}
