package com.javaacademy.robot.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class BookDto {

    Long industryIdentifier;
    String title;
    String subtitle;
    List<String> authors;
    List<String> categories;
    String smallThumbnail;
    String canonicalVolumeLink;
    String saleability;
    double listPriceAmount;
    String listPriceCurrencyCode;
    double retailPriceAmount;
    byte discount;
    String retailPriceCurrencyCode;
    String shopName;
    LocalDateTime dateAdded;
}
