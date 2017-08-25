package com.javaacademy.crawler.googlebooks.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * @author devas
 * @since 24.08.17
 */
@EqualsAndHashCode
@ToString
@Getter
public class VolumeInfo {
    String title;
    String subtitle;
    List<String> authors;
    List<Isbn> industryIdentifiers;
    List<String> categories; // genre
    ImageLinks imageLinks;
    String canonicalVolumeLink;
}
