package com.bidkita.bidkita_backend.service;

import com.bidkita.bidkita_backend.dto.request.TopUpRequestDTO;
import com.bidkita.bidkita_backend.dto.response.WalletResponseDTO;
import com.bidkita.bidkita_backend.dto.response.WalletTransactionResponseDTO;
import com.bidkita.bidkita_backend.exception.ResourceNotFoundException;
import com.bidkita.bidkita_backend.model.Buyer;
import com.bidkita.bidkita_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.List;

@Service
public class WalletService {

    private final UserRepository userRepository;

    public WalletService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public WalletResponseDTO getWallet(String buyerId) {
        Buyer buyer = loadBuyer(buyerId);
        return toDTO(buyer);
    }

    @Transactional
    public WalletResponseDTO topUp(String buyerId, TopUpRequestDTO dto) {
        if (dto.getAmount() == null || dto.getAmount() <= 0) {
            throw new IllegalArgumentException("Nominal deposit harus lebih dari 0");
        }
        Buyer buyer = loadBuyer(buyerId);
        buyer.getWallet().deposit(dto.getAmount());
        userRepository.save(buyer);
        return toDTO(buyer);
    }

    private Buyer loadBuyer(String buyerId) {
        return (Buyer) userRepository.findById(buyerId)
                .orElseThrow(() -> new ResourceNotFoundException("Buyer tidak ditemukan"));
    }

    private WalletResponseDTO toDTO(Buyer buyer) {
        List<WalletTransactionResponseDTO> txDTOs = buyer.getWallet().getTransactions().stream()
                .map(tx -> new WalletTransactionResponseDTO(
                        tx.getId(),
                        tx.getAmount(),
                        tx.getType().name(),
                        tx.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                ))
                .toList();
        return new WalletResponseDTO(
                buyer.getWallet().getWalletId(),
                buyer.getWallet().getBalance(),
                txDTOs
        );
    }
}
