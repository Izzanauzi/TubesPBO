package com.bidkita.bidkita_backend.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletResponseDTO {
    private String walletId;
    private Double balance;
    private List<WalletTransactionResponseDTO> transactions;
}