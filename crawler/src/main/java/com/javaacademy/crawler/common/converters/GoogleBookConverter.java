package com.javaacademy.crawler.common.converters;

import com.javaacademy.crawler.common.interfaces.Book;
import com.javaacademy.crawler.common.logger.AppLogger;
import com.javaacademy.crawler.common.model.BookModel;
import com.javaacademy.crawler.googlebooks.model.BookItem;
import com.javaacademy.crawler.googlebooks.model.Isbn;
import com.javaacademy.crawler.googlebooks.model.SaleInfo;
import com.javaacademy.crawler.googlebooks.model.VolumeInfo;

import java.util.*;
import java.util.logging.Level;

import static com.javaacademy.crawler.common.logger.AppLogger.DEFAULT_LEVEL;

public class GoogleBookConverter {

    public BookModel convertToDto(BookItem bookItem) {
        AppLogger.logger.log(DEFAULT_LEVEL, "Converting book item: " + bookItem);
        BookModel bookModel = new BookModel();
        VolumeInfo volumeInfo = bookItem.getVolumeInfo();
        bookModel.setTitle(volumeInfo.getTitle());
        bookModel.setSubtitle(volumeInfo.getSubtitle());
        bookModel.setAuthors(volumeInfo.getAuthors());
        List<Isbn> industryIdentifiers = volumeInfo.getIndustryIdentifiers();
        if (industryIdentifiers != null) {
            bookModel.setIndustryIdentifier(getIsbn(industryIdentifiers, bookModel.getTitle()));
        }
        bookModel.setCategories(volumeInfo.getCategories());
        bookModel.setSmallThumbnail(volumeInfo.getImageLinks().getSmallThumbnail());
        bookModel.setCanonicalVolumeLink(volumeInfo.getCanonicalVolumeLink());
        SaleInfo saleInfo = bookItem.getSaleInfo();
        bookModel.setSaleability(saleInfo.getSaleability());
        bookModel.setListPriceAmount(saleInfo.getListPrice().getAmount());
        bookModel.setListPriceCurrencyCode(saleInfo.getListPrice().getCurrencyCode());
        bookModel.setRetailPriceAmount(saleInfo.getRetailPrice().getAmount());
        bookModel.setRetailPriceCurrencyCode(saleInfo.getRetailPrice().getCurrencyCode());
        return bookModel;
    }

    Long getIsbn(List<Isbn> list, String title) {
        Map<String, Long> map = new HashMap<>();
        for (Isbn isbn : list) {
            try {
                String name = isbn.getType();
                Long value = Long.valueOf(isbn.getIdentifier().replaceAll("[^0-9]", ""));
                map.put(name, value);
            } catch (NumberFormatException e) {
                AppLogger.logger.log(Level.INFO, "Could not parse ISBN", e);
            }
        }
        return map.getOrDefault("ISBN_13", generateIsbn(title));
    }

    public static long generateIsbn(String title) {
        long isbn =  title.hashCode();
        if(isbn < 0) {
            return isbn * -1;
        } else {
            return isbn;
        }
    }

    public Set<BookModel> convertToDtosWithoutNulls(Set<Book> bookItems) {
        Set<BookModel> models = new HashSet<>();
        for (Book bookItem : bookItems) {
            try {
                models.add(convertToDto((BookItem) bookItem));
            } catch (Exception e) {
                AppLogger.logger.log(Level.WARNING, "Could not convert BookItem: " + bookItem, e);
            }
        }
        return models;
    }
}