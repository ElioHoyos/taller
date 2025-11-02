package com.example.demo.controller;

import com.example.demo.security.JwtService;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository repo;
    private final JwtService jwt;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        User u = repo.findByLogin(req.getLogin())
                .orElse(null);
        if (u == null || u.getKey() == null || !encoder.matches(req.getPassword(), u.getKey())) {
            return ResponseEntity.status(401).body(new Msg("Credenciales inválidas"));
        }
        String token = jwt.generate(u.getLogin());
        return ResponseEntity.ok(new LoginResponse(token, u.getId(), u.getName()));
    }

    @Data static class LoginRequest { private String login; private String password; }

    @Data static class LoginResponse {
        private final String token;
        private final Long userId;
        private final String name;
    }

    @Data static class Msg { private final String message; }
}
