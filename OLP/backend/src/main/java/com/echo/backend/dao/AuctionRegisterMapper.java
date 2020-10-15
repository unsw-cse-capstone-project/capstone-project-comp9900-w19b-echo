package com.echo.backend.dao;

import com.echo.backend.domain.AuctionRegister;

import java.util.List;

public interface AuctionRegisterMapper {
    List<AuctionRegister> getRegisterBidderByAid(int aid);
}
