package com.javaacademy.crawler.jsoup;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author devas
 * @since 05.09.17
 */
@Test
public class JsoupBookScrapperTests {

    private static final String GANDALF_URL = "http://www.gandalf.com.pl";

    public void testValidLink() {
        JsoupConnector jsoupConnector = new JsoupConnector();
        jsoupConnector.connect(GANDALF_URL);
        Assert.assertNotNull(jsoupConnector.getDoc());
    }

    public void shouldParseValidIsbn() {
        Assert.assertEquals(JsoupBookScrapper.parseIsbn("123456789"), 123456789L);
    }

    public void shouldParseInvalidIsbn() {
        Assert.assertNotNull(JsoupBookScrapper.parseIsbn("123456789X"));
    }

    public void shouldParseValidPrice() {
        Assert.assertEquals(JsoupBookScrapper.parsePrice("9.99"), 9.99);
    }

    public void shouldParseInvalidPrice() {
        Assert.assertEquals(JsoupBookScrapper.parsePrice("9.99 PLN"), 0.0);
    }
}
