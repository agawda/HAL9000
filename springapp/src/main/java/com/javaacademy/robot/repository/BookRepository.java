package com.javaacademy.robot.repository;

import com.javaacademy.robot.model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Component
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    @Query("select distinct b from Book b " +
            "join b.authors a " +
            "join b.categories c " +
            "where lower(b.title) like %?1% and " +
            "lower(a) like %?2% and " +
            "lower(c) like %?3% and " +
            "lower(b.shopName) like %?4% and " +
            "b.retailPriceAmount >= ?5 and " +
            "b.retailPriceAmount <= ?6")
    List<Book> findAllBy(String title, String author, String category, String shopName, double minPrice, double maxPrice);

    @Query("select distinct b from Book b " +
            "join b.authors a " +
            "join b.categories c " +
            "where lower(b.title) like %?1% or " +
            "lower(a) like %?1% or " +
            "lower(c) like %?1% or " +
            "lower(b.shopName) like %?1%")
    List<Book> findAllByQuery(String query);
}
