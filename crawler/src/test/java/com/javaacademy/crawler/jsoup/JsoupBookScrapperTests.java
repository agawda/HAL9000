package com.javaacademy.crawler.jsoup;

import com.javaacademy.crawler.common.logger.AppLogger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.logging.Level;

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

    public void testParsingValidIsbn() {
        Assert.assertEquals(JsoupBookScrapper.parseIsbn("123456789"), 123456789L);
    }

    public void testParsingInvalidIsbn() {
        Assert.assertNotNull(JsoupBookScrapper.parseIsbn("123456789X"));
    }

    public void testParsingValidPrice() {
        Assert.assertEquals(JsoupBookScrapper.parsePrice("9.99"), 9.99);
    }

    public void testParsingInvalidPrice() {
        Assert.assertEquals(JsoupBookScrapper.parsePrice("9.99 PLN"), 0.0);
    }
}
