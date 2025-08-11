package com.example.ticketing.web;

import com.example.ticketing.model.Event;
import com.example.ticketing.model.Seat;
import com.example.ticketing.model.User;
import com.example.ticketing.repository.UserRepository;
import com.example.ticketing.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
@Import(com.example.ticketing.config.SecurityConfig.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setupUsers() {
        when(userRepository.findByUsername("admin")).thenReturn(new User(1L, "admin", passwordEncoder.encode("admin"), "ADMIN"));
        when(userRepository.findByUsername("user")).thenReturn(new User(2L, "user", passwordEncoder.encode("password"), "USER"));
    }

    @Test
    void createEventReturnsEvent() throws Exception {
        Event event = new Event(1L, "Show");
        when(adminService.createEvent("Show", 2)).thenReturn(event);

        mockMvc.perform(post("/admin/events")
                .with(httpBasic("admin", "admin"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Show\",\"seatCount\":2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Show"));
    }

    @Test
    void createEventRequiresAdmin() throws Exception {
        mockMvc.perform(post("/admin/events")
                .with(httpBasic("user", "password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Show\",\"seatCount\":2}"))
                .andExpect(status().isForbidden());
    }

    @Test
    void updateSeatReturnsSeat() throws Exception {
        Seat seat = new Seat(1L, 1L, "A1", true);
        when(adminService.updateSeat(1L, true)).thenReturn(seat);

        mockMvc.perform(put("/admin/seats/1")
                .with(httpBasic("admin", "admin"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"reserved\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reserved").value(true));
    }
}
