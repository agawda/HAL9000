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
 * @since 06.09.17
 */
public class CzytamScrapper extends JsoupBookScrapper {

    public CzytamScrapper() {
        scrapperName = "Czytam";
        BASE_URL = "http://www.czytam.pl";
        PROMOS_URL = BASE_URL + "/ksiazki-promocje,";
    }

    @Override
    public Set<BookModel> scrape() {
        AppLogger.logger.log(DEFAULT_LEVEL, "Scrapping books from " + scrapperName);
        Set<BookModel> bookModels = new HashSet<>();
        for (int i = pageStartIndex; i < pageEndIndex; i++) {
            connect(PROMOS_URL + i + ".html");
            bookModels.addAll(parseSingleGrid());
        }
        return bookModels;
    }

    @Override
    Set<String> getLinksFromGrid() {
        Elements elements = getDoc().select("h3.product-title > a");
        Set<String> sublinks = new HashSet<>(elements.eachAttr("href"));
        return new HashSet<>(sublinks.stream().map(BASE_URL::concat).collect(Collectors.toSet()));
    }

    @Override
    Long getIndustryIdentifier() {
        Elements elements = getDoc().select("#panel4-2 > p > span:containsOwn(ISBN:)");
        if (elements.isEmpty()) {
            elements = getDoc().select("#panel4-2 > p > span:containsOwn(Kod paskowy:)");
        }
        return elements.isEmpty() ? new Random().nextLong() :
                parseIsbn(elements.next().text().replace("-", ""));
    }

    @Override
    String getTitle() {
        Elements elements = getDoc().select(".show-for-medium-up > h1");
        return elements.isEmpty() ? "" : elements.text();
    }

    @Override
    List<String> getAuthors() {
        Elements elements = getDoc().select("#panel4-2 > p > span:containsOwn(Autor:) + strong > a");
        return elements.isEmpty() ? Collections.singletonList("") : elements.eachText();
    }

    @Override
    List<String> getCategories() {
        Elements elements = getDoc().select(".small-16 > * .current");
        return elements.isEmpty() ? Collections.singletonList("") : elements.eachText().subList(0, elements.size() - 1);
    }

    @Override
    String getSmallThumbnail() {
        Elements elements = getDoc().select("#panel3-1 > a > img");
        return elements.isEmpty() ? "" : elements.attr("src");
    }

    @Override
    double getListPrice() {
        Elements elements = getDoc().select("div.oldPirce > strong");
        return elements.isEmpty() ? 0 :
                parsePrice(elements.first().text().replace(",", ".").replace(" PLN", ""));
    }

    @Override
    double getRetailPrice() {
        Elements elements = getDoc().select("div.price > strong");
        return elements.isEmpty() ? 0 :
                parsePrice(elements.first().text().replace(",", ".").replace(" PLN", ""));
    }
}