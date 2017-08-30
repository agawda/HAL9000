package com.javaacademy.crawler.googlebooks.model;

import com.javaacademy.crawler.common.converters.GoogleBookConverter;
import com.javaacademy.crawler.common.model.BookModel;
import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.assertEquals;

public class GoogleBookConverterTest {
    @Test
    public void testConvertToDto() {
        Price listPrice = new Price();
        double listPriceAmount = 1.0;
        String listPriceCurrencyCode = "ListPriceCurrencyCodeTest";
        listPrice.amount = listPriceAmount;
        listPrice.currencyCode = listPriceCurrencyCode;
        Price retailPrice = new Price();
        double retailPriceAmount = 2.0;
        String retailPriceCurrencyCode = "RetailPriceCurrencyCodeTest";
        retailPrice.amount = retailPriceAmount;
        retailPrice.currencyCode = retailPriceCurrencyCode;
        String saleability = "SaleabilityString";
        SaleInfo saleInfo = new SaleInfo();
        saleInfo.saleability = saleability;
        saleInfo.retailPrice = retailPrice;
        saleInfo.listPrice = listPrice;
        BookItem bookItem = new BookItem();
        bookItem.saleInfo = saleInfo;
        String title = "TitleTest";
        String subtitle = "SubtitleTest";
        List<String> authors = new ArrayList<>(Arrays.asList("Author1", "Author2"));
        Isbn isbn = new Isbn();
        String type = "ISBN_13";
        String identifier = "1101";
        isbn.type = type;
        isbn.identifier = identifier;
        List<Isbn> isbns = new ArrayList<>(Arrays.asList(isbn));
        List<String> categories = new ArrayList<>(Arrays.asList("Cat1", "Cat2"));
        ImageLinks imageLinks = new ImageLinks();
        String smallThumbNail = "SmallThumbnailTest";
        imageLinks.smallThumbnail = smallThumbNail;
        String canonicalVolumeLink = "canonicalVolumeLinkTest";
        VolumeInfo volumeInfo = new VolumeInfo();
        volumeInfo.title = title;
        volumeInfo.subtitle = subtitle;
        volumeInfo.authors = authors;
        volumeInfo.industryIdentifiers = isbns;
        volumeInfo.categories = categories;
        volumeInfo.imageLinks = imageLinks;
        volumeInfo.canonicalVolumeLink = canonicalVolumeLink;
        bookItem.volumeInfo = volumeInfo;

        GoogleBookConverter googleBookConverter = new GoogleBookConverter();
        BookModel bookModel = googleBookConverter.convertToDto(bookItem);

        Long convertedIsbn = 1101L;

        assertEquals(bookModel.getTitle(), title);
        assertEquals(bookModel.getSubtitle(), subtitle);
        assertEquals(bookModel.getAuthors(), authors);
        assertEquals(bookModel.getIndustryIdentifier(), convertedIsbn);
        assertEquals(bookModel.getCategories(), categories);
        assertEquals(bookModel.getSmallThumbnail(), smallThumbNail);
        assertEquals(bookModel.getCanonicalVolumeLink(), canonicalVolumeLink);
        assertEquals(bookModel.getSaleability(), saleability);
        assertEquals(bookModel.getListPriceAmount(), listPriceAmount);
        assertEquals(bookModel.getListPriceCurrencyCode(), listPriceCurrencyCode);
        assertEquals(bookModel.getRetailPriceAmount(), retailPriceAmount);
        assertEquals(bookModel.getRetailPriceCurrencyCode(), retailPriceCurrencyCode);
     }

}