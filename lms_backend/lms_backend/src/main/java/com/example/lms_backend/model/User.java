


package com.example.lms_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "users") // avoid reserved word "user" in MySQL
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @Email
    @NotBlank(message = "Email is required")
    @Column(nullable = false, unique = true)
    private String email;

    // Password can be null for OAuth2 users
    @Column(nullable = true)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }

    /**
     * Helper method: Checks if password is required
     * for users registered via form (non-OAuth2)
     */
    public boolean isPasswordRequired() {
        // Password required only if role is USER and passwordHash is null or blank
        return role == Role.USER && (passwordHash == null || passwordHash.trim().isEmpty());
    }
}
