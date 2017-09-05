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
    private BookModel bookModel;

    @BeforeMethod
    public void setUp() throws Exception {
        MatrasScrapper matrasScrapper = new MatrasScrapper();
        bookModel = matrasScrapper.parseLinkAndGetBookModel(LINK);
    }

    public void testIdentifier() {
        Assert.assertEquals(bookModel.getIndustryIdentifier(), new Long(9788324040957L));
    }

    public void testTitle() {
        Assert.assertEquals(bookModel.getTitle(), "Wampir z Zagłębia");
    }
}
