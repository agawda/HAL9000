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
public class MatrasScrapperTests {

    private static final String LINK = "http://www.matras.pl/wampir-z-zaglebia,p,243818";
    private static final String TITLE = "Wampir z Zagłębia";

    public void testTitleScrapping() {
        MatrasScrapper matrasScrapper = new MatrasScrapper();
        matrasScrapper.connect(LINK);
        Assert.assertEquals(matrasScrapper.parseSinglePage(LINK).getTitle(), TITLE);
    }

    public void testScrapeNoPages() {
        MatrasScrapper matrasScrapper = new MatrasScrapper();
        matrasScrapper.setPagesToScrap(0);
        Assert.assertEquals(matrasScrapper.scrape(), Collections.emptySet());
    }

    public void testScrapeSomePages() {
        MatrasScrapper matrasScrapper = new MatrasScrapper();
        matrasScrapper.setPagesToScrap(2);
        Assert.assertNotNull(matrasScrapper.scrape());
    }
}
