package com.javaacademy.crawler.common.converters;

import com.javaacademy.crawler.common.model.BookModel;
import com.javaacademy.crawler.googlebooks.model.BookItem;
import com.javaacademy.crawler.googlebooks.model.SaleInfo;
import com.javaacademy.crawler.googlebooks.model.VolumeInfo;

import java.util.HashMap;
import java.util.Map;

public class GoogleBookConverter {

    public BookModel convertToDto(BookItem bookItem) {
        BookModel bookModel = new BookModel();
        VolumeInfo volumeInfo = bookItem.getVolumeInfo();
        bookModel.setTitle(volumeInfo.getTitle());
        bookModel.setSubtitle(volumeInfo.getSubtitle());
        bookModel.setAuthors(volumeInfo.getAuthors());
        Map<String, Integer> map = new HashMap<>();
        volumeInfo.getIndustryIdentifiers().forEach(isbn ->
                map.put(isbn.getType(), Integer.valueOf(isbn.getIdentifier())));
        bookModel.setIndustryIdentifiers(map);
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
}
