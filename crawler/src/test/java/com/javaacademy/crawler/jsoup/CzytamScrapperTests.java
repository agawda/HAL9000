package com.javaacademy.crawler.jsoup;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collections;

/**
 * @author devas
 * @since 05.09.17
 */
@Test
public class CzytamScrapperTests {

    private static final String LINK = "http://www.czytam.pl/k,ks_677631,Przewodnik-wedrowca-Sztuka-odczytywania-znakow-natury-Gooley-Tristan.html";
    private static final String TITLE = "Przewodnik wędrowca. Sztuka odczytywania znaków natury";

    public void shouldScrapeTitle() {
        CzytamScrapper czytamScrapper = new CzytamScrapper();
        czytamScrapper.connect(LINK);
        Assert.assertEquals(czytamScrapper.parseSinglePage(LINK).getTitle(), TITLE);
    }

    public void shouldScrapeNothing() {
        CzytamScrapper czytamScrapper = new CzytamScrapper();
        czytamScrapper.setPageEndIndex(0);
        Assert.assertEquals(czytamScrapper.scrape(), Collections.emptySet());
    }

    public void shouldScrapeSomePages() {
        CzytamScrapper czytamScrapper = new CzytamScrapper();
        czytamScrapper.setPageEndIndex(1);
        czytamScrapper.setShouldDataBeScrapped(false);
        Assert.assertNotNull(czytamScrapper.scrape());
    }
}
