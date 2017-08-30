package com.javaacademy.crawler.googlebooks.model;

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class IsbnTest {

    @Test
    public void equality() {
        Isbn imageLinks = new Isbn();
        Isbn imageLinks2 = new Isbn();
        assertTrue(imageLinks.equals(imageLinks2));
        imageLinks.identifier = "Test";
        assertFalse(imageLinks.equals(imageLinks2));
    }

}