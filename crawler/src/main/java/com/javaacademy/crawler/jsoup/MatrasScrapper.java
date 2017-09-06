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
 * @since 05.09.17
 */
public class MatrasScrapper extends JsoupScrapper {

    private static final String MATRAS_URL = "http://www.matras.pl/ksiazki/promocje,k,53?p=";
    private static final int BOOKS_PER_PAGE = 20;

    public Set<BookModel> scrape() {
        connectAndInitDocument(MATRAS_URL + 0);
        int lastPageNumber = getNumberOfPages() - 1;
        AppLogger.logger.log(DEFAULT_LEVEL, Integer.toString(lastPageNumber));
        Set<BookModel> bookModels = new HashSet<>();
        int i = 0;
        while (true) {
            if (i == lastPageNumber) {
                break;
            }
            connectAndInitDocument(MATRAS_URL + i);
            getNumberOfPages();
            Elements elements = doc.select(".s-item-outer");
            int booksCounter = 0;
            for (Element element : elements) {
                if (booksCounter == BOOKS_PER_PAGE) {
                    break;
                }
                booksCounter++;
                bookModels.add(parseLinkAndGetBookModel(getLink(element)));
            }
            if (i > 1) {
                break;
            }
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

        AppLogger.logger.log(DEFAULT_LEVEL, bookModel.toString());
        return bookModel;
    }

    private void setIndustryIdentifier(BookModel bookModel) {
        Elements elements = doc.select("label:containsOwn(EAN:)");
        if (elements.size() == 0) {
            bookModel.setIndustryIdentifier(new Random().nextLong());
        } else {
            bookModel.setIndustryIdentifier(Long.parseLong(elements.next().text()));
        }
    }

    private void setTitle(BookModel bookModel) {
        Elements elements = doc.select("h1[itemprop=name]");
        if (elements.size() == 0) {
            bookModel.setTitle("");
        } else {
            bookModel.setTitle(elements.text());
        }
    }

    private void setSubtitle(BookModel bookModel) {
        bookModel.setSubtitle("");
    }

    private void setAuthors(BookModel bookModel) {
        Elements elements = doc.select("label:containsOwn(Autor:)");
        if (elements.size() == 0) {
            bookModel.setAuthors(Collections.singletonList(""));
        } else {
            bookModel.setAuthors(Arrays.asList(elements.next().text().split(", ")));
        }
    }

    private void setCategories(BookModel bookModel) {
        Elements elements = doc.select(".categories-product-inner-col");
        if (elements.size() == 0) {
            bookModel.setCategories(Collections.singletonList(""));
        } else {
            bookModel.setCategories(elements.select("span").select("a").select("span").eachText());
        }
    }

    private void setSmallThumbnail(BookModel bookModel) {
        Elements elements = doc.select("img");
        if (elements.size() == 0) {
            bookModel.setSmallThumbnail("");
        } else {
            bookModel.setSmallThumbnail(elements.attr("data-original"));
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
        Elements elements = doc.select("div.old-price");
        if (elements.size() == 0) {
            bookModel.setListPriceAmount(0);
        } else {
            try {
                bookModel.setListPriceAmount(Double.parseDouble(elements.text().replace("\u00a0zł", "").replace(",", ".")));
            } catch (NumberFormatException e) {
                AppLogger.logger.log(Level.WARNING, "Exception while parsing, ", e);
            }
        }
    }

    private void setRetailPrice(BookModel bookModel) {
        Elements elements = doc.select("div.this-main-price");
        if (elements.size() == 0) {
            bookModel.setRetailPriceAmount(0);
        } else {
            try {
                bookModel.setRetailPriceAmount(Double.parseDouble(elements.text().replace("\u00a0zł", "").replace(",", ".")));
            } catch (NumberFormatException e) {
                AppLogger.logger.log(Level.WARNING, "Exception while parsing, ", e);
            }
        }
    }

    private String getLink(Element element) {
        return element.select(".cover").select("a").attr("href");
    }

    private int getNumberOfPages() {
        return 11;
    }
}
