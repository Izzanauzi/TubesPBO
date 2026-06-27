package com.bidkita.bidkita_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "bids")
public class Bid {

    @Id
    @Column(name = "bid_id")
    private String bidId = UUID.randomUUID().toString();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bidder_id")
    private Buyer bidder;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @Column(nullable = false)
    private double amount;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp = new Date();

    public Bid() {}

    public Bid(Buyer bidder, Auction auction, double amount) {
        if (bidder == null) {
            throw new IllegalArgumentException("Bidder tidak boleh null");
        }
        if (auction == null) {
            throw new IllegalArgumentException("Auction tidak boleh null");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Jumlah bid harus lebih dari 0");
        }
        this.bidder = bidder;
        this.auction = auction;
        this.amount = amount;
        this.timestamp = new Date();
    }

    public String getBidId() {
        return bidId;
    }

    public Buyer getBidder() {
        return bidder;
    }

    public Auction getAuction() {
        return auction;
    }

    public double getAmount() {
        return amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
