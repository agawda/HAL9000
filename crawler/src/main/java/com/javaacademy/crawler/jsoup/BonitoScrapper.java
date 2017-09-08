package com.javaacademy.crawler.jsoup;

import com.javaacademy.crawler.common.logger.AppLogger;
import com.javaacademy.crawler.common.model.BookModel;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.stream.Collectors;

import static com.javaacademy.crawler.common.booksender.BookSender.displayProgress;
import static com.javaacademy.crawler.common.booksender.BookSender.printOnConsole;
import static com.javaacademy.crawler.common.logger.AppLogger.DEFAULT_LEVEL;
import static com.javaacademy.crawler.common.logger.AppLogger.logScrappingInfo;

/**
 * @author devas
 * @since 29.08.17
 */
public class BonitoScrapper extends JsoupBookScrapper {

    public BonitoScrapper() {
        scrapperName = "Bonito";
        BASE_URL = "https://bonito.pl";
        PROMOS_URL = BASE_URL + "/wyprzedaz";
    }

    BonitoScrapper(String link) {
        scrapperName = "Bonito";
        BASE_URL = "https://bonito.pl";
        PROMOS_URL = BASE_URL + "/wyprzedaz";
        BASE_URL = link;
    }

    @Override
    public Set<BookModel> scrape() {
        long scrapperStartTime = System.nanoTime();
        AppLogger.logger.log(DEFAULT_LEVEL, "Scrapping books from " + scrapperName);
        printOnConsole("Scrapping from Bonito\n");
        connect(PROMOS_URL);

        Elements elements = getDoc().getElementsByAttributeValueStarting("href", "/k").select("[title=Pokaż...]");
        Set<String> sublinks = new HashSet<>(elements.eachAttr("href"));

        Set<String> links = new HashSet<>(sublinks.stream().map(BASE_URL::concat).collect(Collectors.toSet()));

        Set<BookModel> bookModels = new HashSet<>();
        int index = 0;
        for (String link : links) {
            connect(link);
            BookModel bookModel = parseSinglePage(link);
            bookModels.add(bookModel);
            index++;
            displayProgress(index, links.size());
        }
        logScrappingInfo(scrapperName, scrapperStartTime, bookModels.size());
        return bookModels;
    }

    @Override
    public String getName() {
        return "Bonito";
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
