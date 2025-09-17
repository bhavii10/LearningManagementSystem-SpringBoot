





package com.example.lms_backend.service;

import com.example.lms_backend.dto.*;
import com.example.lms_backend.exception.ApiException;
import com.example.lms_backend.model.Role;
import com.example.lms_backend.model.User;
import com.example.lms_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public LoginResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            log.warn("Email already registered: {}", req.getEmail());
            throw new ApiException("Email already registered");
        }
        Role role = req.getRole() == null ? Role.USER : req.getRole();
        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .passwordHash(encoder.encode(req.getPassword()))
                .role(role)
                .build();
        userRepository.save(user);
        log.info("Registered: {}", user.getEmail());

        // JWT hataya, sirf user details return kar rahe
        return new LoginResponse(user.getId(), user.getName(), user.getEmail(), user.getRole(), null);
    }

    public LoginResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> {
                    log.warn("Email not found: {}", req.getEmail());
                    return new ApiException("Invalid email or password");
                });
        if (!encoder.matches(req.getPassword(), user.getPasswordHash())) {
            log.warn("Password mismatch: {}", req.getEmail());
            throw new ApiException("Invalid email or password");
        }
        log.info("Logged in: {}", user.getEmail());

        // JWT hataya, sirf user details return kar rahe
        return new LoginResponse(user.getId(), user.getName(), user.getEmail(), user.getRole(), null);
    }
}
