package com.echo.backend.service;

import com.echo.backend.dao.AuctionMapper;
import com.echo.backend.domain.Auction;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuctionService {

    private final AuctionMapper auctionMapper;


    @Autowired
    public AuctionService(AuctionMapper auctionMapper) {
        this.auctionMapper = auctionMapper;
    }

    public void addAuction(Auction auction) {

        auction.setEndTime(DateUtils.addHours(auction.getBeginTime(), 12));
        auction.setStatus(1);

        auctionMapper.createAuction(auction);
    }

    public void placeNewBid(double uid, int aid, int pid) {
    }
}
