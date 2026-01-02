package com.backend.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Year;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(
    name = "users",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
    }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    // Nullable for Google login
    @Column(nullable = true)
    private Long number;

    // Nullable for Google login
    @Column(nullable = true)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false, updatable = false)
    private Integer createdYear;

    @Column(nullable = false)
    private String loginprovider;

    /* ---------------- LIFECYCLE HOOKS ---------------- */

    @PrePersist
    public void prePersist() {
        this.createdYear = Year.now().getValue();
        normalizeEmail();
    }

    @PreUpdate
    public void preUpdate() {
        normalizeEmail();
    }

    private void normalizeEmail() {
        if (this.email != null) {
            this.email = this.email.trim().toLowerCase();
        }
    }
}
