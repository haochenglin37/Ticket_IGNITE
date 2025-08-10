package com.example.ticketing;

import com.example.ticketing.model.Seat;
import com.example.ticketing.repository.SeatRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DataInitializerTest {

    @Autowired
    private SeatRepository seatRepository;

    @Test
    void seededSeatIsAvailable() {
        Seat seat = seatRepository.findById(1L);
        assertNotNull(seat);
        assertEquals(1L, seat.getEventId());
        assertEquals("A1", seat.getNumber());
    }
}
