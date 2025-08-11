package com.example.ticketing.web;

import com.example.ticketing.model.User;
import com.example.ticketing.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(com.example.ticketing.config.SecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    void registerSuccess() throws Exception {
        when(userRepository.findByUsername("bob")).thenReturn(null);
        when(userRepository.nextId()).thenReturn(1L);

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"bob\",\"password\":\"pw\"}"))
                .andExpect(status().isOk());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerDuplicate() throws Exception {
        when(userRepository.findByUsername("bob")).thenReturn(new User());

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"bob\",\"password\":\"pw\"}"))
                .andExpect(status().isBadRequest());
    }
}
