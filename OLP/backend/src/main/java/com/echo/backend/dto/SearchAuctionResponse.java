package com.echo.backend.dto;

import com.echo.backend.domain.Auction;

import java.util.List;

public class SearchAuctionResponse extends BaseResponse{
    public SearchAuctionResponse(int code, String msg, Object data) {
        super(code, msg, data);
    }

    private List<Auction> auctions;

    public List<Auction> getAuctions() {
        return auctions;
    }

    public void setAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }
}
