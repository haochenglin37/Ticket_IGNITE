package com.example.ticketing.model;

import java.io.Serializable;

public class Ticket implements Serializable {
    private Long id;
    private Long eventId;
    private Long seatId;
    private String customer;

    public Ticket() {
    }

    public Ticket(Long id, Long eventId, Long seatId, String customer) {
        this.id = id;
        this.eventId = eventId;
        this.seatId = seatId;
        this.customer = customer;
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

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
