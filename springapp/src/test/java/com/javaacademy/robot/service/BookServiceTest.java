package com.javaacademy.robot.service;

import com.javaacademy.robot.model.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BookServiceTest {

    @Autowired
    BookService bookService;

    @Test
    public void testAutowiring() {
        assertNotEquals(bookService, null);
    }

    @Test
    public void testSave() {
        Book book = new Book(1L, "DummyName");
        boolean returnValue = bookService.saveBook(book);
        assertTrue(returnValue);
    }

    @Test
    public void testGetAllBooks() {
        Book book = new Book(1L, "DummyName");
        Book book1 = new Book(2L, "DummyName1");
        bookService.saveBook(book);
        bookService.saveBook(book1);
        List<Book> books = bookService.getAllBooks();
        assertEquals(2, books.size());
    }
}