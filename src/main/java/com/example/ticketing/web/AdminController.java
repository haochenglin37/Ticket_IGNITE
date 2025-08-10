package com.example.ticketing.web;

import com.example.ticketing.model.Event;
import com.example.ticketing.model.Seat;
import com.example.ticketing.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    public static class CreateEventRequest {
        private String name;
        private int seatCount;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSeatCount() {
            return seatCount;
        }

        public void setSeatCount(int seatCount) {
            this.seatCount = seatCount;
        }
    }

    public static class SeatUpdateRequest {
        private boolean reserved;

        public boolean isReserved() {
            return reserved;
        }

        public void setReserved(boolean reserved) {
            this.reserved = reserved;
        }
    }

    @PostMapping("/events")
    public Event createEvent(@RequestBody CreateEventRequest request) {
        return adminService.createEvent(request.getName(), request.getSeatCount());
    }

    @GetMapping("/events/{eventId}/seats")
    public List<Seat> listSeats(@PathVariable Long eventId) {
        return adminService.listSeats(eventId);
    }

    @PutMapping("/seats/{seatId}")
    public Seat updateSeat(@PathVariable Long seatId, @RequestBody SeatUpdateRequest request) {
        return adminService.updateSeat(seatId, request.isReserved());
    }
}
