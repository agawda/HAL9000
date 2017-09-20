package com.javaacademy.robot.controllers;

import com.javaacademy.robot.helpers.FilterOrder;
import com.javaacademy.robot.model.BookDto;
import com.javaacademy.robot.service.BookSearch;
import com.javaacademy.robot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Anna Gawda
 *         08.09.2017
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
        System.out.println("id = " + id);
        BookDto foundBook = bookService.getBookByIsbn(id);
        if (foundBook == null) return ResponseEntity.notFound().build();
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

    @RequestMapping("/api/advancedSearch")
    public ResponseEntity<Set<BookDto>> advancedSearchPostController(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "author") String author,
            @RequestParam(value = "category") String category,
            @RequestParam(value = "bookstore") String bookstore,
            @RequestParam(value = "minPrice") String minPrice,
            @RequestParam(value = "maxPrice") String maxPrice) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("title", title);
        parameters.put("author", author);
        parameters.put("category", category);
        parameters.put("bookstore", bookstore);

        parameters.put("minPrice", minPrice);
        if (minPrice.equals("")) {
            parameters.put("minPrice", "-1.0");
        }
        parameters.put("maxPrice", maxPrice);
        if (maxPrice.equals("")) {
            parameters.put("maxPrice", "-1.0");
        }
        Set<BookDto> books = bookSearch.advancedSearch(parameters);
        return ResponseEntity.ok(books);
    }

    @RequestMapping("/api/pages")
    public List<BookDto> getPage(@RequestParam(value = "id") int pageId) {
        return bookService.findAll(pageId);
    }

    @RequestMapping("/api/booksTotal")
    public int getBooksNumber() {
        return bookService.getAllBookDtos().size();
    }

    @RequestMapping("/api/sort")
    public ResponseEntity<List<BookDto>> sortedBooks(
            @RequestParam(value = "type") String type,
            @RequestParam(value = "order") String order,
            @RequestParam(value = "pageId") int pageId) {
        FilterOrder filterOrder = FilterOrder.valueOf(order.toUpperCase());
        return ResponseEntity.ok(bookService.findAll(filterOrder, type, pageId));
    }
}
