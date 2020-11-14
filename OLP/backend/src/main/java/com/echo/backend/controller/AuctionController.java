package com.echo.backend.controller;

import com.echo.backend.domain.Auction;
import com.echo.backend.domain.AuctionBid;
import com.echo.backend.domain.AuctionRegister;
import com.echo.backend.domain.Property;
import com.echo.backend.dto.*;
import com.echo.backend.service.AuctionService;
import com.echo.backend.service.PropertyService;
import com.echo.backend.service.UserService;
import com.echo.backend.utils.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.time.DateUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(tags = "Auction management apis")
public class AuctionController {

    private final AuctionService auctionService;

    private final PropertyService propertyService;

    private final UserService userService;

    @Autowired
    public AuctionController(AuctionService auctionService, PropertyService propertyService, UserService userService) {
        this.auctionService = auctionService;
        this.propertyService = propertyService;
        this.userService = userService;
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

    @ApiOperation(value="Audit auction", notes="Audit auction")
    @RequestMapping(value = "/update-auction", method = RequestMethod.POST)
    @RequiresAuthentication
    public AddAuctionResponse updateAuction(@RequestBody AddAuctionRequest request) {

        Auction auction = request.getAuction();

        Date oneDay = DateUtils.addDays(new Date(), 1);
        if (auction.getBeginTime()!= null && oneDay.after(auction.getBeginTime())){
            return new AddAuctionResponse(501, "Begin time must one day after current time", null);
        }

        auctionService.updateAuction(auction);

        return new AddAuctionResponse(200, "Update auction success", null);
    }

    @ApiOperation(value="Cancel auction", notes="Audit auction")
    @RequestMapping(value = "/cancel-auction", method = RequestMethod.POST)
    @RequiresAuthentication
    public AddAuctionResponse cancelAuction(@RequestBody AddAuctionRequest request) {

        auctionService.cancelAuction(request.getAuction().getAid());

        return new AddAuctionResponse(200, "Cancel auction success", null);
    }

    @ApiOperation(value="view auction", notes="view auction")
    @RequestMapping(value = "/view-auction", method = RequestMethod.POST)
    @RequiresAuthentication
    public List<Auction> viewAuction(@RequestBody SearchAuctionRequest request) {

        return auctionService.getAuctionByPid(request.getPid());
    }

    @ApiOperation(value="view active auction", notes="view active auction")
    @RequestMapping(value = "/view-active-auction", method = RequestMethod.POST)
    @RequiresAuthentication
    public List<Auction> viewActiveAuction(@RequestBody SearchAuctionRequest request) {

        return auctionService.getActiveAuction(request.getUid());
    }

    @ApiOperation(value="view active auction", notes="view active auction")
    @RequestMapping(value = "/my-active-auction", method = RequestMethod.POST)
    @RequiresAuthentication
    public List<PropertyAuction> viewMyActiveAuction(@RequestBody SearchAuctionRequest request) {



        List<Auction> auctions = auctionService.getActiveAuctionByUid(request.getUid());
        return getPropertyAuctions(auctions);
    }

    @ApiOperation(value="view complete auction", notes="view complete auction")
    @RequestMapping(value = "/view-complete-auction", method = RequestMethod.POST)
    @RequiresAuthentication
    public List<PropertyAuction> viewCompleteAuction(@RequestBody SearchAuctionRequest request) {
        List<Auction> completeAuction = auctionService.getCompleteAuction(request.getUid());
        return getPropertyAuctions(completeAuction);
    }

    private List<PropertyAuction> getPropertyAuctions(List<Auction> completeAuction) {
        List<PropertyAuction> propertyAuctions = new ArrayList<>();
        for (Auction auction : completeAuction) {
            PropertyAuction propertyAuction = new PropertyAuction();
            propertyAuction.setAuction(auction);
            List<Property> properties = propertyService.getPropertyByPid(auction.getPid());
            if(properties != null && properties.size() > 0) {
                propertyAuction.setProperty(properties.get(0));
            }
            propertyAuctions.add(propertyAuction);
        }
        return propertyAuctions;
    }

    @ApiOperation(value="register auction", notes="register auction")
    @RequestMapping(value = "/register-auction", method = RequestMethod.POST)
    @RequiresAuthentication
    public RegisterAuctionResponse registerAuction(@RequestBody RegisterAuctionRequest request, HttpServletRequest hRequest) {

        try {
            int uid = JWTUtil.getUid(hRequest.getHeader("Authorization"), null);
            userService.collectHabitFromRegisterAuction(uid, request.getAuctionRegister().getPid());
        }
        catch (Exception ignored){}

        AuctionRegister register = request.getAuctionRegister();

        auctionService.registerAuction(register);

        return new RegisterAuctionResponse(200, "Register auction success", null);
    }

    @ApiOperation(value="Place new bid", notes="Place new bid")
    @RequestMapping(value = "/place-bid", method = RequestMethod.POST)
    @RequiresAuthentication
    public PlaceBidResponse placeNewBid(@RequestBody PlaceBidRequest request, HttpServletRequest hRequest) {

        double result = auctionService.placeNewBid(request.getNewPrice(), request.getAid(), request.getUid());

        if (result > 0) {
            if (result == request.getNewPrice())
                return new PlaceBidResponse(200, "place new bid success", null, result, true);
            else
                return new PlaceBidResponse(200, "place new bid fail", null, result, false);
        } else
            return new PlaceBidResponse(200, "place new bid fail", null, result, false);
    }

    @ApiOperation(value="view bid history", notes="view bid history")
    @RequestMapping(value = "/view-bid", method = RequestMethod.POST)
    public List<AuctionBid> getBidHistory(@RequestBody SearchBidRequest request) {

        return auctionService.bidHistoryByAid(request.getAid());
    }

    @ApiOperation(value="get Auction by pid", notes="get Auction by pid")
    @RequestMapping(value = "/auction-pid", method = RequestMethod.POST)
    public List<Auction> getAuctionByPid(@RequestBody SearchAuctionRequest request) {

        return auctionService.getAuctionByPid(request.getPid());
    }
}
