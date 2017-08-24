package com.javaacademy.crawler.googlebooks.model;

/**
 * @author devas
 * @since 24.08.17
 */
public class ImageLinks {

    private String smallThumbnail;

    public String getSmallThumbnail() {
        return smallThumbnail;
    }

    public void setSmallThumbnail(String smallThumbnail) {
        this.smallThumbnail = smallThumbnail;
    }

    @Override
    public String toString() {
        return "ImageLinks{" +
                "smallThumbnail='" + smallThumbnail + '\'' +
                '}';
    }
}
