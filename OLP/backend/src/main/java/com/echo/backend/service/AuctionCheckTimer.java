package com.echo.backend.service;

import com.echo.backend.dao.AuctionBidMapper;
import com.echo.backend.dao.AuctionMapper;
import com.echo.backend.dao.AuctionRegisterMapper;
import com.echo.backend.dao.PropertyMapper;
import com.echo.backend.domain.Auction;
import com.echo.backend.domain.AuctionRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Component
public class AuctionCheckTimer {

    private Logger logger = LoggerFactory.getLogger(AuctionCheckTimer.class);

    private final AuctionMapper auctionMapper;

    private final AuctionRegisterMapper auctionRegisterMapper;

    private final AuctionBidMapper auctionBidMapper;

    private final PropertyMapper propertyMapper;

    private final Map<Integer, Auction> onAuctionMap;

    private final Map<Integer, Auction> auctioningMap;

    @Autowired
    public AuctionCheckTimer(AuctionMapper auctionMapper, AuctionRegisterMapper auctionRegisterMapper, AuctionBidMapper auctionBidMapper, PropertyMapper propertyMapper, Map<Integer, Auction> onAuctionMap, Map<Integer, Auction> auctioningMap) {
        this.auctionMapper = auctionMapper;
        this.auctionRegisterMapper = auctionRegisterMapper;
        this.auctionBidMapper = auctionBidMapper;
        this.propertyMapper = propertyMapper;
        this.onAuctionMap = onAuctionMap;
        this.auctioningMap = auctioningMap;
    }

    // 每5分钟检查一次，是否有即将开始竞拍的property
    @Scheduled(cron = "0 0/5 * * * ?")
    public void checkAuction10mins() {

        logger.info("---checking auction---");
        List<Auction> result = auctionMapper.getAuction10mins();
        if (CollectionUtils.isEmpty(result)){
            return;
        }

        // add auction to cache1
        for (Auction auction:result){
            if (!onAuctionMap.containsKey(auction.getAid())){
                logger.debug("Found auction: " + auction.toString() + ", start at" + auction.getBeginTime());
                onAuctionMap.put(auction.getAid(), auction);

            }
        }
    }

    // 每10秒检查一次，将开始的竞拍开始
    @Scheduled(cron = "0/10 * * * * ?")
    public void checkOnAuction() {

        if (CollectionUtils.isEmpty(onAuctionMap))
            return;

        for (Iterator<Map.Entry<Integer, Auction>> it = onAuctionMap.entrySet().iterator(); it.hasNext();){
            Map.Entry<Integer, Auction> entry = it.next();
            if (entry.getValue().getBeginTime().before(new Date())){

                auctionMapper.startAuction(entry.getKey());
                propertyMapper.startAuction(entry.getValue().getPid());
                List<AuctionRegister> registers = auctionRegisterMapper.getRegisterBidderByAid(entry.getValue().getAid());
                double max = entry.getValue().getBasePrice();
                int winner = entry.getValue().getUid();

                for (AuctionRegister register:registers){
                    if (register.getBasePrice()>=max){
                        max = register.getBasePrice();
                        winner = register.getUid();
                    }
                }

                entry.getValue().setCurrentPrice(max);
                entry.getValue().setWinner(winner);

                auctioningMap.put(entry.getKey(), entry.getValue());
                logger.debug("Found auction will start at : "+ entry.getValue().getBeginTime() + " - " + entry.getValue().toString());
                it.remove();
            }
        }
    }

    // 每10秒检查一次，将结束的竞拍结束
    @Scheduled(cron = "0/10 * * * * ?")
    public void checkEndAuction() {

        if (CollectionUtils.isEmpty(auctioningMap))
            return;

        for (Iterator<Map.Entry<Integer, Auction>> it = auctioningMap.entrySet().iterator(); it.hasNext();){
            Map.Entry<Integer, Auction> entry = it.next();
            if (entry.getValue().getEndTime().before(new Date())){

                Auction auction = entry.getValue();
                if (auction.getWinner() == auction.getUid()){
                    auction.setStatus(4);
                    propertyMapper.updateAuctionFail(auction.getPid());
                }
                else {
                    auction.setStatus(3);
                    propertyMapper.updateAuctionSuccess(auction.getPid());
                }
                auctionMapper.updateWinnerPrice(auction);


                logger.debug("Found auction will end on " + entry.getValue().getEndTime() + " - " + entry.getValue().toString());
                it.remove();
            }
        }
    }

}
