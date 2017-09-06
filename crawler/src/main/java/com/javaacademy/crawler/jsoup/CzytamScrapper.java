package com.javaacademy.crawler.jsoup;

import com.javaacademy.crawler.common.logger.AppLogger;
import com.javaacademy.crawler.common.model.BookModel;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.logging.Level;

/**
 * @author devas
 * @since 06.09.17
 */
public class CzytamScrapper extends JsoupBookScrapper {

    private static final String CZYTAM_URL = "http://www.czytam.pl/ksiazki-promocje,";

    @Override
    public Set<BookModel> scrape() {
        Set<BookModel> bookModels = new HashSet<>();
        int pageIndex = 0;
        while (true) {
            connect(CZYTAM_URL + pageIndex + ".html");
            Elements elements = getDoc().select(".product");
            if (elements.isEmpty()) {
                break;
            }
            for (Element element : elements) {
                String link = "http://www.czytam.pl" + element.select("a").attr("href").
                        replace("\n","").replace("\t","");;
                connect(link);
                BookModel bookModel = parseSinglePage(link);
                bookModels.add(bookModel);
            }
            if (pageIndex > 1) {
                break;
            }
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
        Elements elements = getDoc().select("#panel4-2 > p > span:containsOwn(ISBN:)");
        if (elements.isEmpty()) {
            return new Random().nextLong();
        } else {
            return Long.parseLong(elements.next().text().replace("-", ""));
        }
    }

    @Override
    String getTitle() {
        Elements elements = getDoc().select(".show-for-medium-up > h1");
        if (elements.isEmpty()) {
            return "";
        } else {
            return elements.text();
        }
    }

    @Override
    List<String> getAuthors() {
        Elements elements = getDoc().select("#panel4-2 > p > span:containsOwn(Autor:) + strong > a");
        if (elements.isEmpty()) {
            return Collections.singletonList("");
        } else {
            return elements.eachText();
        }
    }

    @Override
    List<String> getCategories() {
        Elements elements = getDoc().select(".small-16 > * .current");
        if (elements.isEmpty()) {
            return Collections.singletonList("");
        } else {
            return elements.eachText().subList(0, elements.size() - 1);
        }
    }

    @Override
    String getSmallThumbnail() {
        Elements elements = getDoc().select("#panel3-1 > a > img");
        if (elements.isEmpty()) {
            return "";
        } else {
            return elements.attr("src");
        }
    }

    @Override
    double getListPrice() {
        Elements elements = getDoc().select("div.oldPirce > strong");
        if (elements.isEmpty()) {
            return 0;
        } else {
            double price = 0;
            try {
                price = Double.parseDouble(elements.first().text().
                        replace(",", ".").replace(" PLN",""));
            } catch (NumberFormatException e) {
                AppLogger.logger.log(Level.WARNING, "Exception while parsing, ", e);
            }
            return price;
        }
    }

    @Override
    double getRetailPrice() {
        Elements elements = getDoc().select("div.price > strong");
        if (elements.isEmpty()) {
            return 0;
        } else {
            double price = 0;
            try {
                price = Double.parseDouble(elements.first().text().
                        replace(",", ".").replace(" PLN",""));
            } catch (NumberFormatException e) {
                AppLogger.logger.log(Level.WARNING, "Exception while parsing, ", e);
            }
            return price;
        }
    }

    @Override
    String getLink(Element element) {
        return null;
    }
}