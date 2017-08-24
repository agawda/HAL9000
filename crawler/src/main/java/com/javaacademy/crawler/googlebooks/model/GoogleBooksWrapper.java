package com.javaacademy.crawler.googlebooks.model;

import java.util.List;

/**
 * @author devas
 * @since 24.08.17
 */
public class GoogleBooksWrapper {

    private int totalItems;
    private List<BookItem> items;

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public List<BookItem> getItems() {
        return items;
    }

    public void setItems(List<BookItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        String message = "GoogleBooksWrapper\n" +
                "totalItems=" + totalItems +
                ", items=\n";
        for (BookItem item : items) {
            message += item + "\n";
        }
        return message;
    }
}
