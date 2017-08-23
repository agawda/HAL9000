package com.javaacademy.robot.converters;

import com.javaacademy.robot.model.Book;
import com.javaacademy.robot.model.BookDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookConverter implements DtoEntityConverter<Book, BookDto> {

    @Override
    public BookDto toDto(Book entity) {
        return new BookDto(entity.getName());
    }

    @Override
    public Book toEntity(BookDto dto) {
        return new Book(dto.getName());
    }

    @Override
    public List<Book> toEntities(List<BookDto> dtos) {
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<BookDto> toDtos(List<Book> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }
}
