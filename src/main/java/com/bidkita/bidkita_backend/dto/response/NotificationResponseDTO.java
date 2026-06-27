package com.bidkita.bidkita_backend.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDTO {
    private String notifId;
    private String message;
    private String type;
    private Boolean isRead;
    private LocalDateTime createdAt;
}