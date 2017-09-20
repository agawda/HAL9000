package com.javaacademy.robot.controllers;

import com.javaacademy.robot.helpers.FilterOrder;
import com.javaacademy.robot.model.BookDto;
import com.javaacademy.robot.service.BookSearch;
import com.javaacademy.robot.service.BookService;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
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
    private BookService bookServiceMock;

    @Test
    public void shouldReturnAllBooks() {
        bookServiceMock = mock(BookService.class);
        BookRestAPIController bookRestAPIController = new BookRestAPIController(bookServiceMock, null);
        List<BookDto> allBooks = Collections.emptyList();
        when(bookServiceMock.getAllBookDtos()).thenReturn(allBooks);
        List<BookDto> givenBooks = bookRestAPIController.bookList();

        assertEquals(Collections.emptyList(), givenBooks);
    }

    @Test
    public void shouldReturnOneBook() {
        bookServiceMock = mock(BookService.class);
        BookRestAPIController bookRestAPIController = new BookRestAPIController(bookServiceMock, null);
        Long id = 1L;
        BookDto expectedBook = new BookDto();
        expectedBook.setIndustryIdentifier(id);
        when(bookServiceMock.getBookByIsbn(id)).thenReturn(expectedBook);
        ResponseEntity<BookDto> givenBook = bookRestAPIController.bookRequestById(id);

        assertEquals(expectedBook, givenBook.getBody());
    }

    @Test
    public void shouldReturnEmptyBook() {
        BookSearch bookSearchMock = mock(BookSearch.class);
        BookRestAPIController bookRestAPIController = new BookRestAPIController(null, bookSearchMock);
        String givenQuery = "Franklin";
        when(bookSearchMock.search(givenQuery)).thenReturn(Collections.emptyList());
        List<BookDto> givenSearchResults = bookRestAPIController.searchResults(givenQuery);

        assertEquals(Collections.emptyList(), givenSearchResults);
    }

    @Test
    public void shouldReturnZeroBooks() {
        bookServiceMock = mock(BookService.class);
        BookRestAPIController bookRestAPIController = new BookRestAPIController(bookServiceMock, null);

        when(bookServiceMock.getAllBookDtos()).thenReturn(Collections.emptyList());

        assertEquals(bookRestAPIController.getBooksNumber(), 0);
    }

    @Test
    public void shouldReturnTwoBooks() {
        bookServiceMock = mock(BookService.class);
        BookRestAPIController bookRestAPIController = new BookRestAPIController(bookServiceMock, null);
        int givenPageId = 0;

        when(bookServiceMock.findAll(givenPageId)).thenReturn(getBookDtos());

        assertEquals(bookRestAPIController.getPage(givenPageId).size(), 2);
    }

    @Test
    public void shouldReturnTwoBooksSorted() {
        bookServiceMock = mock(BookService.class);
        BookRestAPIController bookRestAPIController = new BookRestAPIController(bookServiceMock, null);
        int givenPageId = 0;

        when(bookServiceMock.findAll(FilterOrder.ASCENDING, "title", givenPageId)).thenReturn(getBookDtos());

        assertEquals(bookRestAPIController.sortedBooks("title", "ascending", givenPageId).getStatusCode(), HttpStatus.OK);
    }

    private List<BookDto> getBookDtos() {
        List<BookDto> bookDtos = new ArrayList<>();
        bookDtos.add(new BookDto());
        bookDtos.add(new BookDto());
        return bookDtos;
    }
}