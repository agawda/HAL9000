package com.javaacademy.crawler.googlebooks.model;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class TotalItemsWrapperTest {

    @Test
    public void testGetTotalItems() {
        TotalItemsWrapper totalItemsWrapper = new TotalItemsWrapper();
        int totalItems = 10;
        totalItemsWrapper.totalItems = totalItems;
        assertEquals(totalItems, totalItemsWrapper.getTotalItems());
    }

}