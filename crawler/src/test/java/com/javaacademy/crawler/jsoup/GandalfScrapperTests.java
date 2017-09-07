package com.javaacademy.crawler.jsoup;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collections;

/**
 * @author devas
 * @since 05.09.17
 */
@Test
public class GandalfScrapperTests {

    private static final String LINK = "http://www.gandalf.com.pl/b/co-polske-stanowi/";
    private static final String TITLE = "Co Polskę stanowi Biografie historyczne";

    public void shouldScrapeTitle() {
        GandalfScrapper gandalfScrapper = new GandalfScrapper();
        gandalfScrapper.connect(LINK);
        Assert.assertEquals(gandalfScrapper.parseSinglePage(LINK).getTitle(), TITLE);
    }

    public void shouldScrapeNothing() {
        GandalfScrapper gandalfScrapper = new GandalfScrapper();
        gandalfScrapper.setPageEndIndex(0);
        Assert.assertEquals(gandalfScrapper.scrape(), Collections.emptySet());
    }

    public void shouldScrapeSomePages() {
        GandalfScrapper gandalfScrapper = new GandalfScrapper();
        gandalfScrapper.setPageEndIndex(1);
        gandalfScrapper.setShouldDataBeScrapped(false);
        Assert.assertNotNull(gandalfScrapper.scrape());
    }
}
