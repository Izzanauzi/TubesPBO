package com.bidkita.bidkita_backend.controller;

import com.bidkita.bidkita_backend.dto.request.TopUpRequestDTO;
import com.bidkita.bidkita_backend.dto.response.WalletResponseDTO;
import com.bidkita.bidkita_backend.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping
    @PreAuthorize("hasRole('BUYER')")
    public ResponseEntity<WalletResponseDTO> getWallet() {
        String buyerId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(walletService.getWallet(buyerId));
    }

    @PostMapping("/topup")
    @PreAuthorize("hasRole('BUYER')")
    public ResponseEntity<WalletResponseDTO> topUp(@RequestBody TopUpRequestDTO dto) {
        String buyerId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(walletService.topUp(buyerId, dto));
    }
}
