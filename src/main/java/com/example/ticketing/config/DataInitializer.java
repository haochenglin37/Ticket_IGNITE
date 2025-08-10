package com.example.ticketing.config;

import com.example.ticketing.model.Event;
import com.example.ticketing.model.Seat;
import com.example.ticketing.repository.EventRepository;
import com.example.ticketing.repository.SeatRepository;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final EventRepository eventRepository;
    private final SeatRepository seatRepository;

    public DataInitializer(EventRepository eventRepository, SeatRepository seatRepository) {
        this.eventRepository = eventRepository;
        this.seatRepository = seatRepository;
    }

    @PostConstruct
    public void init() {
        // 注意：如果你的 @Id 是 @GeneratedValue，就不要手動塞 1L 當主鍵
        Event event = new Event(1L, "Demo Event");
        eventRepository.save(event);

        long seatId = 1L;
        for (char row = 'A'; row <= 'J'; row++) {
            for (int number = 1; number <= 10; number++) {
                String seatNumber = row + String.valueOf(number);
                seatRepository.save(new Seat(seatId++, event.getId(), seatNumber, false));
            }
        }
    }
}
