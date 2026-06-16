package com.bidkita.bidkita_backend.scheduler;

import com.bidkita.bidkita_backend.model.Auction;
import com.bidkita.bidkita_backend.model.Bid;
import com.bidkita.bidkita_backend.model.enums.AuctionStatus;
import com.bidkita.bidkita_backend.model.enums.NotificationType;
import com.bidkita.bidkita_backend.repository.AuctionRepository;
import com.bidkita.bidkita_backend.service.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuctionScheduler {

    private final AuctionRepository auctionRepository;
    private final NotificationService notificationService;

    public AuctionScheduler(AuctionRepository auctionRepository,
                            NotificationService notificationService) {
        this.auctionRepository = auctionRepository;
        this.notificationService = notificationService;
    }

    @Scheduled(fixedRate = 60000)
    public void triggerStatusCheck() {
        List<Auction> active = auctionRepository
                .findByStatusIn(List.of(AuctionStatus.OPEN, AuctionStatus.CLOSING));

        for (Auction auction : active) {
            AuctionStatus before = auction.getStatus();
            auction.updateStatus();
            AuctionStatus after = auction.getStatus();

            if (before != after) {
                if (after == AuctionStatus.SOLD && auction.getHighestBidder() != null) {
                    notificationService.send(auction.getHighestBidder(),
                            "Selamat! Kamu memenangkan lelang: " + auction.getItem().getTitle(),
                            NotificationType.AUCTION_WON);
                    notificationService.send(auction.getSeller(),
                            "Item kamu terjual: " + auction.getItem().getTitle(),
                            NotificationType.ITEM_SOLD);
                } else if (after == AuctionStatus.CLOSING) {
                    List<String> notified = new java.util.ArrayList<>();
                    for (Bid bid : auction.getBids()) {
                        String bidderId = bid.getBidder().getUserId();
                        if (!notified.contains(bidderId)) {
                            notificationService.send(bid.getBidder(),
                                    "Lelang segera berakhir: " + auction.getItem().getTitle(),
                                    NotificationType.AUCTION_CLOSING);
                            notified.add(bidderId);
                        }
                    }
                } else if (after == AuctionStatus.CANCELLED) {
                    notificationService.send(auction.getSeller(),
                            "Lelang dibatalkan karena tidak ada bid: " + auction.getItem().getTitle(),
                            NotificationType.AUCTION_CANCELLED);
                }
                auctionRepository.save(auction);
            }
        }
    }
}
