package com.javaacademy.crawler.googlebooks.model;

/**
 * @author devas
 * @since 24.08.17
 */
public class Isbn {

    private String type;
    private String identifier;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return "Isbn{" +
                "type='" + type + '\'' +
                ", identifier='" + identifier + '\'' +
                '}';
    }
}
