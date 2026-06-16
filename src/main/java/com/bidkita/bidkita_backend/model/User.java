package com.bidkita.bidkita_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public abstract class User {

    @Id
    @Column(name = "user_id")
    protected String userId = UUID.randomUUID().toString();

    @Column(unique = true, nullable = false)
    protected String username;

    @Column(unique = true, nullable = false)
    protected String email;

    @Column(nullable = false)
    protected String password;

    @Column(name = "phone_number")
    protected String phoneNumber;

    @Column(name = "registered_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date registeredAt = new Date();

    public User() {}

    public abstract String getRole();

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.trim().length() < 3) {
            throw new IllegalArgumentException("Username minimal 3 karakter");
        }
        this.username = username.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email tidak valid");
        }
        this.email = email.trim().toLowerCase();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Nomor telepon wajib diisi");
        }
        String normalizedPhone = phoneNumber.trim();
        if (!normalizedPhone.startsWith("08") && !normalizedPhone.startsWith("+62")) {
            throw new IllegalArgumentException("Nomor telepon harus diawali 08 atau +62");
        }
        this.phoneNumber = normalizedPhone;
    }

    public Date getRegisteredAt() {
        return registeredAt;
    }
}
