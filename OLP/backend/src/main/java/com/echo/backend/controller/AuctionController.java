package com.echo.backend.controller;

import com.echo.backend.domain.Auction;
import com.echo.backend.dto.AddAuctionRequest;
import com.echo.backend.dto.AddAuctionResponse;
import com.echo.backend.dto.PlaceBidRequest;
import com.echo.backend.dto.PlaceBidResponse;
import com.echo.backend.service.AuctionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.time.DateUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(tags = "Auction management apis")
public class AuctionController {

    private final AuctionService auctionService;

    @Autowired
    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @ApiOperation(value="Create auction", notes="Create auction")
    @RequestMapping(value = "/add-auction", method = RequestMethod.POST)
    @RequiresAuthentication
    public AddAuctionResponse createAuction(@RequestBody AddAuctionRequest request) {

        Auction auction = request.getAuction();

        Date oneDay = DateUtils.addDays(new Date(), 1);
        if (oneDay.after(request.getAuction().getBeginTime())){
            return new AddAuctionResponse(501, "Begin time must one day after current time", null);
        }

        auctionService.addAuction(auction);

        return new AddAuctionResponse(200, "Add auction success", null);
    }

    @ApiOperation(value="Place nid", notes="Place new bid")
    @RequestMapping(value = "/place-bid", method = RequestMethod.POST)
    @RequiresAuthentication
    public PlaceBidResponse placeNewBid(@RequestBody PlaceBidRequest request, HttpServletRequest hRequest) {

        auctionService.placeNewBid(request.getUid(), request.getAid(), request.getPid());

        return new PlaceBidResponse(200, "place new bid success", null);
    }
}
