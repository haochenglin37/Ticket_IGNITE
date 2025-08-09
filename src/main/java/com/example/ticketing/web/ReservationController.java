package com.example.ticketing.web;

import com.example.ticketing.model.Ticket;
import com.example.ticketing.service.ReservationService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
