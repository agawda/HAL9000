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

    public GandalfScrapper() {
        scrapperName = "Gandalf";
        BASE_URL = "http://www.gandalf.com.pl";
        PROMOS_URL = BASE_URL + "/promocje/";
    }

    @Override
    public Set<BookModel> scrape() {
        AppLogger.logger.log(DEFAULT_LEVEL, "Scrapping books from " + scrapperName);
        Set<BookModel> bookModels = new HashSet<>();
        for (int i = pageStartIndex; i < pageEndIndex; i++) {
            connect(PROMOS_URL + i + "/");
            bookModels.addAll(parseSingleGrid());
        }
        return bookModels;
    }

    @Override
    Set<String> getLinksFromGrid() {
        Elements elements = getDoc().select("div.prod p.h2 > a");
        Set<String> sublinks = new HashSet<>(elements.eachAttr("href"));
        return new HashSet<>(sublinks.stream().map(BASE_URL::concat).collect(Collectors.toSet()));
    }

    @Override
    Long getIndustryIdentifier() {
        Elements elements = getDoc().select("td[itemprop=isbn]");
        return elements.isEmpty() ? new Random().nextLong() :
                parseIsbn(elements.text().replace("-", "").replace("X", ""));
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
        return elements.isEmpty() ? "" : BASE_URL + elements.attr("src");
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
}
