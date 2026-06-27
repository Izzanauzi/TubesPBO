package com.bidkita.bidkita_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuctionResponseDTO {
    private String auctionId;
    private String title;
    private String category;
    private String imageBase64;
    private String status;
    private Double currentPrice;
    private Long remainingTimeSeconds;
    private String sellerName;
    private Integer totalBids;
    private String highestBidderUsername;
    private Double buyNowPrice;
}