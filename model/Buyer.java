package com.bidkita.bidkita_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "buyers")
@PrimaryKeyJoinColumn(name = "user_id")
public class Buyer extends User {

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @JsonIgnore
    @OneToMany(mappedBy = "bidder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Bid> bidHistory = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "watchlist",
            joinColumns = @JoinColumn(name = "buyer_id"),
            inverseJoinColumns = @JoinColumn(name = "auction_id")
    )
    private List<Auction> watchlist = new ArrayList<>();

    public Buyer() {}

    public Buyer(String username, String email, String hashedPassword, String phone) {
        setUsername(username);
        setEmail(email);
        setPassword(hashedPassword);
        setPhoneNumber(phone);
        this.wallet = new Wallet();
        this.wallet.setOwner(this);
        this.bidHistory = new ArrayList<>();
        this.watchlist = new ArrayList<>();
    }

    @Override
    public String getRole() {
        return "BUYER";
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
        if (wallet != null && wallet.getOwner() != this) {
            wallet.setOwner(this);
        }
    }

    public List<Bid> getBidHistory() {
        return bidHistory;
    }

    public List<Auction> getWatchlist() {
        return watchlist;
    }

    public boolean placeBid(Auction auction, double amount) {
        if (auction == null) {
            throw new IllegalArgumentException("Auction tidak boleh null");
        }
        return auction.placeBid(this, amount);
    }

    public void addToWatchlist(Auction auction) {
        if (auction == null) {
            throw new IllegalArgumentException("Auction tidak boleh null");
        }
        if (!watchlist.contains(auction)) {
            watchlist.add(auction);
        }
    }
}
