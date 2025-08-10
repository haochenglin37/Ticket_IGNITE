package com.example.ticketing.service;

import com.example.ticketing.model.Seat;
import com.example.ticketing.model.Ticket;
import com.example.ticketing.repository.SeatRepository;
import com.example.ticketing.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private SeatRepository seatRepository;
    @Mock
    private TicketRepository ticketRepository;

    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationService = new ReservationService(seatRepository, ticketRepository);
    }

    @Test
    void reserveSeatSuccessful() {
        Seat seat = new Seat(1L, 1L, "A1", false);
        when(seatRepository.findById(1L)).thenReturn(seat);

        Ticket ticket = reservationService.reserveSeat(1L, 1L, "Alice");

        assertNotNull(ticket.getId());
        assertEquals(1L, ticket.getEventId());
        assertEquals(1L, ticket.getSeatId());
        assertEquals("Alice", ticket.getCustomer());

        ArgumentCaptor<Seat> seatCaptor = ArgumentCaptor.forClass(Seat.class);
        verify(seatRepository).save(seatCaptor.capture());
        assertTrue(seatCaptor.getValue().isReserved());

        verify(ticketRepository).save(ticket);
    }

    @Test
    void reserveAlreadyReservedSeatThrows() {
        Seat seat = new Seat(1L, 1L, "A1", true);
        when(seatRepository.findById(1L)).thenReturn(seat);

        assertThrows(IllegalStateException.class, () -> reservationService.reserveSeat(1L, 1L, "Alice"));

        verify(seatRepository, never()).save(any());
        verify(ticketRepository, never()).save(any());
    }

    @Test
    void reserveSeatWithWrongEventIdThrows() {
        Seat seat = new Seat(1L, 2L, "A1", false);
        when(seatRepository.findById(1L)).thenReturn(seat);

        assertThrows(IllegalStateException.class, () -> reservationService.reserveSeat(1L, 1L, "Alice"));

        verify(seatRepository, never()).save(any());
        verify(ticketRepository, never()).save(any());
    }

    @Test
    void cancelSeatSuccessful() {
        Seat seat = new Seat(1L, 1L, "A1", true);
        Ticket ticket = new Ticket(5L, 1L, 1L, "Alice");
        when(seatRepository.findById(1L)).thenReturn(seat);
        when(ticketRepository.findByEventIdAndSeatId(1L, 1L)).thenReturn(ticket);

        Seat result = reservationService.cancelSeat(1L, 1L);

        assertFalse(result.isReserved());
        verify(seatRepository).save(seat);
        verify(ticketRepository).delete(5L);
    }

    @Test
    void cancelSeatNotReservedThrows() {
        Seat seat = new Seat(1L, 1L, "A1", false);
        when(seatRepository.findById(1L)).thenReturn(seat);

        assertThrows(IllegalStateException.class, () -> reservationService.cancelSeat(1L, 1L));

        verify(seatRepository, never()).save(any());
        verify(ticketRepository, never()).delete(anyLong());
    }

    @Test
    void getSeatsByEventReturnsSeats() {
        List<Seat> seats = Arrays.asList(new Seat(1L, 1L, "A1", false),
                                         new Seat(2L, 1L, "A2", true));
        when(seatRepository.findByEventId(1L)).thenReturn(seats);

        List<Seat> result = reservationService.getSeatsByEvent(1L);

        assertEquals(seats, result);
    }
}
