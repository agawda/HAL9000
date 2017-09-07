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
 * @since 05.09.17
 */
public class MatrasScrapper extends JsoupBookScrapper {

    private static String MATRAS_BASE_URL = "http://www.matras.pl";
    private static String MATRAS_PROMOS_URL = MATRAS_BASE_URL + "/ksiazki/promocje,k,53?p=";
    private int pagesToScrap = 10;

    void setPagesToScrap(int pagesToScrap) {
        this.pagesToScrap = pagesToScrap;
    }

    @Override
    public Set<BookModel> scrape() {
        AppLogger.logger.log(DEFAULT_LEVEL, "Scrapping books from Matras");
        Set<BookModel> bookModels = new HashSet<>();
        for(int i = 1; i < pagesToScrap; i++) {
            connect(MATRAS_PROMOS_URL + i);
            bookModels.addAll(parseSingleGrid());
        }
        return bookModels;
    }

    private Set<BookModel> parseSingleGrid() {
        Elements elements = getDoc().select("div.row.row-items span.right-side a");
        Set<String> links = new HashSet<>(elements.eachAttr("href"));
        links.removeIf(s -> s.startsWith("http://www.matras.pl/szukaj/"));
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
        Elements elements = getDoc().select("label:containsOwn(EAN:)");
        return elements.isEmpty() ? new Random().nextLong() : parseIsbn(elements.next().text());
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
        Elements elements = getDoc().select("img");
        return elements.isEmpty() ? "" : elements.attr("data-original");
    }

    @SuppressWarnings("Duplicates")
    @Override
    double getListPrice() {
        Elements elements = getDoc().select("div.old-price");
        return elements.isEmpty() ? 0 :
                parsePrice(elements.text().replace("\u00a0zł", "").replace(",", "."));
    }

    @SuppressWarnings("Duplicates")
    @Override
    double getRetailPrice() {
        Elements elements = getDoc().select("div.this-main-price");
        return elements.isEmpty() ? 0 :
                parsePrice(elements.text().replace("\u00a0zł", "").replace(",", "."));
    }

    @Override
    String getLink(Element element) {
        return element.select(".cover").select("a").attr("href");
    }
}
