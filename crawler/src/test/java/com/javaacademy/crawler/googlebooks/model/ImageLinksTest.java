package com.javaacademy.crawler.googlebooks.model;

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ImageLinksTest {

    @Test
    public void equality() {
        ImageLinks imageLinks = new ImageLinks();
        ImageLinks imageLinks2 = new ImageLinks();
        assertTrue(imageLinks.equals(imageLinks2));
        imageLinks.smallThumbnail = "Test";
        assertFalse(imageLinks.equals(imageLinks2));
    }

}