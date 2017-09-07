package com.javaacademy.crawler.jsoup;

import com.javaacademy.crawler.common.model.BookModel;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.Set;

/**
 * @author devas
 * @since 05.09.17
 */
@Test
public class CzytamScrapperTests {

    private static final String LINK = "http://www.czytam.pl/k,ks_677631,Przewodnik-wedrowca-Sztuka-odczytywania-znakow-natury-Gooley-Tristan.html";
    private static final String TITLE = "Przewodnik wędrowca. Sztuka odczytywania znaków natury";

    public void testTitleScrapping() {
        CzytamScrapper czytamScrapper = new CzytamScrapper();
        czytamScrapper.connect(LINK);
        Assert.assertEquals(czytamScrapper.parseSinglePage(LINK).getTitle(), TITLE);
    }

    public void testScrapeNoPages() {
        CzytamScrapper czytamScrapper = new CzytamScrapper();
        czytamScrapper.setPagesToScrap(0);
        Assert.assertEquals(czytamScrapper.scrape(), Collections.emptySet());
    }

    public void testScrapeSomePages() {
        CzytamScrapper czytamScrapper = new CzytamScrapper();
        czytamScrapper.setPagesToScrap(1);
        czytamScrapper.setShouldDataBeScrapped(false);
        Assert.assertNotNull(czytamScrapper.scrape());
    }
}
