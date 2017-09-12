package com.javaacademy.robot.controllers;

import com.javaacademy.robot.model.BookDto;
import com.javaacademy.robot.service.BookSearch;
import com.javaacademy.robot.service.BookService;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Anna Gawda
 * @since 12.09.2017
 */
public class BookRestAPIControllerTest {
    private BookService bookService;

    @Test
    public void shouldReturnAllBooks() {
        bookService = mock(BookService.class);
        BookRestAPIController bookRestAPIController = new BookRestAPIController(bookService, null);
        List<BookDto> allBooks = Collections.emptyList();
        when(bookService.getAllBookDtos()).thenReturn(allBooks);
        List<BookDto> givenBooks = bookRestAPIController.bookList();

        assertEquals(Collections.emptyList(), givenBooks);
    }

    @Test
    public void shouldReturnOneBook() {
        bookService = mock(BookService.class);
        BookRestAPIController bookRestAPIController = new BookRestAPIController(bookService, null);
        Long id = 1L;
        BookDto expectedBook = new BookDto();
        expectedBook.setIndustryIdentifier(id);
        when(bookService.getBookByIsbn(id)).thenReturn(expectedBook);
        BookDto givenBook = bookRestAPIController.bookRequestById(id);

        assertEquals(expectedBook, givenBook);
    }

    @Test
    public void shouldReturnEmptyBook() {
        BookSearch bookSearch = mock(BookSearch.class);
        BookRestAPIController bookRestAPIController = new BookRestAPIController(null, bookSearch);
        String givenQuery = "Franklin";
        when(bookSearch.search(givenQuery)).thenReturn(Collections.emptyList());
        List<BookDto> givenSearchResults = bookRestAPIController.searchResults(givenQuery);

        assertEquals(Collections.emptyList(), givenSearchResults);
    }
}