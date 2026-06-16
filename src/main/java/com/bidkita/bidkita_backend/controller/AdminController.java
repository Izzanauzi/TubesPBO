package com.bidkita.bidkita_backend.controller;

import com.bidkita.bidkita_backend.dto.response.AuctionResponseDTO;
import com.bidkita.bidkita_backend.model.User;
import com.bidkita.bidkita_backend.repository.UserRepository;
import com.bidkita.bidkita_backend.service.AuctionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final AuctionService auctionService;

    public AdminController(UserRepository userRepository, AuctionService auctionService) {
        this.userRepository = userRepository;
        this.auctionService = auctionService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/auctions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AuctionResponseDTO>> getAllAuctions() {
        return ResponseEntity.ok(auctionService.getAllAuctions());
    }
}
