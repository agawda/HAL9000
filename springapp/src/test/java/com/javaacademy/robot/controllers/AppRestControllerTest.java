package com.javaacademy.robot.controllers;

import com.javaacademy.robot.model.BookDto;
import com.javaacademy.robot.model.BookModels;
import com.javaacademy.robot.service.BookService;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class AppRestControllerTest {
    private BookService bookService = mock(BookService.class);

    @Test
    public void getAllBooksTest() {
        bookService = mock(BookService.class);
        List<BookDto> allBooks = new ArrayList<>();
        when(bookService.getAllBookDtos()).thenReturn(allBooks);
        AppRestController appRestController = new AppRestController(bookService);
        ResponseEntity responseEntity = appRestController.getAllBooks();
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), allBooks);
    }

    @Test
    public void removeBookTestNotFound() {
        bookService = mock(BookService.class);
        doThrow(EmptyResultDataAccessException.class).when(bookService).remove(any());
        AppRestController appRestController = new AppRestController(bookService);
        ResponseEntity responseEntity = appRestController.removeBookByIsbn(1L);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(responseEntity.getBody(), "Book not found");
    }

    @Test
    public void removeBookTestOk() {
        bookService = mock(BookService.class);
        AppRestController appRestController = new AppRestController(bookService);
        Long id = 1L;
        ResponseEntity responseEntity = appRestController.removeBookByIsbn(id);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        verify(bookService, times(1)).remove(id);
    }

    @Test
    public void addAllTestOK() {
        bookService = mock(BookService.class);
        AppRestController appRestController = new AppRestController(bookService);
        BookModels bookModels = mock(BookModels.class);
        ResponseEntity responseEntity = appRestController.addAllBookDtos(bookModels);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        verify(bookService, times(1)).addAllBookDtos(bookModels);
    }

    @Test
    public void addAllTestException() {
        bookService = mock(BookService.class);
        doThrow(Exception.class).when(bookService).addAllBookDtos(any());
        AppRestController appRestController = new AppRestController(bookService);
        BookModels bookModels = mock(BookModels.class);
        ResponseEntity responseEntity = appRestController.addAllBookDtos(bookModels);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        verify(bookService, times(1)).addAllBookDtos(bookModels);
    }

    @Test
    public void saveBookTest() {
        bookService = mock(BookService.class);
        BookDto bookDto = new BookDto();
        AppRestController appRestController = new AppRestController(bookService);
        ResponseEntity responseEntity = appRestController.saveBook(bookDto);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        verify(bookService, times(1)).saveBook(bookDto);
    }

    @Test
    public void getBookByIsbnTest() {
        bookService = mock(BookService.class);
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Test");
        Long isbn = 1L;
        when(bookService.getBookByIsbn(isbn)).thenReturn(bookDto);
        AppRestController appRestController = new AppRestController(bookService);
        ResponseEntity responseEntity = appRestController.getBookByIsbn(isbn);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(bookDto, responseEntity.getBody());
    }

}