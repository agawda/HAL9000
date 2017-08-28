package com.javaacademy.crawler.googlebooks.dao;

import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Test
public class GoogleBooksDaoTest {

    public void getterAndSetterTests() {
        GoogleBooksDao googleBooksDao = new GoogleBooksDao();
        int totalItems = 10;
        String title = "DummyTitle";
        String subtitle = "DummySubtitle";
        List<String> authors = new ArrayList<>(Arrays.asList("Author1"));
        Map<String, Integer> industryIdentifiers = new HashMap<>();
        List<String> categories = new LinkedList<>(Arrays.asList("Cat1", "Cat2"));
        String smallThumbnail = "DummyThumbnail";
        String canonicalVolumeLink = "DummyLink";
        String saleability = "DummySaleability";
        double listPriceAmount = 1.0;
        String listPriceCurrencyCode = "DummyCurrencyCode";
        double retailPriceAmount = 2.0;
        String retailPriceCurrencyCode = "DummyRetailsPriceCode";

        googleBooksDao.totalItems = totalItems;
        googleBooksDao.title = title;
        googleBooksDao.subtitle = subtitle;
        googleBooksDao.authors = authors;
        googleBooksDao.industryIdentifiers = industryIdentifiers;
        googleBooksDao.categories = categories;
        googleBooksDao.smallThumbnail = smallThumbnail;
        googleBooksDao.canonicalVolumeLink = canonicalVolumeLink;
        googleBooksDao.saleability = saleability;
        googleBooksDao.listPriceAmount = listPriceAmount;
        googleBooksDao.listPriceCurrencyCode = listPriceCurrencyCode;
        googleBooksDao.retailPriceAmount = retailPriceAmount;
        googleBooksDao.retailPriceCurrencyCode = retailPriceCurrencyCode;

        assertEquals(googleBooksDao.getTotalItems(), totalItems);
        assertEquals(googleBooksDao.getTitle(), title);
        assertEquals(googleBooksDao.getSubtitle(), subtitle);
        assertEquals(googleBooksDao.getAuthors(), authors);
        assertEquals(googleBooksDao.getIndustryIdentifiers(), industryIdentifiers);
        assertEquals(googleBooksDao.getCategories(), categories);
        assertEquals(googleBooksDao.getSmallThumbnail(), smallThumbnail);
        assertEquals(googleBooksDao.getCanonicalVolumeLink(), canonicalVolumeLink);
        assertEquals(googleBooksDao.getSaleability(), saleability);
        assertEquals(googleBooksDao.getListPriceAmount(), listPriceAmount);
        assertEquals(googleBooksDao.getListPriceCurrencyCode(), listPriceCurrencyCode);
        assertEquals(googleBooksDao.getRetailPriceAmount(), retailPriceAmount);
        assertEquals(googleBooksDao.getRetailPriceCurrencyCode(), retailPriceCurrencyCode);
    }

    public void testEquals() {
        GoogleBooksDao googleBooksDao = new GoogleBooksDao();
        GoogleBooksDao googleBooksDao1 = new GoogleBooksDao();
        assertTrue(googleBooksDao.equals(googleBooksDao1));
        googleBooksDao.retailPriceCurrencyCode = "Dummy";
        assertFalse(googleBooksDao.equals(googleBooksDao1));
    }

    public void toStringTest() {
        GoogleBooksDao googleBooksDao = new GoogleBooksDao();
        assertEquals(googleBooksDao.toString(), "GoogleBooksDao(totalItems=0, title=null, subtitle=null, authors=null, industryIdentifiers=null, categories=null, smallThumbnail=null, canonicalVolumeLink=null, saleability=null, listPriceAmount=0.0, listPriceCurrencyCode=null, retailPriceAmount=0.0, retailPriceCurrencyCode=null)");
    }
}