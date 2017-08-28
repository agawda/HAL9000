package com.javaacademy.crawler.common.model;

import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.*;

@Test
public class BookModelTest {

    public void getterAndSetterTests() {
        BookModel bookModel = new BookModel();
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

        bookModel.totalItems = totalItems;
        bookModel.title = title;
        bookModel.subtitle = subtitle;
        bookModel.authors = authors;
        bookModel.industryIdentifiers = industryIdentifiers;
        bookModel.categories = categories;
        bookModel.smallThumbnail = smallThumbnail;
        bookModel.canonicalVolumeLink = canonicalVolumeLink;
        bookModel.saleability = saleability;
        bookModel.listPriceAmount = listPriceAmount;
        bookModel.listPriceCurrencyCode = listPriceCurrencyCode;
        bookModel.retailPriceAmount = retailPriceAmount;
        bookModel.retailPriceCurrencyCode = retailPriceCurrencyCode;

        assertEquals(bookModel.getTotalItems(), totalItems);
        assertEquals(bookModel.getTitle(), title);
        assertEquals(bookModel.getSubtitle(), subtitle);
        assertEquals(bookModel.getAuthors(), authors);
        assertEquals(bookModel.getIndustryIdentifiers(), industryIdentifiers);
        assertEquals(bookModel.getCategories(), categories);
        assertEquals(bookModel.getSmallThumbnail(), smallThumbnail);
        assertEquals(bookModel.getCanonicalVolumeLink(), canonicalVolumeLink);
        assertEquals(bookModel.getSaleability(), saleability);
        assertEquals(bookModel.getListPriceAmount(), listPriceAmount);
        assertEquals(bookModel.getListPriceCurrencyCode(), listPriceCurrencyCode);
        assertEquals(bookModel.getRetailPriceAmount(), retailPriceAmount);
        assertEquals(bookModel.getRetailPriceCurrencyCode(), retailPriceCurrencyCode);
    }

    public void testEquals() {
        BookModel bookModel = new BookModel();
        BookModel bookModel1 = new BookModel();
        assertTrue(bookModel.equals(bookModel1));
        bookModel.retailPriceCurrencyCode = "Dummy";
        assertFalse(bookModel.equals(bookModel1));
    }

    public void toStringTest() {
        BookModel bookModel = new BookModel();
        assertEquals(bookModel.toString(), "BookModel(totalItems=0, title=null, subtitle=null, authors=null, industryIdentifiers=null, categories=null, smallThumbnail=null, canonicalVolumeLink=null, saleability=null, listPriceAmount=0.0, listPriceCurrencyCode=null, retailPriceAmount=0.0, retailPriceCurrencyCode=null)");
    }
}