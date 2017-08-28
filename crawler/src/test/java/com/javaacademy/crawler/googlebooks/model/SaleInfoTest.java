package com.javaacademy.crawler.googlebooks.model;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Test
public class SaleInfoTest {
    private static final String DUMMY_STRING = "Dummy";

    public void testGetters() {
        SaleInfo saleInfo = new SaleInfo();
        saleInfo.saleability = DUMMY_STRING;
        Price price = new Price();
        saleInfo.listPrice = price;
        saleInfo.retailPrice = price;

        assertEquals(saleInfo.getSaleability(), DUMMY_STRING);
        assertEquals(saleInfo.getRetailPrice(), price);
        assertEquals(saleInfo.getListPrice(), price);
    }

    public void testEquals() {
        SaleInfo saleInfo = new SaleInfo();
        SaleInfo saleInfo1 = new SaleInfo();
        assertTrue(saleInfo.equals(saleInfo1));
        saleInfo.saleability = DUMMY_STRING;
        assertFalse(saleInfo.equals(saleInfo1));
    }

    public void testToString() {
        SaleInfo saleInfo = new SaleInfo();
        saleInfo.saleability = DUMMY_STRING;
        assertEquals(saleInfo.toString(), "SaleInfo(saleability=Dummy, listPrice=null, retailPrice=null)");
    }
}