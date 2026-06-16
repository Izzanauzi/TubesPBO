package com.bidkita.bidkita_backend.repository;

import com.bidkita.bidkita_backend.model.Auction;
import com.bidkita.bidkita_backend.model.enums.AuctionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, String> {
    List<Auction> findByStatusIn(List<AuctionStatus> statuses);
    List<Auction> findBySellerUserId(String sellerId);
    List<Auction> findByStatus(AuctionStatus status);
}