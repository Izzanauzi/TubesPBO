package com.bidkita.bidkita_backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopUpRequestDTO {
    private Double amount;
    private String paymentMethod;
}