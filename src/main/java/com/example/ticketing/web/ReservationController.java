package com.example.ticketing.web;

import com.example.ticketing.model.Seat;
import com.example.ticketing.model.Ticket;
import com.example.ticketing.service.ReservationService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/events/{eventId}/seats/{seatId}/reserve")
    public Ticket reserveSeat(@PathVariable Long eventId,
                              @PathVariable Long seatId,
                              Authentication authentication) {
        return reservationService.reserveSeat(eventId, seatId, authentication.getName());
    }

    @GetMapping("/events/{eventId}/seats/{seatId}")
    public Seat getSeat(@PathVariable Long eventId,
                        @PathVariable Long seatId) {
        return reservationService.getSeat(eventId, seatId);
    }

    @PostMapping("/events/{eventId}/seats/{seatId}/cancel")
    public Seat cancelSeat(@PathVariable Long eventId,
                           @PathVariable Long seatId) {
        return reservationService.cancelSeat(eventId, seatId);
    }

    @GetMapping("/me/tickets")
    public java.util.List<Ticket> myTickets(Authentication authentication) {
        return reservationService.getTicketsForCustomer(authentication.getName());
    }

    @DeleteMapping("/me/tickets/{ticketId}")
    public void cancelMyTicket(@PathVariable Long ticketId, Authentication authentication) {
        reservationService.cancelTicket(ticketId, authentication.getName());
    }
}
