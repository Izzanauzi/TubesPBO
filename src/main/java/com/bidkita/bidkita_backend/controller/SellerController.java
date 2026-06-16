package com.bidkita.bidkita_backend.controller;

import com.bidkita.bidkita_backend.dto.response.AuctionResponseDTO;
import com.bidkita.bidkita_backend.service.AuctionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/seller")
public class SellerController {

    private final AuctionService auctionService;

    public SellerController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping("/auctions")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<List<AuctionResponseDTO>> getSellerAuctions() {
        String sellerId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(auctionService.getSellerAuctions(sellerId));
    }
}
