package com.echo.backend.dao;

import com.echo.backend.domain.AuctionBid;

import java.util.List;

public interface AuctionBidMapper {
    void newBid(AuctionBid bid);

    List<AuctionBid> viewHistoryByAid(int aid);
}
