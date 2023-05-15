package com.example.api;

public class ExampleData {
    private int id;
    private String name;

    // Constructors, getters, and setters

    public ExampleData(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and setters for the fields

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
