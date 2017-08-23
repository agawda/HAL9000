package com.javaacademy.robot.controllers;

import com.javaacademy.robot.model.BookDto;
import com.javaacademy.robot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/books/")
public class AppRestController {

    private BookService bookService;

    public AppRestController(@Autowired BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("all")
    public List<BookDto> getAllBooks() {
        return bookService.getAllBookDtos();
    }

    @RequestMapping(method = POST, value = "add")
    public void saveBook(@RequestBody BookDto bookDto) {
        bookService.saveBook(bookDto);
    }
}
