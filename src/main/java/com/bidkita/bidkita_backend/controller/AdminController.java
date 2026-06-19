package com.bidkita.bidkita_backend.controller;

import com.bidkita.bidkita_backend.dto.response.AuctionResponseDTO;
import com.bidkita.bidkita_backend.dto.response.UserResponseDTO;
import com.bidkita.bidkita_backend.service.AuctionService;
import com.bidkita.bidkita_backend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AuthService authService;
    private final AuctionService auctionService;

    public AdminController(AuthService authService, AuctionService auctionService) {
        this.authService = authService;
        this.auctionService = auctionService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(authService.getAllUsers());
    }

    @GetMapping("/auctions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AuctionResponseDTO>> getAllAuctions() {
        return ResponseEntity.ok(auctionService.getAllAuctions());
    }
}
