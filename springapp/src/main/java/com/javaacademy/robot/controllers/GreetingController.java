package com.javaacademy.robot.controllers;

import com.javaacademy.robot.model.BookDto;
import com.javaacademy.robot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

/**
 * @author Anna Gawda
 * @since 21.08.2017
 */
@Controller
public class GreetingController {

    private final BookService bookService;

    @Autowired
    public GreetingController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("/greeting")
    public String hello(@RequestParam(value = "name", required = false, defaultValue = "World")
                                String name, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("exampleBooks", Arrays.asList("Advanced Java", "Clean Code", "Effective Java"));
        return "../static/templates/greeting";
    }

    @RequestMapping("/bookstores")
    public String bookstore(@RequestParam(value = "id") String bookstore, Model model) {
        model.addAttribute("id", bookstore);
        List<BookDto> books = bookService.getAllBookDtos();
        model.addAttribute("books", books);
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
