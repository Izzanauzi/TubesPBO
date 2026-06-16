package com.bidkita.bidkita_backend.service;

import com.bidkita.bidkita_backend.dto.request.BidRequestDTO;
import com.bidkita.bidkita_backend.dto.response.BidHistoryResponseDTO;
import com.bidkita.bidkita_backend.exception.ResourceNotFoundException;
import com.bidkita.bidkita_backend.model.Auction;
import com.bidkita.bidkita_backend.model.Bid;
import com.bidkita.bidkita_backend.model.Buyer;
import com.bidkita.bidkita_backend.model.enums.NotificationType;
import com.bidkita.bidkita_backend.repository.AuctionRepository;
import com.bidkita.bidkita_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.List;

@Service
public class BidService {

    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public BidService(AuctionRepository auctionRepository,
                      UserRepository userRepository,
                      NotificationService notificationService) {
        this.auctionRepository = auctionRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public void placeBid(String auctionId, String buyerId, BidRequestDTO dto) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new ResourceNotFoundException("Auction tidak ditemukan"));
        Buyer buyer = (Buyer) userRepository.findById(buyerId)
                .orElseThrow(() -> new ResourceNotFoundException("Buyer tidak ditemukan"));

        Buyer prevHighest = auction.getHighestBidder();

        auction.placeBid(buyer, dto.getAmount());
        auctionRepository.save(auction);
        userRepository.save(buyer);

        if (prevHighest != null && !prevHighest.getUserId().equals(buyerId)) {
            userRepository.save(prevHighest);
            notificationService.send(prevHighest,
                    "Bid kamu dikalahkan pada lelang: " + auction.getItem().getTitle(),
                    NotificationType.OUTBID);
        }
    }

    public List<BidHistoryResponseDTO> getBidHistory(String buyerId) {
        Buyer buyer = (Buyer) userRepository.findById(buyerId)
                .orElseThrow(() -> new ResourceNotFoundException("Buyer tidak ditemukan"));

        return buyer.getBidHistory().stream()
                .map(bid -> toDTO(bid, buyerId))
                .toList();
    }

    private BidHistoryResponseDTO toDTO(Bid bid, String buyerId) {
        Auction auction = bid.getAuction();
        boolean isWinning = auction.getHighestBidder() != null
                && auction.getHighestBidder().getUserId().equals(buyerId);
        return new BidHistoryResponseDTO(
                bid.getBidId(),
                bid.getAmount(),
                bid.getTimestamp().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                auction.getAuctionId(),
                auction.getItem().getTitle(),
                auction.getStatus().name(),
                isWinning
        );
    }
}
