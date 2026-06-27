package com.bidkita.bidkita_backend.model;

import java.util.List;

public interface Biddable {
    boolean placeBid(Buyer bidder, double amount);
    double getCurrentPrice();
    Buyer getHighestBidder();
    List<Bid> getBidHistory();
}
