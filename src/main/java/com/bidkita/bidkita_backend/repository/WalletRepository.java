package com.bidkita.bidkita_backend.repository;

import com.bidkita.bidkita_backend.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, String> {
}