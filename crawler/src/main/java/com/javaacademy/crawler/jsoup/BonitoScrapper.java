package com.javaacademy.crawler.jsoup;

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
class BonitoScrapper {

    private Document connectAndGetDoc(String link) {
        Document doc = null;
        try {
            doc = Jsoup.connect(link).get();
        } catch (IOException e) {
            AppLogger.logger.log(Level.SEVERE, "Exception connecting with Jsoup, ", e);
        }
        return doc;
    }

    List<BookModel> scrapAndGetBookModels() {
        Document doc = connectAndGetDoc("https://bonito.pl/wyprzedaz");

        Elements elements = doc.getElementsByAttributeValueStarting("href", "/k").select("[title=Pokaż...]");
        System.out.println(elements.size());
        Set<String> sublinks = new HashSet<>(elements.eachAttr("href"));

        final String BASE_URL = "https://bonito.pl";
        Set<String> links = new HashSet<>(sublinks.stream().map(BASE_URL::concat).collect(Collectors.toSet()));

        List<BookModel> bookModels = new LinkedList<>();
        links.forEach(link -> bookModels.add(parseLinkAndGetBookModel(link)));
        bookModels.forEach(System.out::println);

        return bookModels;
    }

    private BookModel parseLinkAndGetBookModel(String link) {
        System.out.println(link);
        Document doc = connectAndGetDoc(link);

        BookModel bookModel = new BookModel();
        String identifier = doc.select("td:containsOwn(Numer ISBN:)").next().first().child(0).html();
        bookModel.setIndustryIdentifier(Long.parseLong(identifier.replace("-", "")));
        bookModel.setTitle(doc.getElementsByAttributeValue("itemprop", "name").first().html());
        bookModel.setSubtitle("");
        List<String> authorsList = new ArrayList<>();
        Elements authors = doc.select("td:containsOwn(Autor:\u00a0)").next().first().getElementsByTag("a");
        authors.forEach(element -> authorsList.add(element.html()));
        bookModel.setAuthors(authorsList);
        String categories = doc.getElementsByAttributeValue("itemprop", "category").first().attr("content");
        bookModel.setCategories(Arrays.asList(categories.substring(13).split(" > ")));
        String imageLink = doc.select("a:has(img)[title=Powiększ...]>img").first().attr("src");
        bookModel.setSmallThumbnail("https://".concat(imageLink.substring(2)));
        bookModel.setCanonicalVolumeLink(link);
        bookModel.setSaleability("FOR_SALE");
        Elements prices = doc.select("b:contains(zł)");
        bookModel.setListPriceAmount(Double.parseDouble(prices.get(0).html().split(" ")[0].replace(',', '.')));
        bookModel.setListPriceCurrencyCode("PLN");
        bookModel.setRetailPriceAmount(Double.parseDouble(prices.get(1).html().split(" ")[0].replace(',', '.')));
        bookModel.setRetailPriceCurrencyCode("PLN");

        return bookModel;
    }

}
