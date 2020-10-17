package com.echo.backend.dto;

import com.echo.backend.domain.AuctionRegister;

public class RegisterAuctionRequest {

    private AuctionRegister auctionRegister;

    public AuctionRegister getAuctionRegister() {
        return auctionRegister;
    }

    public void setAuctionRegister(AuctionRegister auctionRegister) {
        this.auctionRegister = auctionRegister;
    }
}
