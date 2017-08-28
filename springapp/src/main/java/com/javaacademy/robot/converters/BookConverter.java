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
        BookDto bookDto = new BookDto();
        bookDto.setIndustryIdentifier(entity.getIndustryIdentifier());
        bookDto.setTitle(entity.getTitle());
        bookDto.setSubtitle(entity.getSubtitle());
        bookDto.setAuthors(entity.getAuthors());
        bookDto.setCategories(entity.getCategories());
        bookDto.setSmallThumbnail(entity.getSmallThumbnail());
        bookDto.setCanonicalVolumeLink(entity.getCanonicalVolumeLink());
        bookDto.setSaleability(entity.getSaleability());
        bookDto.setListPriceAmount(entity.getListPriceAmount());
        bookDto.setListPriceCurrencyCode(entity.getListPriceCurrencyCode());
        bookDto.setRetailPriceAmount(entity.getRetailPriceAmount());
        bookDto.setListPriceCurrencyCode(entity.getRetailPriceCurrencyCode());
        return bookDto;
    }

    @Override
    public Book toEntity(BookDto dto) {
        Book book = new Book();
        book.setIndustryIdentifier(dto.getIndustryIdentifier());
        book.setTitle(dto.getTitle());
        book.setSubtitle(dto.getSubtitle());
        book.setAuthors(dto.getAuthors());
        book.setCategories(dto.getCategories());
        book.setSmallThumbnail(dto.getSmallThumbnail());
        book.setCanonicalVolumeLink(dto.getCanonicalVolumeLink());
        book.setSaleability(dto.getSaleability());
        book.setListPriceAmount(dto.getListPriceAmount());
        book.setListPriceCurrencyCode(dto.getListPriceCurrencyCode());
        book.setRetailPriceAmount(dto.getRetailPriceAmount());
        book.setListPriceCurrencyCode(dto.getRetailPriceCurrencyCode());
        return book;
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
