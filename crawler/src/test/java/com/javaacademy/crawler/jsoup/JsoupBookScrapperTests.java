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
}
