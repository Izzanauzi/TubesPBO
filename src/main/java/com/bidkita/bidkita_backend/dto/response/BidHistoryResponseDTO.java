package com.bidkita.bidkita_backend.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidHistoryResponseDTO {
    private String bidId;
    private Double amount;
    private LocalDateTime timestamp;
    private String auctionId;
    private String auctionTitle;
    private String auctionStatus;
    private Boolean isWinning;
}