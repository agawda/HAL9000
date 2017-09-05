package com.javaacademy.crawler.jsoup;

import com.javaacademy.crawler.common.logger.AppLogger;
import com.javaacademy.crawler.common.model.BookModel;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;

/**
 * @author devas
 * @since 04.09.17
 */
public class GandalfScrapper extends JsoupScrapper {

    private static final String ENDING_CONDITION = "Brak produktów wybranego dystrybutora";
    private static final String GANDALF_URL = "http://www.gandalf.com.pl/promocje/";

    public Set<BookModel> scrape() {
        Set<BookModel> bookModels = new HashSet<>();
        int i = 0;
        while (true) {
            connectAndInitDocument(GANDALF_URL + i + "/");
            if (doc.select(".no_products").text().startsWith(ENDING_CONDITION)) {
                break;
            }
            Elements elements = doc.select(".prod");
            for (Element element : elements) {
                bookModels.add(parseLinkAndGetBookModel(getLink(element)));
            }
            if (i > 1) break;
            i++;
        }
        return bookModels;
    }

    BookModel parseLinkAndGetBookModel(String link) {
        connectAndInitDocument(link);

        BookModel bookModel = new BookModel();
        setIndustryIdentifier(bookModel);
        setTitle(bookModel);
        setSubtitle(bookModel);
        setAuthors(bookModel);
        setCategories(bookModel);
        setSmallThumbnail(bookModel);
        setCanonicalVolumeLink(bookModel, link);
        setSaleability(bookModel);
        setCurrencyCodes(bookModel);
        setListPrice(bookModel);
        setRetailPrice(bookModel);

        return bookModel;
    }

    private void setIndustryIdentifier(BookModel bookModel) {
        Elements elements = doc.select("td[itemprop=isbn]");
        if (elements.size() == 0) {
            bookModel.setIndustryIdentifier(new Random().nextLong());
        } else {
            bookModel.setIndustryIdentifier(Long.parseLong(elements.text().replace("-", "")));
        }
    }

    private void setTitle(BookModel bookModel) {
        Elements elements = doc.select(".gallthumb > img");
        if (elements.size() == 0) {
            bookModel.setTitle("");
        } else {
            bookModel.setTitle(elements.attr("alt"));
        }
    }

    private void setSubtitle(BookModel bookModel) {
        bookModel.setSubtitle("");
    }

    private void setAuthors(BookModel bookModel) {
        Elements elements = doc.select(".persons a");
        if (elements.size() == 0) {
            bookModel.setAuthors(Collections.singletonList(""));
        } else {
            bookModel.setAuthors(elements.eachText());
        }
    }

    private void setCategories(BookModel bookModel) {
        Elements elements = doc.select(".product_categories > a");
        if (elements.size() == 0) {
            bookModel.setCategories(Collections.singletonList(""));
        } else {
            bookModel.setCategories(elements.eachText());
        }
    }

    private void setSmallThumbnail(BookModel bookModel) {
        Elements elements = doc.select(".gallthumb > img");
        if (elements.size() == 0) {
            bookModel.setSmallThumbnail("");
        } else {
            bookModel.setSmallThumbnail("http://www.gandalf.com.pl" + elements.attr("src"));
        }
    }

    private void setCanonicalVolumeLink(BookModel bookModel, String link) {
        bookModel.setCanonicalVolumeLink(link);
    }

    private void setSaleability(BookModel bookModel) {
        bookModel.setSaleability("FOR_SALE");
    }

    private void setCurrencyCodes(BookModel bookModel) {
        bookModel.setListPriceCurrencyCode("PLN");
        bookModel.setRetailPriceCurrencyCode("PLN");
    }

    private void setListPrice(BookModel bookModel) {
        Elements elements = doc.select(".old_price");
        if (elements.size() == 0) {
            bookModel.setListPriceAmount(0);
        } else {
            try {
                bookModel.setListPriceAmount(Double.parseDouble(elements.text().replaceAll(",", ".")));
            } catch (NumberFormatException e) {
                AppLogger.logger.log(Level.WARNING, "Exception while parsing, ", e);
            }
        }
    }

    private void setRetailPrice(BookModel bookModel) {
        Elements elements = doc.select(".new_price_big > span");
        if (elements.size() == 0) {
            bookModel.setRetailPriceAmount(0);
        } else {
            try {
                bookModel.setRetailPriceAmount(Double.parseDouble(elements.text().replaceAll("[a-ż]", "").replaceAll(",", ".")));
            } catch (NumberFormatException e) {
                AppLogger.logger.log(Level.WARNING, "Exception while parsing, ", e);
            }
        }
    }

    private String getLink(Element element) {
        return "http://www.gandalf.com.pl" + element.select("a").attr("href");
    }
}
