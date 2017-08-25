package com.javaacademy.crawler.googlebooks.model;

import lombok.Data;

import java.util.List;

/**
 * @author devas
 * @since 24.08.17
 */
@Data
public class VolumeInfo {
    private String title;
    private String subtitle;
    private List<String> authors;
    private List<Isbn> industryIdentifiers;
    private List<String> categories; // genre
    private ImageLinks imageLinks;
    private String canonicalVolumeLink;
}
