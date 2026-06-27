package com.bidkita.bidkita_backend.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletTransactionResponseDTO {
    private Long id;
    private Double amount;
    private String type;
    private LocalDateTime createdAt;
}
