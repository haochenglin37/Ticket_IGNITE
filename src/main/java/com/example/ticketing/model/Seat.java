package com.example.ticketing.model;

import java.io.Serializable;

public class Seat implements Serializable {
    private Long id;
    private Long eventId;
    private String number;
    private boolean reserved;

    public Seat() {
    }

    public Seat(Long id, Long eventId, String number, boolean reserved) {
        this.id = id;
        this.eventId = eventId;
        this.number = number;
        this.reserved = reserved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }
}
