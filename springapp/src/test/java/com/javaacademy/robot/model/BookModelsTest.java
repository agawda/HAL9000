package com.javaacademy.robot.model;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class BookModelsTest {

    @Test
    public void bookModelGetterTest() {
        BookModels bookModels = new BookModels();
        BookDto bookDto = mock(BookDto.class);
        List<BookDto> books = new ArrayList<>();
        books.add(bookDto);
        bookModels.bookDtos = books;
        assertEquals(books, bookModels.getBookDtos());
    }

    @Test
    public void toStringTest() {
        BookModels bookModels = new BookModels();
        BookDto bookDto = new BookDto();
        List<BookDto> books = new ArrayList<>();
        books.add(bookDto);
        bookModels.bookDtos = books;
        assertEquals("BookModels(bookDtos=[BookDto(industryIdentifier=null, title=null, subtitle=null, authors=null, categories=null, smallThumbnail=null, canonicalVolumeLink=null, saleability=null, listPriceAmount=0.0, listPriceCurrencyCode=null, retailPriceAmount=0.0, discount=0, retailPriceCurrencyCode=null, shopName=null, dateAdded=null)])",
                bookModels.toString());
    }

}