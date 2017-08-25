package com.javaacademy.crawler.googlebooks.model;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Test
public class PriceTest {

    public void testGetters() {
        Price price = new Price();
        double amount = 1.0;
        String currency = "Dummy";
        price.amount = amount;
        price.currencyCode = currency;

        assertEquals(price.getAmount(), amount);
        assertEquals(price.getCurrencyCode(), currency);
    }

    public void testEquals() {
        Price price = new Price();
        Price price1 = new Price();
        assertTrue(price.equals(price1));
        price.currencyCode = "WTF";
        assertFalse(price.equals(price1));
    }
}