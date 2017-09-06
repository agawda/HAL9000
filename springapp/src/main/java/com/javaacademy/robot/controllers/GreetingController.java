package com.javaacademy.robot.controllers;

import com.javaacademy.robot.model.Book;
import com.javaacademy.robot.model.BookDto;
import com.javaacademy.robot.service.BookSearch;
import com.javaacademy.robot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Anna Gawda
 * @since 21.08.2017
 */
@Controller
public class GreetingController {
    private static final String BOOKS_STRING = "books";

    private final BookService bookService;
    private BookSearch bookSearch;

    @Autowired
    public GreetingController(BookService bookService, BookSearch bookSearch) {
        this.bookService = bookService;
        this.bookSearch = bookSearch;
    }

    @RequestMapping("/greeting")
    public String hello(@RequestParam(value = "name", required = false, defaultValue = "World")
                                String name, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("exampleBooks", Arrays.asList("Advanced Java", "Clean Code", "Effective Java"));
        return "../static/templates/greeting";
    }

    @RequestMapping("/bookstores")
    public String bookstore(Model model) {
        List<BookDto> books = bookService.getAllBookDtos();
        model.addAttribute(BOOKS_STRING, books);
        return "../static/templates/bookstore";
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
        List<Book> books = bookSearch.search(content);
        model.addAttribute(BOOKS_STRING, books);
        return "../static/templates/bookstore";
    }

    @RequestMapping("/sort")
    public String sortTitleController(@RequestParam(value = "sorting") String sorting, Model model) {
        List<BookDto> books = bookService.getAllBookDtos();
        if(sorting.equals("title")) {
            Collections.sort(books, Comparator.comparing(BookDto::getTitle));
        } else if(sorting.equals("regularPrice")) {
            Collections.sort(books, Comparator.comparing(BookDto::getListPriceAmount));
        } else if(sorting.equals("newPrice")) {
            Collections.sort(books, Comparator.comparing(BookDto::getRetailPriceAmount));
        }
        model.addAttribute(BOOKS_STRING, books);
        return "../static/templates/bookstore";
    }

    private void setImageZoom(BookDto bookDto, int zoom) {
        String s = bookDto.getSmallThumbnail();
        String zoomString = "&zoom=";
        if(s.contains(zoomString)) {
            String zoomValue = s.split(zoomString)[1].split("&")[0];
            String linkWithNewZoom = s.replace(zoomString + zoomValue, zoomString + zoom);
            bookDto.setSmallThumbnail(linkWithNewZoom);
        }
    }
}
