package com.echo.backend.dao;

import com.echo.backend.domain.AuctionRegister;

import java.util.List;

public interface AuctionRegisterMapper {
    List<AuctionRegister> getRegisterBidderByAid(int aid);

    void register(AuctionRegister register);

    List<AuctionRegister> getRegisterBidderByUid(int uid);

    void deleteRegisterBidderByUid(int uid);
}
