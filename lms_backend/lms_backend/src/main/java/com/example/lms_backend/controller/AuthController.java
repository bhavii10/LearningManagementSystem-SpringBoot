package com.example.lms_backend.controller;

import com.example.lms_backend.dto.LoginRequest;
import com.example.lms_backend.dto.LoginResponse;
import com.example.lms_backend.dto.RegisterRequest;
import com.example.lms_backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@Valid @RequestBody RegisterRequest request) {
        LoginResponse resp = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse resp = userService.login(request);
        return ResponseEntity.ok(resp);
    }
}