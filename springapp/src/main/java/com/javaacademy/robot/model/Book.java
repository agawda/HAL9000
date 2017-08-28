package com.javaacademy.robot.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique=true, nullable = false)
    Long industryIdentifier;

    @Column(name = "title")
    String title;

    @Column(name = "subtitle")
    String subtitle;

    @ElementCollection
    @CollectionTable(name="Authors", joinColumns=@JoinColumn(name="id"))
    @Column(name="author")
    List<String> authors;

    @ElementCollection
    @CollectionTable(name="Categories", joinColumns=@JoinColumn(name="id"))
    @Column(name="author")
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

    public Book() {}

    public Book(long id, String title) {
        this.industryIdentifier = id;
        this.title = title;
    }

    public Book(String title) {
        this.title = title;
    }

    public String getName() {
        return title;
    }

    public void setName(String title) {
        this.title = title;
    }
}
