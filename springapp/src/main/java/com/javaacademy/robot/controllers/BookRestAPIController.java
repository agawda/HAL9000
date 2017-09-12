package com.javaacademy.robot.controllers;

import com.javaacademy.robot.model.BookDto;
import com.javaacademy.robot.service.BookSearch;
import com.javaacademy.robot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Anna Gawda
 * 08.09.2017
 */
@RestController
public class BookRestAPIController {

    private BookService bookService;
    private BookSearch bookSearch;

    @Autowired
    public BookRestAPIController(BookService bookService, BookSearch bookSearch) {
        this.bookService = bookService;
        this.bookSearch = bookSearch;
    }

    @RequestMapping("/api/books")
    public BookDto bookRequestById(@RequestParam(value = "id") Long id) {
        return bookService.getBookByIsbn(id);
    }

    @RequestMapping("/api/app")
    public List<BookDto> bookList() {
        return bookService.getAllBookDtos();
    }

    @RequestMapping("/api/search")
    public List<BookDto> searchResults(@RequestParam(value = "query") String query) {
        return bookSearch.search(query);
    }
}
