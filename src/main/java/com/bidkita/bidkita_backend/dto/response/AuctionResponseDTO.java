package com.bidkita.bidkita_backend.dto.response;

public class AuctionResponseDTO {

    private String auctionId;
    private String title;
    private String category;
    private String imageBase64;
    private String status;
    private double currentPrice;
    private long remainingTimeSeconds;
    private String sellerName;
    private int totalBids;
    private String highestBidderUsername;
    private Double buyNowPrice;

    public AuctionResponseDTO() {}

    public AuctionResponseDTO(String auctionId, String title, String category, String imageBase64,
                               String status, double currentPrice, long remainingTimeSeconds,
                               String sellerName, int totalBids, String highestBidderUsername,
                               Double buyNowPrice) {
        this.auctionId = auctionId;
        this.title = title;
        this.category = category;
        this.imageBase64 = imageBase64;
        this.status = status;
        this.currentPrice = currentPrice;
        this.remainingTimeSeconds = remainingTimeSeconds;
        this.sellerName = sellerName;
        this.totalBids = totalBids;
        this.highestBidderUsername = highestBidderUsername;
        this.buyNowPrice = buyNowPrice;
    }

    // TODO: implement after Model is complete
    // public AuctionResponseDTO(Auction auction) { ... }

    public String getAuctionId() { return auctionId; }
    public void setAuctionId(String auctionId) { this.auctionId = auctionId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getImageBase64() { return imageBase64; }
    public void setImageBase64(String imageBase64) { this.imageBase64 = imageBase64; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(double currentPrice) { this.currentPrice = currentPrice; }

    public long getRemainingTimeSeconds() { return remainingTimeSeconds; }
    public void setRemainingTimeSeconds(long remainingTimeSeconds) { this.remainingTimeSeconds = remainingTimeSeconds; }

    public String getSellerName() { return sellerName; }
    public void setSellerName(String sellerName) { this.sellerName = sellerName; }

    public int getTotalBids() { return totalBids; }
    public void setTotalBids(int totalBids) { this.totalBids = totalBids; }

    public String getHighestBidderUsername() { return highestBidderUsername; }
    public void setHighestBidderUsername(String highestBidderUsername) { this.highestBidderUsername = highestBidderUsername; }

    public Double getBuyNowPrice() { return buyNowPrice; }
    public void setBuyNowPrice(Double buyNowPrice) { this.buyNowPrice = buyNowPrice; }
}
