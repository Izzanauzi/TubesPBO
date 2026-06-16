package com.bidkita.bidkita_backend.model;

import com.bidkita.bidkita_backend.model.enums.AuctionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "admins")
@PrimaryKeyJoinColumn(name = "user_id")
public class Admin extends User {

    @Column(name = "admin_code")
    private String adminCode;

    public Admin() {}

    public Admin(String username, String email, String hashedPassword, String phone, String adminCode) {
        setUsername(username);
        setEmail(email);
        setPassword(hashedPassword);
        setPhoneNumber(phone);
        this.adminCode = adminCode;
    }

    @Override
    public String getRole() {
        return "ADMIN";
    }

    public String getAdminCode() {
        return adminCode;
    }

    public void approveAuction(Auction auction) {
        if (auction == null) {
            throw new IllegalArgumentException("Auction tidak boleh null");
        }
        auction.setStatus(AuctionStatus.OPEN);
    }
}
