package com.javaacademy.crawler.jsoup;

import com.javaacademy.crawler.common.logger.AppLogger;
import com.javaacademy.crawler.common.model.BookModel;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.logging.Level;

import static com.javaacademy.crawler.common.logger.AppLogger.DEFAULT_LEVEL;

/**
 * @author devas
 * @since 04.09.17
 */
public class GandalfScrapper extends JsoupBookScrapper {

    private static String GANDALF_URL = "http://www.gandalf.com.pl/promocje/";
    private static final String ENDING_CONDITION = "Brak produktów wybranego dystrybutora";

    public GandalfScrapper() {
    }

    public GandalfScrapper(String link) {
        GANDALF_URL = link;
    }

    @Override
    public Set<BookModel> scrape() {
        AppLogger.logger.log(DEFAULT_LEVEL, "Scrapping books from Gandalf");
        Set<BookModel> bookModels = new HashSet<>();
        int pageIndex = 0;
        while (true) {
            connect(GANDALF_URL + pageIndex + "/");
            if (getDoc().select(".no_products").text().startsWith(ENDING_CONDITION)) {
                break;
            }
            Elements elements = getDoc().select(".prod");
            for (Element element : elements) {
                String link = getLink(element);
                connect(link);
                BookModel bookModel = parseSinglePage(link);
                bookModels.add(bookModel);
            }
            if (pageIndex > 1) break;
            pageIndex++;
        }
        return bookModels;
    }

    @Override
    BookModel parseSinglePage(String link) {
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
                Long.parseLong(elements.text().replace("-", ""));
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
        return elements.isEmpty() ? "" : "http://www.gandalf.com.pl" + elements.attr("src");
    }

    @Override
    double getListPrice() {
        Elements elements = getDoc().select(".old_price");
        if (elements.isEmpty()) {
            return 0;
        } else {
            double price = 0;
            try {
                price = Double.parseDouble(elements.text().replaceAll(",", "."));
            } catch (NumberFormatException e) {
                AppLogger.logger.log(Level.WARNING, "Exception while parsing, ", e);
            }
            return price;
        }
    }

    @Override
    double getRetailPrice() {
        Elements elements = getDoc().select(".new_price_big > span");
        if (elements.isEmpty()) {
            return 0;
        } else {
            double price = 0;
            try {
                price = Double.parseDouble(elements.text().replaceAll("[a-ż]", "").replaceAll(",", "."));
            } catch (NumberFormatException e) {
                AppLogger.logger.log(Level.WARNING, "Exception while parsing, ", e);
            }
            return price;
        }
    }

    @Override
    String getLink(Element element) {
        return "http://www.gandalf.com.pl" + element.select("a").attr("href");
    }
}
