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
public class BonitoScrapperTests {

    private static final String LINK = "https://bonito.pl/k-167304-podreczny-slownik-niemiecko-polski-a-z";
    private BookModel bookModel;

    @BeforeMethod
    public void setUp() throws Exception {
        BonitoScrapper bonitoScrapper = new BonitoScrapper();
        bookModel = bonitoScrapper.parseLinkAndGetBookModel(LINK);
    }

    public void testIdentifier() {
        Assert.assertEquals(bookModel.getIndustryIdentifier(), new Long(9788321412955L));
    }

    public void testTitle() {
        Assert.assertEquals(bookModel.getTitle(), "Podręczny słownik niemiecko-polski A-Z");
    }
}
