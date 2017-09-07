package com.javaacademy.robot.converters;

import com.javaacademy.robot.model.Book;
import com.javaacademy.robot.model.BookDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        bookDto.setRetailPriceCurrencyCode(entity.getRetailPriceCurrencyCode());
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
        book.setRetailPriceCurrencyCode(dto.getRetailPriceCurrencyCode());
        book.setDateAdded(LocalDateTime.now());
        book.setShoName(recognizeShopName(dto));
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

    private String recognizeShopName(BookDto bookDto) {
        String link = bookDto.getCanonicalVolumeLink();
        if(link.contains("//")) {
            String shopLinkFirstPart = bookDto.getCanonicalVolumeLink().split("//")[1];
            String shopAddress = shopLinkFirstPart.split("/")[0];
            return Shop.getStoreName(shopAddress).toString();
        }
        return "";
    }

    enum Shop {
        GOOGLE_STORE("market.android.com"),
        GANDALF("www.gandalf.com.pl"),
        BONITO("bonito.pl"),
        CZYTAM("www.czytam.pl"),
        MATRAS("www.matras.pl"),
        UNKNOWN("");
        private static final Map<String, Shop> map = new HashMap<>(values().length, 1);

        static {
            for (Shop c : values()) map.put(c.storeAddress, c);
        }

        private String storeAddress;

        Shop(String storeAddress) {
            this.storeAddress = storeAddress;
        }

        static Shop getStoreName(String string) {
            return map.getOrDefault(string, UNKNOWN);
        }
    }

}
