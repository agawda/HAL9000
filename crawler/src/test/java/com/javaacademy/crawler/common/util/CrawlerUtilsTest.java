package com.javaacademy.crawler.common.util;

import org.testng.annotations.Test;

public class CrawlerUtilsTest {

    @Test
    public void testSleep() {
        CrawlerUtils.sleepFor(100, "");
    }

}