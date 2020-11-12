package com.echo.backend.service;

import com.echo.backend.dao.*;
import com.echo.backend.domain.*;
import com.echo.backend.utils.MailUtil;
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

    private final UserMessageMapper userMessageMapper;

    private final Map<Integer, Auction> onAuctionMap;

    private final Map<Integer, Auction> auctioningMap;

    private final Map<String, String> mailTemplate;

    private final MailUtil mailUtil;

    private final UserService userService;

    @Autowired
    public AuctionCheckTimer(AuctionMapper auctionMapper, AuctionRegisterMapper auctionRegisterMapper, AuctionBidMapper auctionBidMapper, PropertyMapper propertyMapper, UserMessageMapper userMessageMapper, Map<Integer, Auction> onAuctionMap, Map<Integer, Auction> auctioningMap, Map<String, String> mailTemplate, MailUtil mailUtil, UserService userService) {
        this.auctionMapper = auctionMapper;
        this.auctionRegisterMapper = auctionRegisterMapper;
        this.auctionBidMapper = auctionBidMapper;
        this.propertyMapper = propertyMapper;
        this.userMessageMapper = userMessageMapper;
        this.onAuctionMap = onAuctionMap;
        this.auctioningMap = auctioningMap;
        this.mailTemplate = mailTemplate;
        this.mailUtil = mailUtil;
        this.userService = userService;
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

                    UserMessage userMessage = new UserMessage();
                    userMessage.setUid(auction.getUid());
                    userMessage.setSender("admin");
                    userMessage.setSendTime(new Date());
                    userMessage.setContent("Sorry, one of your property passed in auction");
                    userMessage.setSubject("Official notification");
                    userMessage.setAid(auction.getAid());
                    userMessage.setPid(auction.getPid());
                    userMessageMapper.sendMessage(userMessage);
                }
                else {
                    auction.setStatus(3);
                    propertyMapper.updateAuctionSuccess(auction.getPid());
                    User winner = userService.getUserByUid(auction.getWinner());
                    User owner = userService.getUserByUid(auction.getUid());
                    Property property = propertyMapper.getPropertyByPid(auction.getPid()).get(0);

                    String winTemp = mailTemplate.get("SuccessBuyer")
                            .replace("${address}", property.getAddress())
                            .replace("${price}", String.valueOf(auction.getCurrentPrice()))
                            .replace("${email}", owner.getEmail());

                    String ownTemp = mailTemplate.get("SuccessSeller")
                            .replace("${address}", property.getAddress())
                            .replace("${price}", String.valueOf(auction.getCurrentPrice()))
                            .replace("${email}", winner.getEmail());

                    mailUtil.sendSimpleMail(winner.getEmail(), "Auction Win", winTemp);
                    mailUtil.sendSimpleMail(winner.getEmail(), "Property Sold", ownTemp);

                    UserMessage userMessage1 = new UserMessage();
                    userMessage1.setUid(auction.getUid());
                    userMessage1.setSender("admin");
                    userMessage1.setSendTime(new Date());
                    userMessage1.setContent("Congrats! Your property is sold!");
                    userMessage1.setSubject("Official notification");
                    userMessage1.setAid(auction.getAid());
                    userMessage1.setPid(auction.getPid());
                    userMessageMapper.sendMessage(userMessage1);

                    UserMessage userMessage2 = new UserMessage();
                    userMessage2.setUid(auction.getWinner());
                    userMessage2.setSender("admin");
                    userMessage2.setSendTime(new Date());
                    userMessage2.setContent("Congrats! Your win a auction!");
                    userMessage2.setSubject("Official notification");
                    userMessage2.setAid(auction.getAid());
                    userMessage2.setPid(auction.getPid());
                    userMessageMapper.sendMessage(userMessage2);
                }
                auctionMapper.updateWinnerPrice(auction);

                logger.debug("Found auction will end on " + entry.getValue().getEndTime() + " - " + entry.getValue().toString());
                it.remove();
            }
        }
    }

}
