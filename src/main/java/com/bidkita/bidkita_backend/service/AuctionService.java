package com.bidkita.bidkita_backend.service;

import com.bidkita.bidkita_backend.dto.request.CreateAuctionRequestDTO;
import com.bidkita.bidkita_backend.dto.response.AuctionResponseDTO;
import com.bidkita.bidkita_backend.exception.InsufficientBalanceException;
import com.bidkita.bidkita_backend.exception.ResourceNotFoundException;
import com.bidkita.bidkita_backend.model.*;
import com.bidkita.bidkita_backend.model.enums.AuctionStatus;
import com.bidkita.bidkita_backend.model.enums.NotificationType;
import com.bidkita.bidkita_backend.repository.AuctionRepository;
import com.bidkita.bidkita_backend.repository.ItemRepository;
import com.bidkita.bidkita_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final ItemFactory itemFactory;

    public AuctionService(AuctionRepository auctionRepository,
                          ItemRepository itemRepository,
                          UserRepository userRepository,
                          NotificationService notificationService,
                          ItemFactory itemFactory) {
        this.auctionRepository = auctionRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        this.itemFactory = itemFactory;
    }

    public List<AuctionResponseDTO> browse(String category, String status,
                                           Double minPrice, Double maxPrice) {
        return auctionRepository.findAll().stream()
                .filter(a -> category == null || a.getItem().getCategory().equalsIgnoreCase(category))
                .filter(a -> status == null || a.getStatus().name().equalsIgnoreCase(status))
                .filter(a -> minPrice == null || a.getCurrentPrice() >= minPrice)
                .filter(a -> maxPrice == null || a.getCurrentPrice() <= maxPrice)
                .map(this::toDTO)
                .toList();
    }

    public AuctionResponseDTO getDetail(String id) {
        Auction auction = auctionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Auction tidak ditemukan"));
        return toDTO(auction);
    }

    public AuctionResponseDTO createAuction(String sellerId, CreateAuctionRequestDTO dto) {
        Seller seller = (Seller) userRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller tidak ditemukan"));

        Item item = itemFactory.create(dto);
        itemRepository.save(item);

        Auction auction = new Auction(item, seller, dto.getStartingPrice(), dto.getDuration());
        if (dto.getBuyNowPrice() != null) {
            auction.setBuyNowPrice(dto.getBuyNowPrice());
        }
        auctionRepository.save(auction);
        return toDTO(auction);
    }

    public AuctionResponseDTO approveAuction(String auctionId) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new ResourceNotFoundException("Auction tidak ditemukan"));
        auction.setStatus(AuctionStatus.OPEN);
        auctionRepository.save(auction);
        notificationService.send(auction.getSeller(),
                "Lelang kamu disetujui: " + auction.getItem().getTitle(),
                NotificationType.AUCTION_APPROVED);
        return toDTO(auction);
    }

    public void deleteAuction(String auctionId) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new ResourceNotFoundException("Auction tidak ditemukan"));
        auctionRepository.delete(auction);
    }

    @Transactional
    public AuctionResponseDTO buyNow(String auctionId, String buyerId) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new ResourceNotFoundException("Auction tidak ditemukan"));
        Buyer buyer = (Buyer) userRepository.findById(buyerId)
                .orElseThrow(() -> new ResourceNotFoundException("Buyer tidak ditemukan"));

        if (auction.getStatus() != AuctionStatus.OPEN || auction.getBuyNowPrice() == null) {
            throw new IllegalArgumentException("Buy Now tidak tersedia untuk lelang ini");
        }

        Buyer prevHighest = auction.getHighestBidder();

        if (!buyer.getWallet().withdraw(auction.getBuyNowPrice())) {
            throw new InsufficientBalanceException();
        }

        if (prevHighest != null && !prevHighest.getUserId().equals(buyerId)) {
            prevHighest.getWallet().depositRefund(auction.getCurrentPrice());
            userRepository.save(prevHighest);
        }

        auction.setStatus(AuctionStatus.SOLD);
        auction.setHighestBidder(buyer);
        auctionRepository.save(auction);
        userRepository.save(buyer);

        notificationService.send(buyer, "Kamu berhasil membeli " + auction.getItem().getTitle(),
                NotificationType.BUY_NOW_SUCCESS);
        notificationService.send(auction.getSeller(),
                "Item kamu terjual: " + auction.getItem().getTitle(),
                NotificationType.ITEM_SOLD);

        return toDTO(auction);
    }

    public List<AuctionResponseDTO> getSellerAuctions(String sellerId) {
        return auctionRepository.findBySellerUserId(sellerId).stream()
                .map(this::toDTO)
                .toList();
    }

    public List<AuctionResponseDTO> getAllAuctions() {
        return auctionRepository.findAll().stream().map(this::toDTO).toList();
    }

    public AuctionResponseDTO toDTO(Auction a) {
        String highestBidderUsername = a.getHighestBidder() != null
                ? a.getHighestBidder().getUsername() : null;
        return new AuctionResponseDTO(
                a.getAuctionId(),
                a.getItem().getTitle(),
                a.getItem().getCategory(),
                a.getItem().getImageBase64(),
                a.getStatus().name(),
                a.getCurrentPrice(),
                a.getRemainingTime(),
                a.getSeller().getUsername(),
                a.getBids().size(),
                highestBidderUsername,
                a.getBuyNowPrice()
        );
    }
}
