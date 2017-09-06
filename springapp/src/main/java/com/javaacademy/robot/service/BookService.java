package com.javaacademy.robot.service;

import com.javaacademy.robot.converters.BookConverter;
import com.javaacademy.robot.logger.ServerLogger;
import com.javaacademy.robot.model.Book;
import com.javaacademy.robot.model.BookDto;
import com.javaacademy.robot.model.BookModels;
import com.javaacademy.robot.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.javaacademy.robot.logger.ServerLogger.DEFAULT_LEVEL;
import static com.javaacademy.robot.logger.ServerLogger.logger;

@Service
public class BookService {

    private BookRepository bookRepository;
    private BookConverter bookConverter;

    public BookService(@Autowired BookRepository bookRepository, @Autowired BookConverter bookConverter) {
        this.bookRepository = bookRepository;
        this.bookConverter = bookConverter;
    }

    boolean saveBook(Book book) {
        Book savedBook = new Book();
        try {
            savedBook = bookRepository.save(book);
        } catch (Exception e) {
            logger.log(DEFAULT_LEVEL, "Could not save the book " + book + ", exception: " +e.getMessage());
        }
        return book.equals(savedBook);
    }

    public boolean saveBook(BookDto bookDto) {
        Book convertedEntity = bookConverter.toEntity(bookDto);
        return saveBook(convertedEntity);
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

    public List<BookDto> getAllBookDtos() {
        return bookConverter.toDtos(getAllBooks());
    }
}
