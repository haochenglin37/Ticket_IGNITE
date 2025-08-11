package com.example.ticketing.service;

import com.example.ticketing.model.Event;
import com.example.ticketing.model.Seat;
import com.example.ticketing.repository.EventRepository;
import com.example.ticketing.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final EventRepository eventRepository;
    private final SeatRepository seatRepository;

    public AdminService(EventRepository eventRepository, SeatRepository seatRepository) {
        this.eventRepository = eventRepository;
        this.seatRepository = seatRepository;
    }

    public Event createEvent(String name, int seatCount) {
        long eventId = eventRepository.nextId();
        Event event = new Event(eventId, name);
        eventRepository.save(event);
        for (int i = 1; i <= seatCount; i++) {
            long seatId = seatRepository.nextId();
            String number = "A" + i;
            seatRepository.save(new Seat(seatId, eventId, number, false));
        }
        return event;
    }

    public List<Seat> listSeats(Long eventId) {
        return seatRepository.findByEventId(eventId);
    }

    public Seat updateSeat(Long seatId, boolean reserved) {
        Seat seat = seatRepository.findById(seatId);
        if (seat != null) {
            seat.setReserved(reserved);
            seatRepository.save(seat);
        }
        return seat;
    }
}
