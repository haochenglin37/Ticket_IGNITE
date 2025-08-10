package com.example.ticketing.model;

import java.io.Serializable;

public class Event implements Serializable {
    private Long id;
    private String name;

    public Event() {
    }

    public Event(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
