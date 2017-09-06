package com.javaacademy.crawler.jsoup;

import com.javaacademy.crawler.common.logger.AppLogger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.logging.Level;

/**
 * @author devas
 * @since 05.09.17
 */
class JsoupConnector {

    private Connection connection;

    void connect(String link) {
        connection = Jsoup.connect(link);
    }

    Document getDoc() {
        try {
            return connection.get();
        } catch (IOException e) {
            AppLogger.logger.log(Level.SEVERE, "Exception connecting with Jsoup, ", e);
        }
        return null;
    }
}
