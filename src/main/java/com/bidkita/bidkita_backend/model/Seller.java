package com.bidkita.bidkita_backend.model;

import com.bidkita.bidkita_backend.model.enums.AuctionStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sellers")
@PrimaryKeyJoinColumn(name = "user_id")
public class Seller extends User {

    @JsonIgnore
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Auction> auctions = new ArrayList<>();

    @Column(nullable = false)
    private double rating = 0.0;

    @Column(name = "total_sold", nullable = false)
    private int totalSold = 0;

    public Seller() {}

    public Seller(String username, String email, String hashedPassword, String phone) {
        setUsername(username);
        setEmail(email);
        setPassword(hashedPassword);
        setPhoneNumber(phone);
        this.auctions = new ArrayList<>();
        this.rating = 0.0;
        this.totalSold = 0;
    }

    @Override
    public String getRole() {
        return "SELLER";
    }

    public List<Auction> getMyAuctions() {
        return auctions;
    }

    public Auction createAuction(Item item, double startPrice, int durationDays) {
        Auction auction = new Auction(item, this, startPrice, durationDays);
        auctions.add(auction);
        return auction;
    }

    public boolean cancelAuction(String auctionId) {
        for (Auction auction : auctions) {
            if (auction.getAuctionId().equals(auctionId)) {
                auction.setStatus(AuctionStatus.CANCELLED);
                return true;
            }
        }
        return false;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Rating harus berada di antara 0 dan 5");
        }
        this.rating = rating;
    }

    public int getTotalSold() {
        return totalSold;
    }

    public void incrementTotalSold() {
        this.totalSold++;
    }
}
