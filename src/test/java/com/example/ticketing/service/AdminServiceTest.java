package com.example.ticketing.service;

import com.example.ticketing.model.Event;
import com.example.ticketing.model.Seat;
import com.example.ticketing.repository.EventRepository;
import com.example.ticketing.repository.SeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private EventRepository eventRepository;
    @Mock
    private SeatRepository seatRepository;

    private AdminService adminService;

    @BeforeEach
    void setUp() {
        adminService = new AdminService(eventRepository, seatRepository);
    }

    @Test
    void createEventGeneratesSeats() {
        when(eventRepository.nextId()).thenReturn(1L);
        when(seatRepository.nextId()).thenReturn(1L, 2L);

        Event event = adminService.createEvent("Show", 2);

        assertEquals(1L, event.getId());
        assertEquals("Show", event.getName());

        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(eventRepository).save(eventCaptor.capture());
        assertEquals(1L, eventCaptor.getValue().getId());

        verify(seatRepository, times(2)).save(any(Seat.class));
    }

    @Test
    void updateSeatChangesReservedStatus() {
        Seat seat = new Seat(1L, 1L, "A1", false);
        when(seatRepository.findById(1L)).thenReturn(seat);

        Seat result = adminService.updateSeat(1L, true);

        assertTrue(result.isReserved());
        verify(seatRepository).save(seat);
    }
}
