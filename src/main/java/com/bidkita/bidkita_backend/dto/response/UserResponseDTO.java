package com.bidkita.bidkita_backend.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserResponseDTO {
    private String userId;
    private String username;
    private String email;
    private String phoneNumber;
    private String role;
    private LocalDateTime registeredAt;
}