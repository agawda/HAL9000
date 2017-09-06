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
    private BookModel bookModel;

    @BeforeMethod
    public void setUp() throws Exception {
        CzytamScrapper czytamScrapper = new CzytamScrapper();
        czytamScrapper.connect(LINK);
        bookModel = czytamScrapper.parseSinglePage(LINK);
    }

    public void testIdentifier() {
        Assert.assertEquals(bookModel.getIndustryIdentifier(), new Long(9788375154450L));
    }

    public void testTitle() {
        Assert.assertEquals(bookModel.getTitle(), "Przewodnik wędrowca. Sztuka odczytywania znaków natury");
    }
}
