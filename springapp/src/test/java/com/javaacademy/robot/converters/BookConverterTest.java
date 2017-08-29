package com.javaacademy.robot.converters;

import com.javaacademy.robot.model.Book;
import com.javaacademy.robot.model.BookDto;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.testng.Assert.assertEquals;

public class BookConverterTest {

    @Test
    public void toDtoTest() {
        Book book = new Book();
        book.setIndustryIdentifier(1L);
        book.setTitle("Title");
        book.setSubtitle("Subtitle");
        book.setAuthors(new ArrayList<>(Arrays.asList("WTF", "OMG")));
        book.setCategories(new ArrayList<>(Arrays.asList("CAT1", "CAT2")));
        book.setSmallThumbnail("SmallThumb");
        book.setCanonicalVolumeLink("CanonLink");
        book.setSaleability("Saleability");
        book.setListPriceAmount(10.0);
        book.setListPriceCurrencyCode("listCurrCode");
        book.setRetailPriceAmount(20.0);
        book.setRetailPriceCurrencyCode("retailCurr");

        BookConverter bookConverter = new BookConverter();
        BookDto bookDto = bookConverter.toDto(book);

        assertEquals(bookDto.getIndustryIdentifier(), book.getIndustryIdentifier());
        assertEquals(bookDto.getTitle(), book.getTitle());
        assertEquals(bookDto.getSubtitle(), book.getSubtitle());
        assertEquals(bookDto.getAuthors(), book.getAuthors());
        assertEquals(bookDto.getCategories(), book.getCategories());
        assertEquals(bookDto.getSmallThumbnail(), book.getSmallThumbnail());
        assertEquals(bookDto.getCanonicalVolumeLink(), book.getCanonicalVolumeLink());
        assertEquals(bookDto.getSaleability(), book.getSaleability());
        assertEquals(bookDto.getListPriceAmount(), book.getListPriceAmount());
        assertEquals(bookDto.getListPriceCurrencyCode(), book.getListPriceCurrencyCode());
        assertEquals(bookDto.getRetailPriceAmount(), book.getRetailPriceAmount());
        assertEquals(bookDto.getRetailPriceCurrencyCode(), book.getRetailPriceCurrencyCode());
    }

    @Test
    public void toEntityTest() {
        BookDto bookDto = new BookDto();
        bookDto.setIndustryIdentifier(1L);
        bookDto.setTitle("Title");
        bookDto.setSubtitle("Subtitle");
        bookDto.setAuthors(new ArrayList<>(Arrays.asList("WTF", "OMG")));
        bookDto.setCategories(new ArrayList<>(Arrays.asList("CAT1", "CAT2")));
        bookDto.setSmallThumbnail("SmallThumb");
        bookDto.setCanonicalVolumeLink("CanonLink");
        bookDto.setSaleability("Saleability");
        bookDto.setListPriceAmount(10.0);
        bookDto.setListPriceCurrencyCode("listCurrCode");
        bookDto.setRetailPriceAmount(20.0);
        bookDto.setRetailPriceCurrencyCode("retailCurr");

        BookConverter bookConverter = new BookConverter();
        Book book = bookConverter.toEntity(bookDto);

        assertEquals(bookDto.getIndustryIdentifier(), book.getIndustryIdentifier());
        assertEquals(bookDto.getTitle(), book.getTitle());
        assertEquals(bookDto.getSubtitle(), book.getSubtitle());
        assertEquals(bookDto.getAuthors(), book.getAuthors());
        assertEquals(bookDto.getCategories(), book.getCategories());
        assertEquals(bookDto.getSmallThumbnail(), book.getSmallThumbnail());
        assertEquals(bookDto.getCanonicalVolumeLink(), book.getCanonicalVolumeLink());
        assertEquals(bookDto.getSaleability(), book.getSaleability());
        assertEquals(bookDto.getListPriceAmount(), book.getListPriceAmount());
        assertEquals(bookDto.getListPriceCurrencyCode(), book.getListPriceCurrencyCode());
        assertEquals(bookDto.getRetailPriceAmount(), book.getRetailPriceAmount());
        assertEquals(bookDto.getRetailPriceCurrencyCode(), book.getRetailPriceCurrencyCode());
    }

}