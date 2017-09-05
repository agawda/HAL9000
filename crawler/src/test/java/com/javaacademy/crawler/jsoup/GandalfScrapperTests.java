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
    private BookModel bookModel;

    @BeforeMethod
    public void setUp() throws Exception {
        GandalfScrapper gandalfScrapper = new GandalfScrapper();
        bookModel = gandalfScrapper.parseLinkAndGetBookModel(LINK);
    }

    public void testIdentifier() {
        Assert.assertEquals(bookModel.getIndustryIdentifier(), new Long(9788378648338L));
    }

    public void testTitle() {
        Assert.assertEquals(bookModel.getTitle(), "Co Polskę stanowi Biografie historyczne");
    }

    public void testSubtitle() {
        Assert.assertEquals(bookModel.getSubtitle(), "");
    }
}
