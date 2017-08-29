package com.javaacademy.robot.service;

import com.javaacademy.robot.converters.BookConverter;
import com.javaacademy.robot.model.Book;
import com.javaacademy.robot.model.BookDto;
import com.javaacademy.robot.repository.BookRepository;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookServiceTest {
    private BookRepository bookRepository = mock(BookRepository.class);
    private BookConverter bookConverter = mock(BookConverter.class);
    BookService bookService;

    @Test
    public void getBookByIsbnTest() {
        long isbn = 10L;
        bookRepository = mock(BookRepository.class);
        bookConverter = mock(BookConverter.class);
        Book book = mock(Book.class);
        BookDto bookDto = mock(BookDto.class);
        when(bookRepository.findOne(isbn)).thenReturn(book);
        when(bookConverter.toDto(book)).thenReturn(bookDto);
        bookService = new BookService(bookRepository, bookConverter);
        BookDto retrievedBook = bookService.getBookByIsbn(isbn);
        assertEquals(retrievedBook, bookDto);
    }

    @Test
    public void saveBookTest() {
        bookConverter = mock(BookConverter.class);
        bookRepository = mock(BookRepository.class);
        Book book = mock(Book.class);
        BookDto bookDto = mock(BookDto.class);
        when(bookConverter.toEntity(bookDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        bookService = new BookService(bookRepository, bookConverter);
        boolean saveResult = bookService.saveBook(bookDto);
        assertTrue(saveResult);
    }

   @Test
    public void getAllBooksTest() {
       bookConverter = mock(BookConverter.class);
       bookRepository = mock(BookRepository.class);
       List<BookDto> bookDtos = new ArrayList<>(Arrays.asList(new BookDto(), new BookDto()));
       List<Book> books = new ArrayList<>(Arrays.asList(new Book(), new Book()));
       when(bookRepository.findAll()).thenReturn(books);
       when(bookConverter.toDtos(books)).thenReturn(bookDtos);
       bookService = new BookService(bookRepository, bookConverter);
       List<BookDto> resultDtos = bookService.getAllBookDtos();
       assertEquals(resultDtos, bookDtos);
   }

}