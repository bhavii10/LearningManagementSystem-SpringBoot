package com.example.lms_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class LoginRequest {
    @Email @NotBlank
    private String email;

    @NotBlank
    private String password;
}