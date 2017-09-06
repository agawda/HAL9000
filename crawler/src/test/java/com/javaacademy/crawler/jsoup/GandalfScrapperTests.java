package com.javaacademy.crawler.jsoup;

import com.javaacademy.crawler.common.model.BookModel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author devas
 * @since 05.09.17
 */
@Test
public class GandalfScrapperTests {

    private static final String LINK = "http://www.gandalf.com.pl/b/co-polske-stanowi/";

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidUrlScrapping() {
        GandalfScrapper gandalfScrapper = new GandalfScrapper("");
        Assert.assertNotNull(gandalfScrapper.scrape());
    }

    public void testTitleScrapping() {
        GandalfScrapper gandalfScrapper = new GandalfScrapper();
        gandalfScrapper.connect(LINK);
        Assert.assertEquals(gandalfScrapper.parseSinglePage(LINK).getTitle(), "Co Polskę stanowi Biografie historyczne");
    }
}
