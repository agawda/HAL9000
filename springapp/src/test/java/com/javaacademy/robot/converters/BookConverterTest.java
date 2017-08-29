package com.javaacademy.robot.converters;

import com.javaacademy.robot.model.Book;
import com.javaacademy.robot.model.BookDto;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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

        Book converted = bookConverter.toEntity(bookDto);
        assertTrue(converted.equals(book));
    }

    @Test
    public void testCollectiveConversions() {
        Book book1 = new Book();
        book1.setTitle("Title");
        book1.setSubtitle("Subtitle1");
        Book book2 = new Book();
        book2.setTitle("Title2");
        book2.setSubtitle("Subtitle2");
        List<Book> books = new ArrayList<>(Arrays.asList(book1, book2));

        BookConverter bookConverter = new BookConverter();
        List<BookDto> dtos = bookConverter.toDtos(books);
        BookDto bookDto1 = dtos.get(0);
        BookDto bookDto2 = dtos.get(1);

        assertEquals(bookDto1.getTitle(), book1.getTitle());
        assertEquals(bookDto1.getSubtitle(), book1.getSubtitle());
        assertEquals(bookDto2.getTitle(), book2.getTitle());
        assertEquals(bookDto2.getSubtitle(), book2.getSubtitle());

        List<Book> convertedBooks = bookConverter.toEntities(dtos);
        assertEquals(convertedBooks, books);
    }
}