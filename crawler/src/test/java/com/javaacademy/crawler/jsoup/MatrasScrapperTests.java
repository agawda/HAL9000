package com.javaacademy.crawler.jsoup;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collections;

/**
 * @author devas
 * @since 05.09.17
 */
@Test
public class MatrasScrapperTests {

    private static final String LINK = "http://www.matras.pl/wampir-z-zaglebia,p,243818";
    private static final String TITLE = "Wampir z Zagłębia";

    public void shouldScrapeTitle() {
        MatrasScrapper matrasScrapper = new MatrasScrapper();
        matrasScrapper.connect(LINK);
        Assert.assertEquals(matrasScrapper.parseSinglePage(LINK).getTitle(), TITLE);
    }

    public void shouldScrapeNothing() {
        MatrasScrapper matrasScrapper = new MatrasScrapper();
        matrasScrapper.setPageEndIndex(0);
        Assert.assertEquals(matrasScrapper.scrape(), Collections.emptySet());
    }

    public void shouldScrapeSomePages() {
        MatrasScrapper matrasScrapper = new MatrasScrapper();
        matrasScrapper.setPageEndIndex(2);
        matrasScrapper.setShouldDataBeScrapped(false);
        Assert.assertNotNull(matrasScrapper.scrape());
    }
}
