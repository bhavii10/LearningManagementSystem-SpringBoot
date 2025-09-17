package com.example.lms_backend.dto;

import com.example.lms_backend.model.Role;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class LoginResponse {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private String token;
}