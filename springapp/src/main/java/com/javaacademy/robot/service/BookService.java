package com.javaacademy.robot.service;

import com.javaacademy.robot.converters.BookConverter;
import com.javaacademy.robot.model.Book;
import com.javaacademy.robot.model.BookDto;
import com.javaacademy.robot.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private BookRepository bookRepository;
    private BookConverter bookConverter;

    public BookService(@Autowired BookRepository bookRepository, @Autowired BookConverter bookConverter) {
        this.bookRepository = bookRepository;
        this.bookConverter = bookConverter;
    }

    boolean saveBook(Book book) {
        Book savedBook = bookRepository.save(book);
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

    public List<BookDto> getAllBookDtos() {
        return bookConverter.toDtos(getAllBooks());
    }
}
