package com.javaacademy.robot.service;

import com.javaacademy.robot.converters.BookConverter;
import com.javaacademy.robot.helpers.FilterOrder;
import com.javaacademy.robot.logger.ServerLogger;
import com.javaacademy.robot.model.Book;
import com.javaacademy.robot.model.BookDto;
import com.javaacademy.robot.model.BookModels;
import com.javaacademy.robot.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static com.javaacademy.robot.logger.ServerLogger.DEFAULT_LEVEL;
import static com.javaacademy.robot.logger.ServerLogger.logger;

@Service
public class BookService {

    private BookRepository bookRepository;
    private BookConverter bookConverter;

    private static final int ENTRIES_PER_PAGE = 20;

    public BookService(@Autowired BookRepository bookRepository, @Autowired BookConverter bookConverter) {
        this.bookRepository = bookRepository;
        this.bookConverter = bookConverter;
    }

    boolean saveBook(Book book) {
        Book savedBook = new Book();
        try {
            savedBook = bookRepository.save(book);
        } catch (Exception e) {
            logger.log(DEFAULT_LEVEL, "Could not save the book " + book + ", exception: " + e.getMessage());
        }
        return book.equals(savedBook);
    }

    public boolean saveBook(BookDto bookDto) {
        try {
            Book convertedEntity = bookConverter.toEntity(bookDto);
            return saveBook(convertedEntity);
        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, "Could not parse book because prices were incorrect: ", e);
        }
        return false;
    }

    List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        bookRepository.findAll().forEach(books::add);
        return books;
    }

    public void remove(Long isbn) {
        bookRepository.delete(isbn);
    }

    public BookDto getBookByIsbn(Long isbn) {
        Book book = bookRepository.findOne(isbn);
        if (book == null) return null;
        return bookConverter.toDto(book);
    }

    public void addAllBookDtos(BookModels bookModels) {
        ServerLogger.logger.log(DEFAULT_LEVEL, "Adding dtos: " + bookModels.getBookDtos());
        List<BookDto> dtos = bookModels.getBookDtos();
        List<BookDto> nonNullDtos = dtos.stream().filter(bookDto -> bookDto.getIndustryIdentifier() != null).collect(Collectors.toList());
        List<Book> books = bookConverter.toEntities(nonNullDtos);
        for (Book book : books) {
            saveBook(book);
        }
    }

    public List<BookDto> findAll(int pageId) {
        List<Book> books = bookRepository.findAll(new PageRequest(pageId, ENTRIES_PER_PAGE)).getContent();
        return this.bookConverter.toDtos(books);
    }

    public List<BookDto> findAll(FilterOrder filterOrder, String columnName, int pageId) {
        List<Book> books;
        if (filterOrder == FilterOrder.ASCENDING) {
            Sort.Order order = new Sort.Order(Sort.Direction.ASC, columnName).ignoreCase();
            books = getFilteredBooks(pageId, new Sort(order));
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, columnName).ignoreCase();
            books = getFilteredBooks(pageId, new Sort(order));
        }
        if(columnName.equalsIgnoreCase("authors")) {
            books.forEach(book -> sortIfApplied(filterOrder, book.getAuthors()));
        }
        if(columnName.equalsIgnoreCase("categories")) {
            books.forEach(book -> sortIfApplied(filterOrder, book.getCategories()));
        }
        return bookConverter.toDtos(books);
    }

    void sortIfApplied(FilterOrder filterOrder, List<String> toSort) {
        if (filterOrder == FilterOrder.ASCENDING) {
            Collections.sort(toSort);
        } else {
            toSort.sort(Collections.reverseOrder());
        }
    }

    private List<Book> getFilteredBooks(int pageId, Sort sort) {
        List<Book> books;
        books = bookRepository.findAll(
                new PageRequest(pageId, ENTRIES_PER_PAGE, sort)).getContent();
        return books;
    }

    public List<BookDto> getAllBookDtos() {
        return bookConverter.toDtos(getAllBooks());
    }

    public List<BookDto> getByEverything(String title, String author, String category, String shopName, double minPrice, double maxPrice) {
        List<Book> books = bookRepository.findAllBy(title, author, category, shopName, minPrice, maxPrice);
        return bookConverter.toDtos(books);
    }

    public List<BookDto> searchOneKeyword(String query) {
        List<Book> books = bookRepository.findAllByQuery(query);
        return bookConverter.toDtos(books);
    }
}
