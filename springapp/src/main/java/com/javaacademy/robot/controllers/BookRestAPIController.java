package com.javaacademy.robot.controllers;

import com.javaacademy.robot.helpers.FilterType;
import com.javaacademy.robot.model.Book;
import com.javaacademy.robot.model.BookDto;
import com.javaacademy.robot.service.BookSearch;
import com.javaacademy.robot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<BookDto> bookRequestById(@RequestParam(value = "id") Long id) {
        BookDto foundBook = bookService.getBookByIsbn(id);
        if(foundBook == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(bookService.getBookByIsbn(id));
    }

    @RequestMapping("/api/app")
    public List<BookDto> bookList() {
        return bookService.getAllBookDtos();
    }

    @RequestMapping("/api/search")
    public List<BookDto> searchResults(@RequestParam(value = "query") String query) {
        return bookSearch.search(query);
    }

    @RequestMapping("/api/pages")
    public List<BookDto> getPage(@RequestParam(value = "id") int pageId) {
        return bookService.findAll(pageId);
    }

    @RequestMapping("/api/booksTotal")
    public Integer getBooksNumber() {
        return bookService.getAllBookDtos().size();
    }

    @RequestMapping("/api/sort")
    public ResponseEntity<List<BookDto>> sortedBooks(
            @RequestParam(value = "type") String type,
            @RequestParam(value = "order") String order,
            @RequestParam(value = "pageId") int pageId) {
        FilterType filterType = FilterType.valueOf(type.toUpperCase() + "_" + order.toUpperCase());
        return ResponseEntity.ok(bookService.findAll(filterType, pageId));
    }
}
