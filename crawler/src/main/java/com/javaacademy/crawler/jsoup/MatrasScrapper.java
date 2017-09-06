package com.javaacademy.crawler.jsoup;

import com.javaacademy.crawler.common.logger.AppLogger;
import com.javaacademy.crawler.common.model.BookModel;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.logging.Level;

/**
 * @author devas
 * @since 05.09.17
 */
public class MatrasScrapper extends JsoupBookScrapper {

    private static final String MATRAS_URL = "http://www.matras.pl/ksiazki/promocje,k,53?p=";
    private static final int BOOKS_PER_PAGE = 20;

    @Override
    public Set<BookModel> scrape() {
        connect(MATRAS_URL + 0);
        int lastPageNumber = getNumberOfPages() - 1;
        System.out.println(lastPageNumber);
        Set<BookModel> bookModels = new HashSet<>();
        int pageIndex = 0;
        while (true) {
            if (pageIndex == lastPageNumber) {
                break;
            }
            connect(MATRAS_URL + pageIndex);
            getNumberOfPages();
            Elements elements = getDoc().select(".s-item-outer");
            int booksCounter = 0;
            for (Element element : elements) {
                if (booksCounter == BOOKS_PER_PAGE) {
                    break;
                }
                booksCounter++;
                String link = getLink(element);
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
        Elements elements = getDoc().select("label:containsOwn(EAN:)");
        if (elements.isEmpty()) {
            return new Random().nextLong();
        } else {
            return Long.parseLong(elements.next().text());
        }
    }

    @Override
    String getTitle() {
        Elements elements = getDoc().select("h1[itemprop=name]");
        if (elements.isEmpty()) {
            return "";
        } else {
            return elements.text();
        }
    }

    @Override
    List<String> getAuthors() {
        Elements elements = getDoc().select("label:containsOwn(Autor:)");
        if (elements.isEmpty()) {
            return Collections.singletonList("");
        } else {
            return Arrays.asList(elements.next().text().split(", "));
        }
    }

    @Override
    List<String> getCategories() {
        Elements elements = getDoc().select(".categories-product-inner-col");
        if (elements.isEmpty()) {
            return Collections.singletonList("");
        } else {
            return elements.select("span").select("a").select("span").eachText();
        }
    }

    @Override
    String getSmallThumbnail() {
        Elements elements = getDoc().select("img");
        if (elements.isEmpty()) {
            return "";
        } else {
            return elements.attr("data-original");
        }
    }

    @SuppressWarnings("Duplicates")
    @Override
    double getListPrice() {
        Elements elements = getDoc().select("div.old-price");
        if (elements.isEmpty()) {
            return 0;
        } else {
            double price = 0;
            try {
                price = Double.parseDouble(elements.text().replace("\u00a0zł", "").replace(",", "."));
            } catch (NumberFormatException e) {
                AppLogger.logger.log(Level.WARNING, "Exception while parsing, ", e);
            }
            return price;
        }
    }

    @SuppressWarnings("Duplicates")
    @Override
    double getRetailPrice() {
        Elements elements = getDoc().select("div.this-main-price");
        if (elements.isEmpty()) {
            return 0;
        } else {
            double price = 0;
            try {
                price = Double.parseDouble(elements.text().replace("\u00a0zł", "").replace(",", "."));
            } catch (NumberFormatException e) {
                AppLogger.logger.log(Level.WARNING, "Exception while parsing, ", e);
            }
            return price;
        }
    }

    @Override
    String getLink(Element element) {
        return element.select(".cover").select("a").attr("href");
    }

    private int getNumberOfPages() {
//        return Integer.parseInt(connection.select("ul.pagination-list > *").get(7).select("a").text());
        return 11;
    }
}
