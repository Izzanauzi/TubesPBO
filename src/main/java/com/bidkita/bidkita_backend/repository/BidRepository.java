package com.bidkita.bidkita_backend.repository;

import com.bidkita.bidkita_backend.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BidRepository extends JpaRepository<Bid, String> {
    List<Bid> findByBidderUserId(String buyerId);
}