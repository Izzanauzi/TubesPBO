package com.bidkita.bidkita_backend.controller;

import com.bidkita.bidkita_backend.dto.response.BidHistoryResponseDTO;
import com.bidkita.bidkita_backend.service.BidService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/buyer")
public class BuyerController {

    private final BidService bidService;

    public BuyerController(BidService bidService) {
        this.bidService = bidService;
    }

    @GetMapping("/bids")
    @PreAuthorize("hasRole('BUYER')")
    public ResponseEntity<List<BidHistoryResponseDTO>> getBidHistory() {
        String buyerId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(bidService.getBidHistory(buyerId));
    }
}
