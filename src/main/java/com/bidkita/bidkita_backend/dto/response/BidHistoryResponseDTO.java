package com.bidkita.bidkita_backend.dto.response;

import java.util.Date;

public class BidHistoryResponseDTO {

    private String bidId;
    private double amount;
    private Date timestamp;
    private String auctionId;
    private String auctionTitle;
    private String auctionStatus;
    private boolean isWinning;

    public BidHistoryResponseDTO() {}

    public BidHistoryResponseDTO(String bidId, double amount, Date timestamp, String auctionId,
                                  String auctionTitle, String auctionStatus, boolean isWinning) {
        this.bidId = bidId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.auctionId = auctionId;
        this.auctionTitle = auctionTitle;
        this.auctionStatus = auctionStatus;
        this.isWinning = isWinning;
    }

    // TODO: implement after Model is complete
    // public BidHistoryResponseDTO(Bid bid, String buyerId) { ... }

    public String getBidId() { return bidId; }
    public void setBidId(String bidId) { this.bidId = bidId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }

    public String getAuctionId() { return auctionId; }
    public void setAuctionId(String auctionId) { this.auctionId = auctionId; }

    public String getAuctionTitle() { return auctionTitle; }
    public void setAuctionTitle(String auctionTitle) { this.auctionTitle = auctionTitle; }

    public String getAuctionStatus() { return auctionStatus; }
    public void setAuctionStatus(String auctionStatus) { this.auctionStatus = auctionStatus; }

    public boolean isWinning() { return isWinning; }
    public void setWinning(boolean isWinning) { this.isWinning = isWinning; }
}
