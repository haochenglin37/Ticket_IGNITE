package com.example.ticketing.service;

import com.example.ticketing.model.Seat;
import com.example.ticketing.model.Ticket;
import com.example.ticketing.repository.SeatRepository;
import com.example.ticketing.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReservationService {

    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;

    public ReservationService(SeatRepository seatRepository, TicketRepository ticketRepository) {
        this.seatRepository = seatRepository;
        this.ticketRepository = ticketRepository;
    }

    public Ticket reserveSeat(Long eventId, Long seatId, String customer) {
        Seat seat = seatRepository.findById(seatId);
        if (seat == null || !seat.getEventId().equals(eventId) || seat.isReserved()) {
            throw new IllegalStateException("Seat not available");
        }
        seat.setReserved(true);
        seatRepository.save(seat);

        Ticket ticket = new Ticket();
        ticket.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        ticket.setEventId(eventId);
        ticket.setSeatId(seatId);
        ticket.setCustomer(customer);
        ticketRepository.save(ticket);
        return ticket;
    }

    public Seat getSeat(Long eventId, Long seatId) {
        Seat seat = seatRepository.findById(seatId);
        if (seat == null || !seat.getEventId().equals(eventId)) {
            throw new IllegalStateException("Seat not found");
        }
        return seat;
    }

    public Seat cancelSeat(Long eventId, Long seatId) {
        Seat seat = seatRepository.findById(seatId);
        if (seat == null || !seat.getEventId().equals(eventId) || !seat.isReserved()) {
            throw new IllegalStateException("Seat not reserved");
        }
        seat.setReserved(false);
        seatRepository.save(seat);
        Ticket ticket = ticketRepository.findByEventIdAndSeatId(eventId, seatId);
        if (ticket != null) {
            ticketRepository.delete(ticket.getId());
        }
        return seat;
    }

    public List<Ticket> getTicketsForCustomer(String customer) {
        return ticketRepository.findByCustomer(customer);
    }

    public void cancelTicket(Long ticketId, String customer) {
        Ticket ticket = ticketRepository.findById(ticketId);
        if (ticket == null || !customer.equals(ticket.getCustomer())) {
            throw new IllegalStateException("Ticket not found");
        }
        Seat seat = seatRepository.findById(ticket.getSeatId());
        if (seat != null) {
            seat.setReserved(false);
            seatRepository.save(seat);
        }
        ticketRepository.delete(ticketId);
    }
}
