package com.javaacademy.crawler.googlebooks.model;

import java.util.List;

/**
 * @author devas
 * @since 24.08.17
 */
public class VolumeInfo {

    private String title;
    private String subtitle;
    private List<String> authors;
    private List<Isbn> industryIdentifiers;
    private List<String> categories; // genre
    private ImageLinks imageLinks;
    private String canonicalVolumeLink;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public List<Isbn> getIndustryIdentifiers() {
        return industryIdentifiers;
    }

    public void setIndustryIdentifiers(List<Isbn> industryIdentifiers) {
        this.industryIdentifiers = industryIdentifiers;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public ImageLinks getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(ImageLinks imageLinks) {
        this.imageLinks = imageLinks;
    }

    public String getCanonicalVolumeLink() {
        return canonicalVolumeLink;
    }

    public void setCanonicalVolumeLink(String canonicalVolumeLink) {
        this.canonicalVolumeLink = canonicalVolumeLink;
    }

    @Override
    public String toString() {
        return "VolumeInfo{" +
                "title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", authors=" + authors +
                ", industryIdentifiers=" + industryIdentifiers +
                ", categories=" + categories +
                ", imageLinks=" + imageLinks +
                ", canonicalVolumeLink='" + canonicalVolumeLink + '\'' +
                '}';
    }
}
