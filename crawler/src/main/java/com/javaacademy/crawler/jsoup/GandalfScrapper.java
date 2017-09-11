package com.javaacademy.crawler.jsoup;

import com.javaacademy.crawler.common.logger.AppLogger;
import com.javaacademy.crawler.common.model.BookModel;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.stream.Collectors;

import static com.javaacademy.crawler.common.booksender.BookSender.displayProgress;
import static com.javaacademy.crawler.common.booksender.BookSender.printOnConsole;
import static com.javaacademy.crawler.common.logger.AppLogger.DEFAULT_LEVEL;
import static com.javaacademy.crawler.common.logger.AppLogger.logScrappingInfo;

/**
 * @author devas
 * @since 04.09.17
 */
public class GandalfScrapper extends JsoupBookScrapper {

    public GandalfScrapper() {
        scrapperName = "Gandalf";
        baseUrl = "http://www.gandalf.com.pl";
        promosUrl = baseUrl + "/promocje/";
    }

    @Override
    public Set<BookModel> scrape() {
        long scrapperStartTime = System.nanoTime();
        AppLogger.logger.log(DEFAULT_LEVEL, "Scrapping books from " + scrapperName);
        printOnConsole("Scrapping from Gandalf\n");
        Set<BookModel> bookModels = new HashSet<>();
        for (int i = pageStartIndex; i < pageEndIndex; i++) {
            connect(promosUrl + i + "/");
            bookModels.addAll(parseSingleGrid());
            displayProgress(i+1, pageEndIndex);
        }
        logScrappingInfo(scrapperName, scrapperStartTime, bookModels.size());
        return bookModels;
    }

    @Override
    Set<String> getLinksFromGrid() {
        Elements elements = getDoc().select("div.prod p.h2 > a");
        Set<String> sublinks = new HashSet<>(elements.eachAttr("href"));
        return new HashSet<>(sublinks.stream().map(baseUrl::concat).collect(Collectors.toSet()));
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
        return elements.isEmpty() ? "" : baseUrl + elements.attr("src");
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
