package com.javaacademy.robot.model;

public class BookDto {

    private String name;

    public BookDto() {}

    public BookDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
