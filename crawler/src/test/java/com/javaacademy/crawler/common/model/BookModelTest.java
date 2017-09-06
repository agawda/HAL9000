package com.javaacademy.crawler.common.model;

import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.*;
import static org.testng.AssertJUnit.assertEquals;

@Test
public class BookModelTest {

    public void getterAndSetterTests() {
        BookModel bookModel = new BookModel();
        String title = "DummyTitle";
        String subtitle = "DummySubtitle";
        List<String> authors = new ArrayList<>(Arrays.asList("Author1"));
        Long industryIdentifier = 10L;
        List<String> categories = new LinkedList<>(Arrays.asList("Cat1", "Cat2"));
        String smallThumbnail = "DummyThumbnail";
        String canonicalVolumeLink = "DummyLink";
        String saleability = "DummySaleability";
        double listPriceAmount = 1.0;
        String listPriceCurrencyCode = "DummyCurrencyCode";
        double retailPriceAmount = 2.0;
        String retailPriceCurrencyCode = "DummyRetailsPriceCode";

        bookModel.setTitle(title);
        bookModel.setSubtitle(subtitle);
        bookModel.setAuthors(authors);
        bookModel.setIndustryIdentifier(industryIdentifier);
        bookModel.setCategories(categories);
        bookModel.setSmallThumbnail(smallThumbnail);
        bookModel.setCanonicalVolumeLink(canonicalVolumeLink);
        bookModel.setSaleability(saleability);
        bookModel.setListPriceAmount(listPriceAmount);
        bookModel.setListPriceCurrencyCode(listPriceCurrencyCode);
        bookModel.setRetailPriceAmount(retailPriceAmount);
        bookModel.setRetailPriceCurrencyCode(retailPriceCurrencyCode);

        assertEquals(bookModel.getTitle(), title);
        assertEquals(bookModel.getSubtitle(), subtitle);
        assertEquals(bookModel.getAuthors(), authors);
        assertEquals(bookModel.getIndustryIdentifier(), industryIdentifier);
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
        bookModel.setTitle("Java");
        assertFalse(bookModel.equals(bookModel1));
    }

    public void toStringTest() {
        BookModel bookModel = new BookModel();
        assertEquals(bookModel.toString(),
                "BookModel(industryIdentifier=null, title=null, subtitle=null, authors=null, categories=null, canonicalVolumeLink=null, saleability=null, smallThumbnail=null, listPriceAmount=0.0, retailPriceAmount=0.0, listPriceCurrencyCode=null, retailPriceCurrencyCode=null)");
    }

    @Test
    public void testThis() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2,3,4,5,6));
        int maxIndex = 7;
        if( maxIndex > list.size()) {
            maxIndex = list.size();
        }
        List<Integer> sublist = list.subList(0, maxIndex);
        assertEquals(sublist.size(), 6);
    }
}