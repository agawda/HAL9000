package com.javaacademy.crawler.jsoup;

import com.javaacademy.crawler.common.logger.AppLogger;
import com.javaacademy.crawler.common.model.BookModel;
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
        pageEndIndex = 1;
    }

    @Override
    public Set<BookModel> scrape() {
        long scrapperStartTime = System.nanoTime();
        AppLogger.logger.log(DEFAULT_LEVEL, "Scrapping books from " + scrapperName);
        printOnConsole("Scrapping from Bonito\n");
        Set<BookModel> bookModels = new HashSet<>();
        for (int i = pageStartIndex; i < pageEndIndex; i++) {
            connect(PROMOS_URL);
            bookModels.addAll(parseSingleGrid());
            displayProgress(i, pageEndIndex);
        }
        logScrappingInfo(scrapperName, scrapperStartTime, bookModels.size());
        return bookModels;
    }

    @Override
    public String getName() {
        return "Bonito";
    }

    @Override
    Set<String> getLinksFromGrid() {
        Elements elements = getDoc().getElementsByAttributeValueStarting("href", "/k").select("[title=Pokaż...]");
        Set<String> sublinks = new HashSet<>(elements.eachAttr("href"));
        return new HashSet<>(sublinks.stream().map(BASE_URL::concat).collect(Collectors.toSet()));
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
}
