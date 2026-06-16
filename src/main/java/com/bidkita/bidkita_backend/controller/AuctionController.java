package com.bidkita.bidkita_backend.controller;

import com.bidkita.bidkita_backend.dto.request.BidRequestDTO;
import com.bidkita.bidkita_backend.dto.request.CreateAuctionRequestDTO;
import com.bidkita.bidkita_backend.dto.response.AuctionResponseDTO;
import com.bidkita.bidkita_backend.service.AuctionService;
import com.bidkita.bidkita_backend.service.BidService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auctions")
public class AuctionController {

    private final AuctionService auctionService;
    private final BidService bidService;

    public AuctionController(AuctionService auctionService, BidService bidService) {
        this.auctionService = auctionService;
        this.bidService = bidService;
    }

    @GetMapping
    public ResponseEntity<List<AuctionResponseDTO>> browse(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        return ResponseEntity.ok(auctionService.browse(category, status, minPrice, maxPrice));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuctionResponseDTO> getDetail(@PathVariable String id) {
        return ResponseEntity.ok(auctionService.getDetail(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<AuctionResponseDTO> createAuction(@RequestBody CreateAuctionRequestDTO dto) {
        String sellerId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED).body(auctionService.createAuction(sellerId, dto));
    }

    @PostMapping("/{id}/bid")
    @PreAuthorize("hasRole('BUYER')")
    public ResponseEntity<Map<String, String>> placeBid(@PathVariable String id,
                                                        @RequestBody BidRequestDTO dto) {
        String buyerId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        bidService.placeBid(id, buyerId, dto);
        return ResponseEntity.ok(Map.of("message", "Bid berhasil dipasang"));
    }

    @PostMapping("/{id}/buynow")
    @PreAuthorize("hasRole('BUYER')")
    public ResponseEntity<AuctionResponseDTO> buyNow(@PathVariable String id) {
        String buyerId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(auctionService.buyNow(id, buyerId));
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuctionResponseDTO> approveAuction(@PathVariable String id) {
        return ResponseEntity.ok(auctionService.approveAuction(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAuction(@PathVariable String id) {
        auctionService.deleteAuction(id);
        return ResponseEntity.noContent().build();
    }
}
