package com.javaacademy.robot.repository;

import com.javaacademy.robot.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public interface BookRepository extends CrudRepository<Book, Long> {
}
