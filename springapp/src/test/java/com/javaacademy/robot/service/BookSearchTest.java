package com.javaacademy.robot.service;

import com.javaacademy.robot.model.Book;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * @author Anna Gawda
 * 05.09.2017
 */
public class BookSearchTest {

    private BookService bookServiceMock = mock(BookService.class);
    private List<Book> bookList;

    @BeforeTest
    public void setUp() {
        List<Book> result = new ArrayList<>();
        Book exampleBook = new Book();
        exampleBook.setTitle("Keeping Up with the Quants");
        exampleBook.setAuthors(Arrays.asList("Thomas H. Davenport", "Jinho Kim"));
        result.add(exampleBook);
        bookList = result;
    }

    @Test
    public void shouldReturnBooksContainingTitleKeyword() {
        when(bookServiceMock.getAllBooks()).thenReturn(bookList);
        BookSearch bookSearch = new BookSearch(bookServiceMock);
        String givenTitle = "keeping";

        List<Book> givenResults = bookSearch.search(givenTitle);

        assertEquals(givenResults.size(), 1);
    }

    @Test
    public void shouldReturnBooksContainingAuthorKeyword() {
        when(bookServiceMock.getAllBooks()).thenReturn(bookList);
        BookSearch bookSearch = new BookSearch(bookServiceMock);
        String givenTitle = "davenport";

        List<Book> givenResults = bookSearch.search(givenTitle);

        assertEquals(givenResults.size(), 1);
    }
}