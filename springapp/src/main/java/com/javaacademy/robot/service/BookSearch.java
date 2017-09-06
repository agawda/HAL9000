package com.javaacademy.robot.service;

import com.javaacademy.robot.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Anna Gawda
 * 05.09.2017
 */
@Service
public class BookSearch {

    private BookService bookService;

    public BookSearch(@Autowired BookService bookService) {
        this.bookService = bookService;
    }

    public List<Book> search(String givenTitle) {
        List<Book> result = new ArrayList<>();
        List<Book> allBooks = bookService.getAllBooks();
        allBooks.forEach(book -> {
            boolean isAdded = false;
            if(book.getTitle().toLowerCase().contains(givenTitle.toLowerCase())) {
                result.add(book);
                isAdded = true;
            }

            if(!isAdded) book.getAuthors().forEach(author -> {
                if(author.toLowerCase().contains(givenTitle.toLowerCase())) {
                    result.add(book);
                }
            });

            if(!isAdded) book.getCategories().forEach(category -> {
                if(category.toLowerCase().contains(givenTitle.toLowerCase())) {
                    result.add(book);
                }
            });
        });
        return result;
    }

    public Set<Book> advancedSearch(Map<String, String> parameters) {
        Set<Book> result = new HashSet<>();
        List<Book> allBooks = bookService.getAllBooks();
        String givenTitle = parameters.getOrDefault("title", "");
        String givenAuthor = parameters.getOrDefault("author", "");
        String givenCategory = parameters.getOrDefault("category", "");
        Double maxPrice = Double.valueOf(parameters.get("maxPrice"));
        Double minPrice = Double.valueOf(parameters.get("minPrice"));
        allBooks.forEach(book -> {
            if(!givenTitle.equals("") && book.getTitle().toLowerCase().contains(givenTitle.toLowerCase())) {
                result.add(book);
            }

            if(!givenAuthor.equals("")) book.getAuthors().forEach(author -> {
                if(author.toLowerCase().contains(givenAuthor.toLowerCase())) {
                    result.add(book);
                }
            });

            if(!givenCategory.equals("")) book.getCategories().forEach(category -> {
                if(category.toLowerCase().contains(givenCategory.toLowerCase())) {
                    result.add(book);
                }
            });

            if(maxPrice != -1 && minPrice != -1) {
                if(book.getRetailPriceAmount() <= maxPrice && book.getRetailPriceAmount() >= minPrice) {
                    result.add(book);
                }
            } else {
                if (book.getRetailPriceAmount() <= maxPrice) {
                    result.add(book);
                }

                if (minPrice != -1 && book.getRetailPriceAmount() >= minPrice) {
                    result.add(book);
                }
            }
        });
        return result;
    }
}
