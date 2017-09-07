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
 * @since 29.08.17
 */
public class BonitoScrapper extends JsoupBookScrapper {

    private static String BONITO_BASE_URL = "https://bonito.pl";
    private static final String BONITO_PROMOS_URL = "https://bonito.pl/wyprzedaz";

    public BonitoScrapper() {
    }

    BonitoScrapper(String link) {
        BONITO_BASE_URL = link;
    }

    @Override
    public Set<BookModel> scrape() {
        AppLogger.logger.log(DEFAULT_LEVEL, "Scrapping books from Gandalf");
        connect(BONITO_PROMOS_URL);

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
        return elements.isEmpty() ? new Random().nextLong() :
                parseIsbn(elements.next().first().child(0).html().replace("-", ""));
    }

    @Override
    String getTitle() {
        Elements elements = getDoc().getElementsByAttributeValue("itemprop", "name");
        return elements.isEmpty() ? "" : elements.first().html();
    }

    @Override
    List<String> getAuthors() {
        Elements elements = getDoc().select("td:containsOwn(Autor:\u00a0)");
        if (elements.isEmpty()) {
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
        return elements.isEmpty() ? Collections.singletonList("") :
                Arrays.asList(elements.first().attr("content").substring(13).split(" > "));
    }

    @Override
    String getSmallThumbnail() {
        Elements elements = getDoc().select("a:has(img)[title=Powiększ...]>img");
        return elements.isEmpty() ? "" : "https://".concat(elements.first().attr("src").substring(2));
    }

    @Override
    double getListPrice() {
        Elements prices = getDoc().select("b:contains(zł)");
        return prices.isEmpty() ? 0 : parsePrice(prices.get(1).html().split(" ")[0].replace(',', '.'));
    }

    @Override
    double getRetailPrice() {
        Elements prices = getDoc().select("b:contains(zł)");
        return prices.isEmpty() ? 0 : parsePrice(prices.get(0).html().split(" ")[0].replace(',', '.'));
    }

    @Override
    String getLink(Element element) {
        return null;
    }
}
