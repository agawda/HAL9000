package com.javaacademy.robot.service;

import com.javaacademy.robot.converters.BookConverter;
import com.javaacademy.robot.helpers.FilterOrder;
import com.javaacademy.robot.model.Book;
import com.javaacademy.robot.model.BookDto;
import com.javaacademy.robot.model.BookModels;
import com.javaacademy.robot.repository.BookRepository;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {
    private BookRepository bookRepository = mock(BookRepository.class);
    private BookConverter bookConverter = mock(BookConverter.class);
    private Page pageMock = mock(Page.class);
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

    @Test
    public void shouldReturnOnePage() {
        Page pageMock = mock(Page.class);
        BookService givenBookService = new BookService(bookRepository, bookConverter);
        int givenPageId = 0;

        when(bookRepository.findAll(any(Pageable.class))).thenReturn(pageMock);
        when(bookConverter.toDtos(getBooks())).thenReturn(getDtos());
        when(bookRepository.findAll(any(Pageable.class)).getContent()).thenReturn(getBooks());

        List<BookDto> givenBooks = givenBookService.findAll(givenPageId);
        assertEquals(givenBooks.size(), 3);
    }

    @Test
    public void shouldReturnBooksSortedByTitleAscending() {
        BookService givenBookService = new BookService(bookRepository, bookConverter);
        int givenPageId = 0;

        when(bookRepository.findAll(new PageRequest(givenPageId, 20, Sort.Direction.ASC, "title"))).thenReturn(pageMock);
        when(pageMock.getContent()).thenReturn(getBooks());
        when(bookConverter.toDtos(any())).thenReturn(getDtos());

        List<BookDto> givenBooks = givenBookService.findAll(FilterOrder.ASCENDING, "title", givenPageId);
        assertNotNull(givenBooks);
    }

    @Test
    public void shouldReturnBooksSortedByTitleDescending() {
        BookService givenBookService = new BookService(bookRepository, bookConverter);
        int givenPageId = 0;

        when(bookRepository.findAll(new PageRequest(givenPageId, 20, Sort.Direction.DESC, "title"))).thenReturn(pageMock);
        when(pageMock.getContent()).thenReturn(getBooks());
        when(bookConverter.toDtos(any())).thenReturn(getDtos());

        List<BookDto> givenBooks = givenBookService.findAll(FilterOrder.DESCENDING, "title", givenPageId);
        assertNotNull(givenBooks);
    }

    @Test
    public void shouldReturnBooksSortedByAuthorsAscending() {
        BookService givenBookService = new BookService(bookRepository, bookConverter);
        int givenPageId = 0;

        when(bookRepository.findAll(new PageRequest(givenPageId, 20, Sort.Direction.ASC, "authors"))).thenReturn(pageMock);
        when(pageMock.getContent()).thenReturn(getBooks());
        when(bookConverter.toDtos(any())).thenReturn(getDtos());

        List<BookDto> givenBooks = givenBookService.findAll(FilterOrder.ASCENDING, "authors", givenPageId);
        assertNotNull(givenBooks);
    }

    @Test
    public void shouldReturnBooksSortedByAuthorsDescending() {
        BookService givenBookService = new BookService(bookRepository, bookConverter);
        int givenPageId = 0;

        when(bookRepository.findAll(new PageRequest(givenPageId, 20, Sort.Direction.DESC, "authors"))).thenReturn(pageMock);
        when(pageMock.getContent()).thenReturn(getBooks());
        when(bookConverter.toDtos(any())).thenReturn(getDtos());

        List<BookDto> givenBooks = givenBookService.findAll(FilterOrder.DESCENDING, "authors", givenPageId);
        assertNotNull(givenBooks);
    }

    @Test
    public void shouldReturnBooksSortedByPriceAscending() {
        BookService givenBookService = new BookService(bookRepository, bookConverter);
        int givenPageId = 0;

        when(bookRepository.findAll(new PageRequest(givenPageId, 20, Sort.Direction.ASC, "retailPriceAmount"))).thenReturn(pageMock);
        when(pageMock.getContent()).thenReturn(getBooks());
        when(bookConverter.toDtos(any())).thenReturn(getDtos());

        List<BookDto> givenBooks = givenBookService.findAll(FilterOrder.ASCENDING, "retailPriceAmount", givenPageId);
        assertNotNull(givenBooks);
    }

    @Test
    public void shouldReturnBooksSortedByPriceDescending() {
        BookService givenBookService = new BookService(bookRepository, bookConverter);
        int givenPageId = 0;

        when(bookRepository.findAll(new PageRequest(givenPageId, 20, Sort.Direction.DESC, "retailPriceAmount"))).thenReturn(pageMock);
        when(pageMock.getContent()).thenReturn(getBooks());
        when(bookConverter.toDtos(any())).thenReturn(getDtos());

        List<BookDto> givenBooks = givenBookService.findAll(FilterOrder.DESCENDING, "retailPriceAmount",  givenPageId);
        assertNotNull(givenBooks);
    }

    @Test
    public void shouldReturnBooksSortedByDiscountAscending() {
        BookService givenBookService = new BookService(bookRepository, bookConverter);
        int givenPageId = 0;

        when(bookRepository.findAll(new PageRequest(givenPageId, 20, Sort.Direction.ASC, "discount"))).thenReturn(pageMock);
        when(pageMock.getContent()).thenReturn(getBooks());
        when(bookConverter.toDtos(any())).thenReturn(getDtos());

        List<BookDto> givenBooks = givenBookService.findAll(FilterOrder.ASCENDING, "discount", givenPageId);
        assertNotNull(givenBooks);
    }

    @Test
    public void shouldReturnBooksSortedByDiscountDescending() {
        BookService givenBookService = new BookService(bookRepository, bookConverter);
        int givenPageId = 0;

        when(bookRepository.findAll(new PageRequest(givenPageId, 20, Sort.Direction.DESC, "discount"))).thenReturn(pageMock);
        when(pageMock.getContent()).thenReturn(getBooks());
        when(bookConverter.toDtos(any())).thenReturn(getDtos());

        List<BookDto> givenBooks = givenBookService.findAll(FilterOrder.DESCENDING, "discount", givenPageId);
        assertNotNull(givenBooks);
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
        Book book = new Book();
        book.setIndustryIdentifier(1L);
        Book book2 = new Book();
        book2.setIndustryIdentifier(2L);
        Book book3 = new Book();
        book3.setIndustryIdentifier(3L);
        return new ArrayList<>(Arrays.asList(book, book2, book3));
    }

    private List<BookDto> getDtos() {
        BookDto bookDto = new BookDto();
        bookDto.setIndustryIdentifier(1L);
        BookDto bookDto2 = new BookDto();
        bookDto2.setIndustryIdentifier(2L);
        BookDto bookDto3 = new BookDto();
        bookDto3.setIndustryIdentifier(3L);
        return new ArrayList<>(Arrays.asList(bookDto, bookDto2, bookDto3));
    }
}