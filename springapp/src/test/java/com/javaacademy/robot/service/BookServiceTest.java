package com.javaacademy.robot.service;

import com.javaacademy.robot.converters.BookConverter;
import com.javaacademy.robot.model.Book;
import com.javaacademy.robot.model.BookDto;
import com.javaacademy.robot.model.BookModels;
import com.javaacademy.robot.repository.BookRepository;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class BookServiceTest {
    private BookRepository bookRepository = mock(BookRepository.class);
    private BookConverter bookConverter = mock(BookConverter.class);
    private BookService bookService;

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

    @Test
    public void testRemove() {
        bookRepository = mock(BookRepository.class);
        bookService = new BookService(bookRepository, bookConverter);
        long id = 10L;
        bookService.remove(id);
        verify(bookRepository, times(1)).delete(id);
    }

    @Test
    public void testAddAllBookDtos() {
        bookRepository = mock(BookRepository.class);
        when(bookConverter.toEntities(anyList())).thenReturn(getBooks());
        bookService = new BookService(bookRepository, bookConverter);
        bookService.addAllBookDtos(getBookModels());
        verify(bookRepository, times(3)).save(any(Book.class));
    }

    @Test
    public void testAddAllBookDtosException() {
        bookRepository = mock(BookRepository.class);
        when(bookConverter.toEntities(anyList())).thenReturn(getBooks());
        when(bookRepository.save(any(Book.class))).thenThrow(Exception.class);
        bookService = new BookService(bookRepository, bookConverter);
        bookService.addAllBookDtos(getBookModels());
        verify(bookRepository, times(3)).save(any(Book.class));
    }

    @Test
    public void testSavingBook() {
        BookDto bookDto = new BookDto();
        when(bookConverter.toEntity(bookDto)).thenThrow(new IllegalArgumentException());
        bookService = new BookService(bookRepository, bookConverter);
        assertFalse(bookService.saveBook(bookDto));
    }

    private BookModels getBookModels() {
        BookDto bookDto = new BookDto();
        bookDto.setIndustryIdentifier(1L);
        BookDto bookDto2 = new BookDto();
        bookDto2.setIndustryIdentifier(2L);
        BookDto bookDto3 = new BookDto();
        bookDto3.setIndustryIdentifier(3L);
        BookModels bookModels = mock(BookModels.class);
        List<BookDto> bookDtos = new ArrayList<>(Arrays.asList(bookDto, bookDto2, bookDto3));
        when(bookModels.getBookDtos()).thenReturn(bookDtos);
        return bookModels;
    }

    private List<Book> getBooks() {
        Book bookDto = new Book();
        bookDto.setIndustryIdentifier(1L);
        Book bookDto2 = new Book();
        bookDto2.setIndustryIdentifier(2L);
        Book bookDto3 = new Book();
        bookDto3.setIndustryIdentifier(3L);
        return new ArrayList<>(Arrays.asList(bookDto, bookDto2, bookDto3));
    }

}