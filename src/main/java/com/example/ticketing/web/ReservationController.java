package com.example.ticketing.web;

import com.example.ticketing.model.Seat;
import com.example.ticketing.model.Ticket;
import com.example.ticketing.service.ReservationService;
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
                              @RequestParam String customer) {
        return reservationService.reserveSeat(eventId, seatId, customer);
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
}
