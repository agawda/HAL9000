package com.javaacademy.crawler.jsoup;

import com.javaacademy.crawler.common.logger.AppLogger;
import com.javaacademy.crawler.common.model.BookModel;
import org.jsoup.select.Elements;

import java.util.*;

import static com.javaacademy.crawler.common.booksender.BookSender.displayProgress;
import static com.javaacademy.crawler.common.booksender.BookSender.printOnConsole;
import static com.javaacademy.crawler.common.logger.AppLogger.DEFAULT_LEVEL;
import static com.javaacademy.crawler.common.logger.AppLogger.logScrappingInfo;

/**
 * @author devas
 * @since 05.09.17
 */
public class MatrasScrapper extends JsoupBookScrapper {

    /**
     * It starts from 1 to avoid duplicates, because 0 and 1 is the same.
     */
    public MatrasScrapper() {
        scrapperName = "Matras";
        baseUrl = "http://www.matras.pl";
        promosUrl = baseUrl + "/ksiazki/promocje,k,53?p=";
        pageStartIndex = 1;
    }

    @Override
    public Set<BookModel> scrape() {
        long scrapperStartTime = System.nanoTime();
        AppLogger.logger.log(DEFAULT_LEVEL, "Scrapping books from " + scrapperName);
        printOnConsole("Scrapping from Matras\n");
        for (int i = pageStartIndex; i < pageEndIndex; i++) {
            connect(promosUrl + i);
            bookModels.addAll(parseSingleGrid());
            displayProgress(i+1, pageEndIndex);
        }
        logScrappingInfo(scrapperName, scrapperStartTime, bookModels.size());
        return bookModels;
    }

    @Override
    Set<String> getLinksFromGrid() {
        Elements elements = getDoc().select("div.row.row-items span.right-side a");
        Set<String> links = new HashSet<>(elements.eachAttr("href"));
        links.removeIf(s -> s.startsWith(baseUrl + "/szukaj/"));
        return links;
    }

    @Override
    Long getIndustryIdentifier() {
        Elements elements = getDoc().select("label:containsOwn(ISBN:)");
        if (elements.isEmpty()) {
            return new Random().nextLong();
        } else {
            String[] split = elements.next().text().split(", ");
            return parseIsbn(split[split.length - 1].replace("-", ""));
        }
    }

    @Override
    String getTitle() {
        Elements elements = getDoc().select("h1[itemprop=name]");
        return elements.isEmpty() ? "" : elements.text();
    }

    @Override
    List<String> getAuthors() {
        Elements elements = getDoc().select("label:containsOwn(Autor:)");
        return elements.isEmpty() ? Collections.singletonList("") :
                Arrays.asList(elements.next().text().split(", "));
    }

    @Override
    List<String> getCategories() {
        Elements elements = getDoc().select(".categories-product-inner-col");
        return elements.isEmpty() ? Collections.singletonList("") :
                elements.select("span").select("a").select("span").eachText();
    }

    @Override
    String getSmallThumbnail() {
        Elements elements = getDoc().select(".main-cover-inner-col img");
        return elements.isEmpty() ? "" : elements.attr("src");
    }

    @Override
    double getListPrice() {
        Elements elements = getDoc().select("div.old-price");
        return elements.isEmpty() ? 0 :
                parsePrice(elements.text().replace("\u00a0zł", "").replace(",", "."));
    }

    @Override
    double getRetailPrice() {
        Elements elements = getDoc().select("div.this-main-price");
        return elements.isEmpty() ? 0 : parsePrice(elements.attr("content"));
    }
}
