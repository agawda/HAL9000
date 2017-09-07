package com.javaacademy.crawler.common.util;

import com.javaacademy.crawler.common.logger.AppLogger;

import java.util.logging.Level;

public class CrawlerUtils {

    public static void sleepFor(int sleepTime, String messageWhenInterrupted) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            AppLogger.logger.log(Level.WARNING, "Exception while waiting, " + messageWhenInterrupted, e);
            Thread.currentThread().interrupt();
        }
    }
}
