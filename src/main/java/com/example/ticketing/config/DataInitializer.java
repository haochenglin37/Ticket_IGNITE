package com.example.ticketing.config;

import com.example.ticketing.model.Event;
import com.example.ticketing.model.Seat;
import com.example.ticketing.model.User;
import com.example.ticketing.repository.EventRepository;
import com.example.ticketing.repository.SeatRepository;
import com.example.ticketing.repository.UserRepository;
import javax.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final EventRepository eventRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(EventRepository eventRepository, SeatRepository seatRepository,
                           UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.eventRepository = eventRepository;
        this.seatRepository = seatRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        if (userRepository.findByUsername("admin") == null) {
            User admin = new User(userRepository.nextId(), "admin", passwordEncoder.encode("admin"), "ADMIN");
            userRepository.save(admin);
        }

        long eventId = eventRepository.nextId();
        Event event = new Event(eventId, "Demo Event");
        eventRepository.save(event);

        seatRepository.save(new Seat(seatRepository.nextId(), eventId, "A1", false));
        seatRepository.save(new Seat(seatRepository.nextId(), eventId, "A2", false));
    }
}
