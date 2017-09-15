package com.javaacademy.robot.service;

import com.javaacademy.robot.model.Book;
import com.javaacademy.robot.model.BookDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BookServiceTestIT {

    @Autowired
    BookService bookService;

    @Test
    public void testAutowiring() {
        assertNotNull(bookService);
    }

    @Test
    public void testGetAllBooks() {
        BookDto book = new BookDto();
        book.setIndustryIdentifier(1L);
        book.setTitle("DummyName");
        BookDto book1 = new BookDto();
        book1.setIndustryIdentifier(2L);
        book1.setTitle("DummyName1");
        bookService.saveBook(book);
        bookService.saveBook(book1);
        List<Book> books = bookService.getAllBooks();
        assertEquals(2, books.size());
    }
}