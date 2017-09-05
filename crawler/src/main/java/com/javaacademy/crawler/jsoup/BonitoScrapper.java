package com.javaacademy.crawler.jsoup;

import com.javaacademy.crawler.common.logger.AppLogger;
import com.javaacademy.crawler.common.model.BookModel;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * @author devas
 * @since 29.08.17
 */
public class BonitoScrapper extends JsoupBookScrapper {

    private static final String BONITO_BASE_URL = "https://bonito.pl";
    private static final String BONITO_PROMOS_LINK = "https://bonito.pl/wyprzedaz";

    public Set<BookModel> scrape() {
        connect(BONITO_PROMOS_LINK);

        Elements elements = getDoc().getElementsByAttributeValueStarting("href", "/k").select("[title=Pokaż...]");
        Set<String> sublinks = new HashSet<>(elements.eachAttr("href"));

        Set<String> links = new HashSet<>(sublinks.stream().map(BONITO_BASE_URL::concat).collect(Collectors.toSet()));

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
        Elements elements = getDoc().select("td:containsOwn(Kod paskowy (EAN):)");
        if (elements.size() == 0) {
            return new Random().nextLong();
        } else {
            String identifier = elements.next().first().child(0).html();
            return Long.parseLong(identifier.replace("-", ""));
        }
    }

    @Override
    String getTitle() {
        Elements elements = getDoc().getElementsByAttributeValue("itemprop", "name");
        if (elements.size() == 0) {
            return "";
        } else {
            return elements.first().html();
        }
    }

    @Override
    List<String> getAuthors() {
        Elements elements = getDoc().select("td:containsOwn(Autor:\u00a0)");
        if (elements.size() == 0) {
            return Collections.singletonList("");
        } else {
            Elements authors = elements.next().first().getElementsByTag("a");
            List<String> authorsList = new ArrayList<>();
            authors.forEach(element -> authorsList.add(element.html()));
            return authorsList;
        }
    }

    @Override
    List<String> getCategories() {
        Elements elements = getDoc().getElementsByAttributeValue("itemprop", "category");
        if (elements.size() == 0) {
            return Collections.singletonList("");
        } else {
            String categories = elements.first().attr("content");
            return Arrays.asList(categories.substring(13).split(" > "));
        }
    }

    @Override
    String getSmallThumbnail() {
        Elements elements = getDoc().select("a:has(img)[title=Powiększ...]>img");
        if (elements.size() == 0) {
            return "";
        } else {
            String imageLink = elements.first().attr("src");
            return "https://".concat(imageLink.substring(2));
        }
    }

    @Override
    double getListPrice() {
        Elements prices = getDoc().select("b:contains(zł)");
        if (prices.size() == 0) {
            return 0;
        } else {
            double price = 0;
            try {
                price = Double.parseDouble(prices.get(1).html().split(" ")[0].replace(',', '.'));
            } catch (NumberFormatException e) {
                AppLogger.logger.log(Level.WARNING, "Exception while parsing, ", e);
            }
            return price;
        }
    }

    @Override
    double getRetailPrice() {
        Elements prices = getDoc().select("b:contains(zł)");
        if (prices.size() == 0) {
            return 0;
        } else {
            double price = 0;
            try {
                price = Double.parseDouble(prices.get(0).html().split(" ")[0].replace(',', '.'));
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
