package com.javaacademy.crawler.jsoup;

import com.javaacademy.crawler.common.model.BookModel;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collections;

/**
 * @author devas
 * @since 05.09.17
 */
@Test
public class BonitoScrapperTests {

    private static final String LINK = "https://bonito.pl/k-167304-podreczny-slownik-niemiecko-polski-a-z";
    private static final String TITLE = "Podręczny słownik niemiecko-polski A-Z";

    public void shouldScrapeTitle() {
        BonitoScrapper bonitoScrapper = new BonitoScrapper();
        bonitoScrapper.connect(LINK);
        Assert.assertEquals(bonitoScrapper.parseSinglePage(LINK).getTitle(), TITLE);
    }

    public void shouldScrapeNothing() {
        BonitoScrapper bonitoScrapper = new BonitoScrapper();
        bonitoScrapper.setPageEndIndex(0);
        Assert.assertEquals(bonitoScrapper.scrape(), Collections.emptySet());
    }

    public void shouldScrapeSomePages() {
        BonitoScrapper bonitoScrapper = new BonitoScrapper();
        bonitoScrapper.setPageEndIndex(1);
        bonitoScrapper.setShouldDataBeScrapped(false);
        Assert.assertNotNull(bonitoScrapper.scrape());
    }
}
