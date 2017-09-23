package com.javaacademy.robot.controllers;

import com.javaacademy.robot.helpers.FilterOrder;
import com.javaacademy.robot.model.BookDto;
import com.javaacademy.robot.service.BookService;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
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
        BookRestAPIController bookRestAPIController = new BookRestAPIController(bookServiceMock);
        List<BookDto> allBooks = Collections.emptyList();
        when(bookServiceMock.getAllBookDtos()).thenReturn(allBooks);
        List<BookDto> givenBooks = bookRestAPIController.bookList();

        assertEquals(Collections.emptyList(), givenBooks);
    }

    @Test
    public void shouldReturnOneBook() {
        bookServiceMock = mock(BookService.class);
        BookRestAPIController bookRestAPIController = new BookRestAPIController(bookServiceMock);
        Long id = 1L;
        BookDto expectedBook = new BookDto();
        expectedBook.setIndustryIdentifier(id);
        when(bookServiceMock.getBookByIsbn(id)).thenReturn(expectedBook);
        ResponseEntity<BookDto> givenBook = bookRestAPIController.bookRequestById(id);

        assertEquals(expectedBook, givenBook.getBody());
    }

    @Test
    public void shouldReturnEmptyBook() {
        BookService bookServiceMock = mock(BookService.class);
        BookRestAPIController bookRestAPIController = new BookRestAPIController(bookServiceMock);
        String givenQuery = "Franklin";
        when(bookServiceMock.searchOneKeyword(givenQuery.toLowerCase())).thenReturn(getBookDtos());
        List<BookDto> givenSearchResults = bookRestAPIController.searchResults(givenQuery);

        assertEquals(2, givenSearchResults.size());
    }

    @Test
    public void shouldReturnZeroBooks() {
        bookServiceMock = mock(BookService.class);
        BookRestAPIController bookRestAPIController = new BookRestAPIController(bookServiceMock);

        when(bookServiceMock.getAllBookDtos()).thenReturn(Collections.emptyList());

        assertEquals(0, bookRestAPIController.getBooksNumber());
    }

    @Test
    public void shouldReturnTwoBooks() {
        bookServiceMock = mock(BookService.class);
        BookRestAPIController bookRestAPIController = new BookRestAPIController(bookServiceMock);
        int givenPageId = 0;

        when(bookServiceMock.findAll(givenPageId)).thenReturn(getBookDtos());

        assertEquals(2, bookRestAPIController.getPage(givenPageId).size());
    }

    @Test
    public void shouldReturnTwoBooksSorted() {
        bookServiceMock = mock(BookService.class);
        BookRestAPIController bookRestAPIController = new BookRestAPIController(bookServiceMock);
        int givenPageId = 0;

        when(bookServiceMock.findAll(FilterOrder.ASCENDING, "title", givenPageId)).thenReturn(getBookDtos());

        assertEquals(HttpStatus.OK, bookRestAPIController.sortedBooks("title", "ascending", givenPageId).getStatusCode());
    }

    @Test
    public void shouldReturn404ErrorForSortedBooks() {
        bookServiceMock = mock(BookService.class);
        BookRestAPIController bookRestAPIController = new BookRestAPIController(bookServiceMock);
        int givenPageId = 1;

        when(bookServiceMock.findAll(FilterOrder.ASCENDING, "title", givenPageId)).thenReturn(null);

        assertEquals(HttpStatus.NOT_FOUND, bookRestAPIController.sortedBooks("title", "ascending", givenPageId).getStatusCode());
    }

    @Test
    public void shouldReturnSearchedBook() {
        String givenTitle = "Norwegian Wood";
        String givenAuthor = "gawda";
        String givenCategory = "fiction";
        String givenBookstore = "Google";
        double givenMinPrice = 0;
        double givenMaxPrice = 100;
        bookServiceMock = mock(BookService.class);
        BookRestAPIController bookRestAPIController = new BookRestAPIController(bookServiceMock);

        when(bookServiceMock.getByEverything(givenTitle.toLowerCase(), givenAuthor.toLowerCase(), givenCategory.toLowerCase(), givenBookstore.toLowerCase(), givenMinPrice, givenMaxPrice)).thenReturn(getSearchResultBook());
        ResponseEntity<List<BookDto>> givenResponse = bookRestAPIController.advancedSearchController(givenTitle, givenAuthor, givenCategory, givenBookstore, givenMinPrice, givenMaxPrice);
        List<BookDto> books = givenResponse.getBody();

        assertEquals(1, books.size());
        assertEquals(HttpStatus.OK, givenResponse.getStatusCode());
    }

    private List<BookDto> getBookDtos() {
        List<BookDto> bookDtos = new ArrayList<>();
        bookDtos.add(new BookDto());
        bookDtos.add(new BookDto());
        return bookDtos;
    }

    private List<BookDto> getSearchResultBook() {
        List<BookDto> result = new ArrayList<>();
        BookDto book = new BookDto();
        book.setTitle("Norwegian Wood");
        book.setAuthors(Arrays.asList("Anna Gawda"));
        book.setCategories(Arrays.asList("Fiction"));
        book.setShopName("GOOGLE_STORE");

        result.add(book);
        return result;
    }
}