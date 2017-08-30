package com.javaacademy.robot.controllers;

import com.javaacademy.robot.model.BookDto;
import com.javaacademy.robot.model.BookModels;
import com.javaacademy.robot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/books/")
public class AppRestController {

    private BookService bookService;

    public AppRestController(@Autowired BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("all")
    public ResponseEntity getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBookDtos(), HttpStatus.OK);
    }

    @RequestMapping(method = POST, value = "add")
    public ResponseEntity saveBook(@RequestBody BookDto bookDto) {
        bookService.saveBook(bookDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping("{isbn}")
    public ResponseEntity getBookByIsbn(@PathVariable Long isbn) {
        return new ResponseEntity<>(bookService.getBookByIsbn(isbn), HttpStatus.OK);
    }

    @RequestMapping(method = DELETE, value = "{isbn}")
    public ResponseEntity removeBookByIsbn(@PathVariable Long isbn) {
        try {
            bookService.remove(isbn);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = PUT, value = "addall")
    public void addAllBookDtos(@RequestBody BookModels bookModels) {
        bookService.addAllBookDtos(bookModels);
    }
}
