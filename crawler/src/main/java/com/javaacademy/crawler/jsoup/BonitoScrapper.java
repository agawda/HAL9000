package com.javaacademy.crawler.jsoup;

import com.javaacademy.crawler.common.interfaces.Book;
import com.javaacademy.crawler.common.logger.AppLogger;
import com.javaacademy.crawler.common.model.BookModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * @author devas
 * @since 29.08.17
 */
public class BonitoScrapper {

    private Document doc;
    private static final String BONITO_BASE_URL = "https://bonito.pl";
    private static final String BONITO_PROMOS_LINK = "https://bonito.pl/wyprzedaz";

    public Set<BookModel> scrapAndGetBookModels() {
        doc = connectAndGetDoc(BONITO_PROMOS_LINK);

        Elements elements = doc.getElementsByAttributeValueStarting("href", "/k").select("[title=Pokaż...]");
        Set<String> sublinks = new HashSet<>(elements.eachAttr("href"));

        Set<String> links = new HashSet<>(sublinks.stream().map(BONITO_BASE_URL::concat).collect(Collectors.toSet()));

        Set<BookModel> bookModels = new HashSet<>();
        links.forEach(link -> bookModels.add(parseLinkAndGetBookModel(link)));

        return bookModels;
    }

    private Document connectAndGetDoc(String link) {
        Document doc = null;
        try {
            doc = Jsoup.connect(link).get();
        } catch (IOException e) {
            AppLogger.logger.log(Level.SEVERE, "Exception connecting with Jsoup, ", e);
        }
        return doc;
    }

    private BookModel parseLinkAndGetBookModel(String link) {
        doc = connectAndGetDoc(link);

        BookModel bookModel = new BookModel();
        setIndustryIdentifier(bookModel);
        setTitle(bookModel);
        setSubtitle(bookModel);
        setAuthors(bookModel);
        setCategories(bookModel);
        setSmallThumbnail(bookModel);
        setCanonicalVolumeLink(bookModel, link);
        setSaleability(bookModel);
        setPrices(bookModel);

        return bookModel;
    }

    private void setIndustryIdentifier(BookModel bookModel) {
        Elements elements = doc.select("td:containsOwn(Kod paskowy (EAN):)");
        if (elements.size() == 0) {
            bookModel.setIndustryIdentifier(new Random().nextLong());
        } else {
            String identifier = elements.next().first().child(0).html();
            bookModel.setIndustryIdentifier(Long.parseLong(identifier.replace("-", "")));
        }
    }

    private void setTitle(BookModel bookModel) {
        Elements elements = doc.getElementsByAttributeValue("itemprop", "name");
        if (elements.size() == 0) {
            bookModel.setTitle("");
        } else {
            bookModel.setTitle(elements.first().html());
        }
    }

    private void setSubtitle(BookModel bookModel) {
        bookModel.setSubtitle("");
    }

    private void setAuthors(BookModel bookModel) {
        List<String> authorsList = new ArrayList<>();
        Elements elements = doc.select("td:containsOwn(Autor:\u00a0)");
        if (elements.size() == 0) {
            bookModel.setAuthors(Collections.singletonList(""));
        } else {
            Elements authors = elements.next().first().getElementsByTag("a");
            authors.forEach(element -> authorsList.add(element.html()));
            bookModel.setAuthors(authorsList);
        }
    }

    private void setCategories(BookModel bookModel) {
        Elements elements = doc.getElementsByAttributeValue("itemprop", "category");
        if (elements.size() == 0) {
            bookModel.setCategories(Collections.singletonList(""));
        } else {
            String categories = elements.first().attr("content");
            bookModel.setCategories(Arrays.asList(categories.substring(13).split(" > ")));
        }
    }

    private void setSmallThumbnail(BookModel bookModel) {
        Elements elements = doc.select("a:has(img)[title=Powiększ...]>img");
        if (elements.size() == 0) {
            bookModel.setSmallThumbnail("");
        } else {
            String imageLink = elements.first().attr("src");
            bookModel.setSmallThumbnail("https://".concat(imageLink.substring(2)));
        }
    }

    private void setCanonicalVolumeLink(BookModel bookModel, String link) {
        bookModel.setCanonicalVolumeLink(link);
    }

    private void setSaleability(BookModel bookModel) {
        bookModel.setSaleability("FOR_SALE");
    }

    private void setPrices(BookModel bookModel) {
        bookModel.setListPriceCurrencyCode("PLN");
        bookModel.setRetailPriceCurrencyCode("PLN");

        Elements prices = doc.select("b:contains(zł)");
        if (prices.size() == 0) {
            bookModel.setListPriceAmount(0);
            bookModel.setRetailPriceAmount(0);
        } else {
            bookModel.setListPriceAmount(Double.parseDouble(prices.get(0).html().split(" ")[0].replace(',', '.')));
            bookModel.setRetailPriceAmount(Double.parseDouble(prices.get(1).html().split(" ")[0].replace(',', '.')));
        }
    }
}
