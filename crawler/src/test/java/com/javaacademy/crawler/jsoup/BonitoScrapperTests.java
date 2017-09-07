package com.javaacademy.crawler.jsoup;

import com.javaacademy.crawler.common.model.BookModel;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author devas
 * @since 05.09.17
 */
@Test
public class BonitoScrapperTests {

    private static final String LINK = "https://bonito.pl/k-167304-podreczny-slownik-niemiecko-polski-a-z";

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidUrlScrapping() {
        BonitoScrapper bonitoScrapper = new BonitoScrapper("");
        Assert.assertNotNull(bonitoScrapper.scrape());
    }

    public void shouldScrapeTitle() {
        BonitoScrapper bonitoScrapper = new BonitoScrapper();
        bonitoScrapper.connect(LINK);
        Assert.assertEquals(bonitoScrapper.parseSinglePage(LINK).getTitle(), "Podręczny słownik niemiecko-polski A-Z");
    }
}
