package com.javaacademy.robot.service;

import com.javaacademy.robot.model.Book;
import com.javaacademy.robot.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private BookRepository bookRepository;

    public BookService(@Autowired BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public boolean saveBook(Book book) {
        Book savedBook = bookRepository.save(book);
        return book.equals(savedBook);
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        bookRepository.findAll().forEach(books::add);
        return books;
    }
}
