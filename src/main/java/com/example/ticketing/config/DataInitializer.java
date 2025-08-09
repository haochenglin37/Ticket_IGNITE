package com.example.ticketing.config;

import com.example.ticketing.model.Event;
import com.example.ticketing.model.Seat;
import com.example.ticketing.repository.EventRepository;
import com.example.ticketing.repository.SeatRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final EventRepository eventRepository;
    private final SeatRepository seatRepository;

    public DataInitializer(EventRepository eventRepository, SeatRepository seatRepository) {
        this.eventRepository = eventRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    public void run(String... args) {
        Event event = new Event(1L, "Sample Event");
        eventRepository.save(event);

        Seat seat = new Seat(1L, event.getId(), "A1", false);
        seatRepository.save(seat);

        // Verify seeding by fetching the seat
        Seat fetched = seatRepository.findById(seat.getId());
        if (fetched != null) {
            System.out.println("Seeded seat: " + fetched.getNumber() + " for event " + fetched.getEventId());
        }
    }
}
