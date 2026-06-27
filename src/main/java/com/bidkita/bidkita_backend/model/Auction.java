package com.bidkita.bidkita_backend.model;

import com.bidkita.bidkita_backend.exception.AuctionNotOpenException;
import com.bidkita.bidkita_backend.exception.BidTooLowException;
import com.bidkita.bidkita_backend.exception.InsufficientBalanceException;
import com.bidkita.bidkita_backend.model.enums.AuctionStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "auctions")
public class Auction implements Biddable {

    @Id
    @Column(name = "auction_id")
    private String auctionId = UUID.randomUUID().toString();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @Column(name = "current_price", nullable = false)
    private double currentPrice;

    @Column(name = "buy_now_price")
    private Double buyNowPrice;

    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuctionStatus status = AuctionStatus.PENDING;

    @JsonIgnore
    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Bid> bids = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "highest_bidder_id")
    private Buyer highestBidder;

    public Auction() {
        this.startTime = new Date();
        this.endTime = new Date();
        this.status = AuctionStatus.PENDING;
        this.bids = new ArrayList<>();
    }

    public Auction(Item item, Seller seller, double startPrice, int durationDays) {
        if (item == null) {
            throw new IllegalArgumentException("Item tidak boleh null");
        }
        if (seller == null) {
            throw new IllegalArgumentException("Seller tidak boleh null");
        }
        if (startPrice <= 0) {
            throw new IllegalArgumentException("Harga awal harus lebih dari 0");
        }
        if (durationDays <= 0) {
            throw new IllegalArgumentException("Durasi lelang minimal 1 hari");
        }
        this.item = item;
        this.seller = seller;
        this.currentPrice = startPrice;
        this.startTime = new Date();
        this.endTime = new Date(System.currentTimeMillis() + (long) durationDays * 86_400_000L);
        this.status = AuctionStatus.PENDING;
        this.bids = new ArrayList<>();
    }

    @Override
    public boolean placeBid(Buyer bidder, double amount) {
        if (status != AuctionStatus.OPEN && status != AuctionStatus.CLOSING) {
            throw new AuctionNotOpenException();
        }

        if (amount <= currentPrice) {
            throw new BidTooLowException();
        }

        if (bidder == null || bidder.getWallet() == null || !bidder.getWallet().withdraw(amount)) {
            throw new InsufficientBalanceException();
        }

        if (highestBidder != null && !highestBidder.getUserId().equals(bidder.getUserId())) {
            highestBidder.getWallet().depositRefund(currentPrice);
        }

        Bid newBid = new Bid(bidder, this, amount);
        bids.add(newBid);
        if (bidder.getBidHistory() != null && !bidder.getBidHistory().contains(newBid)) {
            bidder.getBidHistory().add(newBid);
        }
        currentPrice = amount;
        highestBidder = bidder;
        return true;
    }

    public void updateStatus() {
        long remainingTime = getRemainingTime();
        if (remainingTime <= 0) {
            status = (highestBidder != null) ? AuctionStatus.SOLD : AuctionStatus.CANCELLED;
        } else if (remainingTime <= 900 && status == AuctionStatus.OPEN) {
            status = AuctionStatus.CLOSING;
        }
    }

    public boolean isExpired() {
        return getRemainingTime() <= 0;
    }

    public long getRemainingTime() {
        if (endTime == null) {
            return 0;
        }
        return Math.max(0, (endTime.getTime() - System.currentTimeMillis()) / 1000);
    }

    public String getAuctionId() {
        return auctionId;
    }

    public Item getItem() {
        return item;
    }

    public Seller getSeller() {
        return seller;
    }

    @Override
    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        if (currentPrice <= 0) {
            throw new IllegalArgumentException("Harga saat ini harus lebih dari 0");
        }
        this.currentPrice = currentPrice;
    }

    public Double getBuyNowPrice() {
        return buyNowPrice;
    }

    public void setBuyNowPrice(Double buyNowPrice) {
        if (buyNowPrice != null && buyNowPrice <= currentPrice) {
            throw new IllegalArgumentException("Buy now price harus lebih tinggi dari harga saat ini");
        }
        this.buyNowPrice = buyNowPrice;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public AuctionStatus getStatus() {
        return status;
    }

    public void setStatus(AuctionStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status lelang tidak boleh null");
        }
        this.status = status;
    }

    public List<Bid> getBids() {
        return bids;
    }

    @Override
    public List<Bid> getBidHistory() {
        return bids;
    }

    @Override
    public Buyer getHighestBidder() {
        return highestBidder;
    }

    public void setHighestBidder(Buyer highestBidder) {
        this.highestBidder = highestBidder;
    }
}
