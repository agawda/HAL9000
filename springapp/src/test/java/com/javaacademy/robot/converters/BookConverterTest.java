package com.javaacademy.robot.converters;

import com.javaacademy.robot.model.Book;
import com.javaacademy.robot.model.BookDto;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        book.setListPriceAmount(30.0);
        book.setListPriceCurrencyCode("listCurrCode");
        book.setRetailPriceAmount(20.0);
        book.setDiscount(new BookConverter().calculateDiscount(30, 20));
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
        assertEquals(bookDto.getListPriceAmount(), book.getListPriceAmount(), 0.1);
        assertEquals(bookDto.getListPriceCurrencyCode(), book.getListPriceCurrencyCode());
        assertEquals(bookDto.getRetailPriceAmount(), book.getRetailPriceAmount(), 0.1);
        assertEquals(bookDto.getRetailPriceCurrencyCode(), book.getRetailPriceCurrencyCode());

        Book converted = bookConverter.toEntity(bookDto);
        assertTrue(converted.equals(book));
    }

    @Test
    public void testCollectiveConversions() {
        Book book1 = new Book();
        book1.setTitle("Title");
        book1.setSubtitle("Subtitle1");
        book1.setAuthors(new ArrayList<>());
        Book book2 = new Book();
        book2.setTitle("Title2");
        book2.setSubtitle("Subtitle2");
        book2.setAuthors(new ArrayList<>());
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

    @Test
    public void recognizeShopNameTest() {
        BookConverter bookConverter = new BookConverter();
        BookConverter.Shop[] shops = BookConverter.Shop.values();
        for (BookConverter.Shop shop : shops) {
            BookDto bookDto = new BookDto();
            bookDto.setCanonicalVolumeLink("http://" + shop.getStoreAddress() + "/");
            assertEquals(shop.toString(), bookConverter.recognizeShopName(bookDto));
        }
    }

    @Test
    public void recognizeShopNameTestEmptyString() {
        BookConverter bookConverter = new BookConverter();
        String s = "";
        BookDto bookDto = new BookDto();
        bookDto.setCanonicalVolumeLink(s);
        String result = bookConverter.recognizeShopName(bookDto);
        assertEquals(result, "UNKNOWN");
    }

    @Test
    public void calculateDiscountTest() {
        BookConverter bookConverter = new BookConverter();
        double listPriceDiscount = 2;
        double retailPriceAmount = 1;
        byte result = bookConverter.calculateDiscount(listPriceDiscount, retailPriceAmount);
        assertEquals(result, 50);
    }

    @Test
    public void calculateDiscountTestZero() {
        BookConverter bookConverter = new BookConverter();
        double listPriceDiscount = 0;
        double retailPriceAmount = 0;
        byte result = bookConverter.calculateDiscount(listPriceDiscount, retailPriceAmount);
        assertEquals(result, 0);
    }

    @Test
    public void parseAuthorsTest() {
        BookConverter bookConverter = new BookConverter();
        List<String> authors = new ArrayList<>();
        authors.add("WTF");
        assertEquals(authors, bookConverter.parseAuthors(authors));
    }

    @Test
    public void parseAuthorsTestNull() {
        BookConverter bookConverter = new BookConverter();
        List<String> authors = null;
        assertEquals(new ArrayList<>(), bookConverter.parseAuthors(authors));
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateDiscountTestException() {
        BookConverter bookConverter = new BookConverter();
        double listPriceDiscount = 1;
        double retailPriceAmount = 3;
        bookConverter.calculateDiscount(listPriceDiscount, retailPriceAmount);
    }
}