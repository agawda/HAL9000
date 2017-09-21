package com.javaacademy.robot.controllers;

import com.javaacademy.robot.model.BookDto;
import com.javaacademy.robot.service.BookSearch;
import com.javaacademy.robot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

/**
 * @author Anna Gawda
 * @since 21.08.2017
 */
@Controller
public class GreetingController {

    private static final String BOOKS_STRING = "books";
    private static final String STATIC_TEMPLATES_BOOKSTORE = "../static/templates/bookstore";
    private final BookService bookService;
    private BookSearch bookSearch;

    @Autowired
    public GreetingController(BookService bookService, BookSearch bookSearch) {
        this.bookService = bookService;
        this.bookSearch = bookSearch;
    }

    @RequestMapping("/bookstores")
    public String bookstore(Model model) {
        model.addAttribute("id", "Books");
        List<BookDto> books = bookService.getAllBookDtos();
        model.addAttribute(BOOKS_STRING, books);
        return STATIC_TEMPLATES_BOOKSTORE;
    }

    @RequestMapping("/books/")
    public String book(@RequestParam(value = "id") Long isbn, Model model) {
        model.addAttribute("id", isbn);
        BookDto bookDto = bookService.getBookByIsbn(isbn);
        setImageZoom(bookDto, 3);
        model.addAttribute("book", bookDto);
        return "../static/templates/bookview";
    }

    @PostMapping("/search")
    public String searchController(@RequestParam("content") String content, Model model) {
        List<BookDto> books = bookSearch.search(content);
        model.addAttribute(BOOKS_STRING, books);
        return STATIC_TEMPLATES_BOOKSTORE;
    }

    @RequestMapping("/sort")
    public String sortTitleController(@RequestParam(value = "sorting") String sorting, Model model) {
        List<BookDto> books = bookService.getAllBookDtos();
        if(sorting.equals("title")) {
            books.sort(Comparator.comparing(BookDto::getTitle));
        } else if(sorting.equals("regularPrice")) {
            books.sort(Comparator.comparing(BookDto::getListPriceAmount));
        } else if(sorting.equals("newPrice")) {
            books.sort(Comparator.comparing(BookDto::getRetailPriceAmount));
        }
        model.addAttribute(BOOKS_STRING, books);
        return STATIC_TEMPLATES_BOOKSTORE;
    }

    @RequestMapping("/advancedSearch")
    public String advancedSearchController() {
        return "../static/templates/advancedSearch";
    }

    @PostMapping("/advancedSearch")
    public String advancedSearchPostController(@RequestParam(value = "title") String title,
                                               @RequestParam(value = "author") String author,
                                               @RequestParam(value = "category") String category,
                                               @RequestParam(value = "minPrice") String minPrice,
                                               @RequestParam(value = "maxPrice") String maxPrice,
                                               Model model) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("title", title);
        parameters.put("author", author);
        parameters.put("category", category);

        parameters.put("minPrice", minPrice);
        if(minPrice.equals("")) {
            parameters.put("minPrice", "-1.0");
        }
        parameters.put("maxPrice", maxPrice);
        if(maxPrice.equals("")) {
            parameters.put("maxPrice", "-1.0");
        }
        Set<BookDto> books = bookSearch.advancedSearch(parameters);
        model.addAttribute("books", books);
        model.addAttribute("id", "Search results");
        return STATIC_TEMPLATES_BOOKSTORE;
    }

    void setImageZoom(BookDto bookDto, int zoom) {
        String s = bookDto.getSmallThumbnail();
        String zoomString = "&zoom=";
        if(s.contains(zoomString)) {
            String zoomValue = s.split(zoomString)[1].split("&")[0];
            String linkWithNewZoom = s.replace(zoomString + zoomValue, zoomString + zoom);
            bookDto.setSmallThumbnail(linkWithNewZoom);
        }
    }
}
