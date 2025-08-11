package com.example.ticketing.web;

import com.example.ticketing.model.User;
import com.example.ticketing.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        if (userRepository.findByUsername(username) != null) {
            return ResponseEntity.badRequest().body("User exists");
        }
        User user = new User(userRepository.nextId(), username, passwordEncoder.encode(password), "USER");
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public Map<String, String> me(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        return Map.of("username", user.getUsername(), "role", user.getRole());
    }
}
