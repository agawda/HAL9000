package com.javaacademy.robot.service;

import com.javaacademy.robot.model.BookDto;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Anna Gawda
 * 05.09.2017
 */
public class BookSearchTest {

    private BookService bookServiceMock = mock(BookService.class);
    private List<BookDto> bookList;

    @Before
    public void setUp() {
        List<BookDto> result = new ArrayList<>();
        BookDto exampleBook = new BookDto();
        exampleBook.setTitle("Keeping Up with the Quants");
        exampleBook.setAuthors(Arrays.asList("Thomas H. Davenport", "Jinho Kim"));
        exampleBook.setCategories(Arrays.asList("Medical", "Thriller"));
        exampleBook.setRetailPriceAmount(75);
        result.add(exampleBook);
        bookList = result;
    }

    @Test
    public void shouldReturnBooksContainingTitleKeyword() {
        when(bookServiceMock.getAllBookDtos()).thenReturn(bookList);
        BookSearch bookSearch = new BookSearch(bookServiceMock);
        String givenTitle = "keeping";

        List<BookDto> givenResults = bookSearch.search(givenTitle);

        assertEquals(givenResults.size(), 1);
    }

    @Test
    public void shouldReturnBooksContainingAuthorKeyword() {
        when(bookServiceMock.getAllBookDtos()).thenReturn(bookList);
        BookSearch bookSearch = new BookSearch(bookServiceMock);
        String givenTitle = "davenport";

        List<BookDto> givenResults = bookSearch.search(givenTitle);

        assertEquals(givenResults.size(), 1);
    }

    @Test
    public void shouldReturnBooksContainingCategoryKeyword() {
        when(bookServiceMock.getAllBookDtos()).thenReturn(bookList);
        BookSearch bookSearch = new BookSearch(bookServiceMock);
        String givenTitle = "medical";

        List<BookDto> givenResults = bookSearch.search(givenTitle);

        assertEquals(givenResults.size(), 1);
    }

    @Test
    public void shouldReturnBooksContainingAllKeywords() {
        when(bookServiceMock.getAllBookDtos()).thenReturn(bookList);
        BookSearch bookSearch = new BookSearch(bookServiceMock);
        Map<String, String> query = new HashMap<>();
        query.put("title", "Keeping");
        query.put("author", "Davenport");
        query.put("category", "medical");
        query.put("minPrice", "50");
        query.put("maxPrice", "100");

        Set<BookDto> givenResults = bookSearch.advancedSearch(query);

        assertEquals(givenResults.size(), 1);
    }

    @Test
    public void shouldReturnBooksContainingMinPrice() {
        when(bookServiceMock.getAllBookDtos()).thenReturn(bookList);
        BookSearch bookSearch = new BookSearch(bookServiceMock);
        Map<String, String> query = new HashMap<>();
        query.put("minPrice", "50");
        query.put("maxPrice", "-1.0");

        Set<BookDto> givenResults = bookSearch.advancedSearch(query);

        assertEquals(givenResults.size(), 1);
    }

    @Test
    public void shouldReturnBooksContainingMaxPrice() {
        when(bookServiceMock.getAllBookDtos()).thenReturn(bookList);
        BookSearch bookSearch = new BookSearch(bookServiceMock);
        Map<String, String> query = new HashMap<>();
        query.put("minPrice", "-1.0");
        query.put("maxPrice", "100");

        Set<BookDto> givenResults = bookSearch.advancedSearch(query);

        assertEquals(givenResults.size(), 1);
    }
}