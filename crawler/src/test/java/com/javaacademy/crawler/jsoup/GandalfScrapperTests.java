package com.javaacademy.crawler.jsoup;

import com.javaacademy.crawler.common.model.BookModel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
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

    public void testTitleScrapping() {
        GandalfScrapper gandalfScrapper = new GandalfScrapper();
        gandalfScrapper.connect(LINK);
        Assert.assertEquals(gandalfScrapper.parseSinglePage(LINK).getTitle(), TITLE);
    }

    public void testScrapeNoPages() {
        GandalfScrapper gandalfScrapper = new GandalfScrapper();
        gandalfScrapper.setPagesToScrap(0);
        Assert.assertEquals(gandalfScrapper.scrape(), Collections.emptySet());
    }

    public void testScrapeSomePages() {
        GandalfScrapper gandalfScrapper = new GandalfScrapper();
        gandalfScrapper.setPagesToScrap(1);
        Assert.assertNotNull(gandalfScrapper.scrape());
    }
}
