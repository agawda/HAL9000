package com.javaacademy.crawler.jsoup;

import com.javaacademy.crawler.common.logger.AppLogger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.logging.Level;

/**
 * @author devas
 * @since 04.09.17
 */
class JsoupScrapper {

    Document doc;

    void connectAndInitDocument(String link) {
        try {
            doc = Jsoup.connect(link).get();
        } catch (IOException e) {
            AppLogger.logger.log(Level.SEVERE, "Exception connecting with Jsoup, ", e);
        }
    }
}
