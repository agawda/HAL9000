package com.javaacademy.crawler.jsoup;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author devas
 * @since 05.09.17
 */
@Test
public class JsoupScrapperTests {

    private static final String GANDALF_URL = "http://www.gandalf.com.pl";

    public void testValidLink() {
        JsoupScrapper jsoupScrapper = new JsoupScrapper();
        jsoupScrapper.connectAndInitDocument(GANDALF_URL);
        Assert.assertNotNull(jsoupScrapper.doc);
    }
}
