package com.echo.backend.service;

import com.echo.backend.dao.AuctionBidMapper;
import com.echo.backend.dao.AuctionMapper;
import com.echo.backend.dao.AuctionRegisterMapper;
import com.echo.backend.domain.Auction;
import com.echo.backend.domain.AuctionBid;
import com.echo.backend.domain.AuctionRegister;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AuctionService {

    private Logger logger = LoggerFactory.getLogger(AuctionService.class);

    private final AuctionMapper auctionMapper;

    private final AuctionRegisterMapper auctionRegisterMapper;

    private final AuctionBidMapper auctionBidMapper;

    private final Map<Integer, Auction> auctioningMap;

    @Autowired
    public AuctionService(AuctionMapper auctionMapper, AuctionRegisterMapper auctionRegisterMapper, AuctionBidMapper auctionBidMapper, Map<Integer, Auction> auctioningMap) {
        this.auctionMapper = auctionMapper;
        this.auctionRegisterMapper = auctionRegisterMapper;
        this.auctionBidMapper = auctionBidMapper;
        this.auctioningMap = auctioningMap;
    }

    public void registerAuction(AuctionRegister register) {
        auctionRegisterMapper.register(register);
    }

    public void quitAuction(int uid) {
        auctionRegisterMapper.deleteRegisterBidderByUid(uid);
    }

    public void addAuction(Auction auction) {

        if (null == auction.getEndTime())
        {
            auction.setEndTime(DateUtils.addHours(auction.getBeginTime(), 12));
        }
        auction.setStatus(1);

        auctionMapper.createAuction(auction);
    }

    public double placeNewBid(double newPrice, int aid, int uid) {

        if (auctioningMap.containsKey(aid))
        {
            Auction auction = auctioningMap.get(aid);

            if (new Date().after(auction.getEndTime())){
                return -1;
            }

            if (newPrice > auction.getCurrentPrice()) {
                auction.setCurrentPrice(newPrice);
                auction.setWinner(uid);
                if (DateUtils.addMinutes(new Date(), 5).after(auction.getEndTime())){
                    auction.setEndTime(DateUtils.addMinutes(auction.getEndTime(), 2));
                    auctionMapper.updateEndTime(auction);
                }

                auctionMapper.updateWinnerPrice(auction);
                AuctionBid bid = new AuctionBid();
                bid.setUid(auction.getWinner());
                bid.setAid(auction.getAid());
                bid.setPid(auction.getPid());
                bid.setBidValue(auction.getCurrentPrice());
                bid.setBidTime(new Date());
                auctionBidMapper.newBid(bid);
                logger.info("---- new bid ----/n" + auction.toString());
                return newPrice;
            }
            return auction.getCurrentPrice();
        }

        return -1;
    }

    public List<Auction> getAuctionByPid(int pid){
        return auctionMapper.getAuctionByPid(pid);
    }

    public List<AuctionBid> bidHistoryByAid(int aid) {
        return auctionBidMapper.viewHistoryByAid(aid);
    }

    public void updateAuction(Auction auction) {
        auctionMapper.updateAuction(auction);
    }

    public void cancelAuction(int aid) {
        auctionMapper.cancelAuction(aid);
    }

    public List<Auction> getActiveAuction(int uid) {
        List<AuctionRegister> registers = auctionRegisterMapper.getRegisterBidderByUid(uid);

        List<Auction> ret = new ArrayList<>();
        for (AuctionRegister re:registers){
            ret.add(auctioningMap.get(re.getAid()));
        }

        return ret;
    }

    public List<Auction> getActiveAuctionByUid(int uid) {
        List<AuctionRegister> registers = auctionRegisterMapper.getRegisterBidderByUid(uid);

        List<Auction> ret = new ArrayList<>();
        for (AuctionRegister re:registers){
            Auction auction = auctionMapper.getAuctionByPid(re.getPid()).get(0);
            if(auction.getStatus() == 1 || auction.getStatus() == 2)
                ret.add(auction);
        }

        return ret;
    }

    public List<Auction> getCompleteAuction(int uid) {
        return auctionMapper.getCompleteAuction(uid);
    }

    public void cancelAuctionByPid(int pid) {
        auctionMapper.cancelAuctionByPid(pid);
    }
}
