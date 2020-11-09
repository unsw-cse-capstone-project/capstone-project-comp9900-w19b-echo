package com.echo.backend.controller;

import com.echo.backend.domain.Auction;
import com.echo.backend.domain.Property;
import com.echo.backend.dto.*;
import com.echo.backend.service.AuctionService;
import com.echo.backend.service.PropertyService;
import com.echo.backend.service.UserService;
import com.echo.backend.utils.FileUtil;
import com.echo.backend.utils.JWTUtil;
import com.echo.backend.utils.PagingUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(tags = "Property management apis")
public class PropertyController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    private final PropertyService propertyService;

    private final UserService userService;

    private final AuctionService auctionService;

    @Value( "${server.file.upload.path}" )
    private String uploadPath;

    @Value( "${server.file.access.path}" )
    private String accessPath;

    @Autowired
    public PropertyController(PropertyService propertyService, UserService userService, AuctionService auctionService) {
        this.propertyService = propertyService;
        this.userService = userService;
        this.auctionService = auctionService;
    }

    @ApiOperation(value="Create property", notes="Create property")
    @RequestMapping(value = "/add-property", method = RequestMethod.POST)
    @RequiresAuthentication
    public AddPropertyResponse createProperty(@RequestBody AddPropertyRequest request, HttpServletRequest hRquest) {

        int uid = JWTUtil.getUid(hRquest.getHeader("Authorization"), userService);
        Property property = request.getProperty();
        property.setOwner(uid);

        propertyService.addNewProperty(property);

        return new AddPropertyResponse(200, "Add property success", null);
    }

    @RequestMapping(value = "/listAllProperty", method = RequestMethod.POST)
    //@RequiresAuthentication
    //@ApiIgnore
    public List<PropertyAuction> getAllProperty(@RequestBody SearchPropertyRequest searchRequest) {

        List<Property> properties = PagingUtil.afterPaging(FileUtil.generatePropertyPic(propertyService.getAllProperty(), uploadPath, accessPath), searchRequest.getPage(), searchRequest.getDataNum());
        return getPropertyAuctions(properties);
    }

    @RequestMapping(value = "/my-property", method = RequestMethod.POST)
    @RequiresAuthentication
    public List<PropertyAuction> getMyProperty(HttpServletRequest request) {
        int uid = JWTUtil.getUid(request.getHeader("Authorization"), userService);
        List<Property> properties = FileUtil.generatePropertyPic(propertyService.getPropertyByUid(uid), uploadPath, accessPath);
        return getPropertyAuctions(properties);
    }

    private List<PropertyAuction> getPropertyAuctions(List<Property> properties) {
        List<PropertyAuction> propertyAuctions = new ArrayList<>();
        for(Property p : properties){
            PropertyAuction propertyAuction = new PropertyAuction();
            propertyAuction.setProperty(p);
            List<Auction> auctions = auctionService.getAuctionByPid(p.getPid());
            if(auctions != null && auctions.size() > 0) {
                propertyAuction.setAuction(auctions.get(0));
            }
            propertyAuctions.add(propertyAuction);
        }
        return propertyAuctions;
    }

    @RequestMapping(value = "/others-property", method = RequestMethod.POST)
    //@RequiresAuthentication
    public List<Property> getOthersProperty(@RequestBody SearchPropertyRequest request) {

        int uid = request.getUid();
        return FileUtil.generatePropertyPic(propertyService.getPropertyByUid(uid), uploadPath, accessPath);
    }

    @RequestMapping(value = "/view-property-pid", method = RequestMethod.POST)
    @RequiresAuthentication
    public List<Property> viewPropertyByPid(@RequestBody SearchPropertyRequest request) {

        return FileUtil.generatePropertyPic(propertyService.getPropertyByPid(request.getPid()), uploadPath, accessPath);
    }

    @RequestMapping(value = "/update-property", method = RequestMethod.POST)
    @RequiresAuthentication
    public UpdatePropertyResponse updateProperty(@RequestBody UpdatePropertyRequest request) {

        propertyService.updateProperty(request.getProperty());

        return new UpdatePropertyResponse(200, "Update property success", null);
    }

    @RequestMapping(value = "/remove-property", method = RequestMethod.POST)
    @RequiresAuthentication
    public UpdatePropertyResponse removeProperty(@RequestBody UpdatePropertyRequest request) {

        propertyService.removeProperty(request.getProperty().getPid());

        return new UpdatePropertyResponse(200, "Remove property success", null);
    }

    @RequestMapping(value = "/search-property-address", method = RequestMethod.POST)
    //@RequiresAuthentication
    public List<Property> searchPropertyFilter(@RequestBody SearchPropertyRequest searchRequest) {

        List<Property> result = FileUtil.generatePropertyPic(propertyService.searchPropertyFilter(searchRequest.getProperty()), uploadPath, accessPath);
        return PagingUtil.afterPaging(result, searchRequest.getPage(), searchRequest.getDataNum());
    }

    @RequestMapping(value = "/search-property-position", method = RequestMethod.POST)
    //@RequiresAuthentication
    public List<Property> searchPropertyPosition(@RequestBody SearchPropertyRequest searchRequest) {

        List<Property> result = FileUtil.generatePropertyPic(propertyService.searchPropertyPosition(searchRequest.getNortheast(), searchRequest.getSouthwest()), uploadPath, accessPath);
        return PagingUtil.afterPaging(result, searchRequest.getPage(), searchRequest.getDataNum());
    }

    @RequestMapping(value = "/search-property-like", method = RequestMethod.POST)
    //@RequiresAuthentication
    public List<PropertyAuction> searchPropertyVague(@RequestBody SearchPropertyRequest searchRequest) {

        List<Property> result = FileUtil.generatePropertyPic(propertyService.searchPropertyVague(searchRequest.getKeyword()), uploadPath, accessPath);
        List<Property> properties = PagingUtil.afterPaging(result, searchRequest.getPage(), searchRequest.getDataNum());
        return getPropertyAuctions(properties);
    }

}
