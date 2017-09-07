package com.javaacademy.robot.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * An entity which is being stored in a database
 */

@Entity
@Table(name = "books")
@Getter
@Setter
@EqualsAndHashCode(exclude = {"shopName", "dateAdded"})
@ToString
public class Book {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    Long industryIdentifier;

    @Column(name = "title")
    String title;

    @Column(name = "subtitle", length = 512)
    String subtitle;

    @ElementCollection
    @CollectionTable(name = "Authors", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "author")
    List<String> authors;

    @ElementCollection
    @CollectionTable(name = "Categories", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "author")
    List<String> categories; // genre

    @Column(name = "thumbnail")
    String smallThumbnail;

    @Column(name = "link")
    String canonicalVolumeLink;

    @Column(name = "saleability")
    String saleability;

    @Column(name = "listPriceAmount")
    double listPriceAmount;

    @Column(name = "listPriceCurrencyCode")
    String listPriceCurrencyCode;

    @Column(name = "retailPriceAmount")
    double retailPriceAmount;

    @Column(name = "retailPriceCurrencyCode")
    String retailPriceCurrencyCode;

    @Column(name = "shopName")
    String shopName;

    @Column(name = "dateAdded")
    LocalDateTime dateAdded;

    public Book() {
        //required for ORM
    }
}
