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
public class CzytamScrapperTests {

    private static final String LINK = "http://www.czytam.pl/k,ks_677631,Przewodnik-wedrowca-Sztuka-odczytywania-znakow-natury-Gooley-Tristan.html";

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidUrlScrapping() {
        CzytamScrapper czytamScrapper = new CzytamScrapper("");
        Assert.assertNotNull(czytamScrapper.scrape());
    }

    public void testTitleScrapping() {
        CzytamScrapper czytamScrapper = new CzytamScrapper();
        czytamScrapper.connect(LINK);
        Assert.assertEquals(czytamScrapper.parseSinglePage(LINK).getTitle(), "Przewodnik wędrowca. Sztuka odczytywania znaków natury");
    }
}
