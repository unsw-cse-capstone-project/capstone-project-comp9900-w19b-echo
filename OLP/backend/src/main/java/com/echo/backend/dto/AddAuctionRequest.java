package com.echo.backend.dto;

import com.echo.backend.domain.Auction;

public class AddAuctionRequest {
    private Auction auction;

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }
}
