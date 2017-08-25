package com.javaacademy.crawler.googlebooks.model;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Test
public class BookItemTest {

    public void testBookCreation() {
        BookItem bookItem = new BookItem();
        VolumeInfo volumeInfo = new VolumeInfo();
        SaleInfo saleInfo = new SaleInfo();
        Price price1 = new Price();
        price1.amount = 1.0;
        price1.currencyCode = "CODE1";
        saleInfo.listPrice = price1;
        Price price2 = new Price();
        price2.amount = 2.0;
        price2.currencyCode = "CODE2";
        saleInfo.retailPrice = price2;
        saleInfo.saleability = "DummyData";
        bookItem.saleInfo = saleInfo;
        bookItem.volumeInfo = volumeInfo;

        assertEquals(bookItem.getVolumeInfo(), volumeInfo);
        assertEquals(bookItem.getSaleInfo(), saleInfo);
    }

    public void testToString() {
        BookItem bookItem = new BookItem();
        bookItem.saleInfo = new SaleInfo();
        assertEquals(bookItem.toString(), "BookItem(volumeInfo=null, saleInfo=SaleInfo(saleability=null, listPrice=null, retailPrice=null))");
    }

    public void testEquals() {
        BookItem bookItem = new BookItem();
        BookItem bookItem1 = new BookItem();
        assertTrue(bookItem.equals(bookItem1));
        bookItem.saleInfo = new SaleInfo();
        assertFalse(bookItem.equals(bookItem1));
    }

}