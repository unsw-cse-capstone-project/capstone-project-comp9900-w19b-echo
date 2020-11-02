package com.echo.backend.dao;

import com.echo.backend.domain.Auction;

import java.util.List;

public interface AuctionMapper {

    void createAuction(Auction auction);

    List<Auction> getAuctionByPid(int pid);

    List<Auction> getAuction10mins();

    void startAuction(Integer aid);

    void endAuctionSuccess(Integer aid);

    void endAuctionFail(Integer aid);

    void updateWinnerPrice(Auction auction);

    void updateAuction(Auction auction);

    void cancelAuction(int aid);
}
