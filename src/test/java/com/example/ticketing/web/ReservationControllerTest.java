package com.example.ticketing.web;

import com.example.ticketing.model.Seat;
import com.example.ticketing.model.Ticket;
import com.example.ticketing.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationController.class)
@Import(com.example.ticketing.config.SecurityConfig.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    void reserveSeatReturnsTicket() throws Exception {
        Ticket ticket = new Ticket(1L, 1L, 1L, "user");
        when(reservationService.reserveSeat(1L, 1L, "user")).thenReturn(ticket);

        mockMvc.perform(post("/events/1/seats/1/reserve")
                .with(httpBasic("user", "password"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventId").value(1L))
                .andExpect(jsonPath("$.seatId").value(1L))
                .andExpect(jsonPath("$.customer").value("user"));
    }

    @Test
    void reserveSeatReturnsError() throws Exception {
        when(reservationService.reserveSeat(1L, 1L, "user"))
                .thenThrow(new IllegalStateException("Seat not available"));

        mockMvc.perform(post("/events/1/seats/1/reserve")
                .with(httpBasic("user", "password"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void getSeatReturnsSeat() throws Exception {
        Seat seat = new Seat(1L, 1L, "A1", false);
        when(reservationService.getSeat(1L, 1L)).thenReturn(seat);

        mockMvc.perform(get("/events/1/seats/1").with(httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.reserved").value(false));
    }

    @Test
    void cancelSeatReturnsSeat() throws Exception {
        Seat seat = new Seat(1L, 1L, "A1", false);
        when(reservationService.cancelSeat(1L, 1L)).thenReturn(seat);

        mockMvc.perform(post("/events/1/seats/1/cancel").with(httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reserved").value(false));
    }

    @Test
    void cancelSeatReturnsError() throws Exception {
        when(reservationService.cancelSeat(1L, 1L)).thenThrow(new IllegalStateException("Seat not reserved"));

        mockMvc.perform(post("/events/1/seats/1/cancel").with(httpBasic("user", "password")))
                .andExpect(status().isInternalServerError());
    }
}
