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
public class MatrasScrapperTests {

    private static final String LINK = "http://www.matras.pl/wampir-z-zaglebia,p,243818";

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidUrlScrapping() {
        MatrasScrapper matrasScrapper = new MatrasScrapper("");
        Assert.assertNotNull(matrasScrapper.scrape());
    }

    public void testTitleScrapping() {
        MatrasScrapper matrasScrapper = new MatrasScrapper();
        matrasScrapper.connect(LINK);
        Assert.assertEquals(matrasScrapper.parseSinglePage(LINK).getTitle(), "Wampir z Zagłębia");
    }
}
