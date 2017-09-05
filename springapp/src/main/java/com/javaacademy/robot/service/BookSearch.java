package com.javaacademy.robot.service;

import com.javaacademy.robot.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anna Gawda
 * 05.09.2017
 */
@Service
public class BookSearch {

    private BookService bookService;

    public BookSearch(@Autowired BookService bookService) {
        this.bookService = bookService;
    }

    public List<Book> search(String givenTitle) {
        List<Book> result = new ArrayList<>();
        List<Book> allBooks = bookService.getAllBooks();
        allBooks.forEach(book -> {
            if(book.getTitle().toLowerCase().contains(givenTitle)) {
                result.add(book);
            }

            book.getAuthors().forEach(author -> {
                if(author.toLowerCase().contains(givenTitle)) {
                    result.add(book);
                }
            });

            book.getCategories().forEach(category -> {
                if(category.toLowerCase().contains(givenTitle)) {
                    result.add(book);
                }
            });
        });
        return result;
    }
}
