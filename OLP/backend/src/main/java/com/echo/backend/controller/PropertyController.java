package com.echo.backend.controller;

import com.echo.backend.domain.Property;
import com.echo.backend.dto.AddPropertyRequest;
import com.echo.backend.dto.AddPropertyResponse;
import com.echo.backend.dto.SearchPropertyRequest;
import com.echo.backend.service.AuctionService;
import com.echo.backend.service.PropertyService;
import com.echo.backend.service.UserService;
import com.echo.backend.utils.JWTUtil;
import com.echo.backend.utils.PagingUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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

    @Autowired
    public PropertyController(PropertyService propertyService, UserService userService) {
        this.propertyService = propertyService;
        this.userService = userService;
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
    @RequiresAuthentication
    @ApiIgnore
    public List<Property> getAllProperty(@RequestBody SearchPropertyRequest searchRequest) {

        return PagingUtil.afterPaging(propertyService.getAllProperty(), searchRequest.getPage(), searchRequest.getDataNum());
    }

    @RequestMapping(value = "/my-property", method = RequestMethod.POST)
    @RequiresAuthentication
    public List<Property> getMyProperty(HttpServletRequest request) {

        int uid = JWTUtil.getUid(request.getHeader("Authorization"), userService);
        return propertyService.getPropertyByUid(uid);
    }

    @RequestMapping(value = "/others-property", method = RequestMethod.POST)
    //@RequiresAuthentication
    public List<Property> getOthersProperty(SearchPropertyRequest request) {

        int uid = request.getProperty().getOwner();
        return propertyService.getPropertyByUid(uid);
    }

    @RequestMapping(value = "/search-property-address", method = RequestMethod.POST)
    //@RequiresAuthentication
    public List<Property> searchPropertyFilter(@RequestBody SearchPropertyRequest searchRequest) {

        List<Property> result = propertyService.searchPropertyFilter(searchRequest.getProperty());
        return PagingUtil.afterPaging(result, searchRequest.getPage(), searchRequest.getDataNum());
    }

    @RequestMapping(value = "/search-property-position", method = RequestMethod.POST)
    //@RequiresAuthentication
    public List<Property> searchPropertyPosition(@RequestBody SearchPropertyRequest searchRequest) {

        List<Property> result = propertyService.searchPropertyPosition(searchRequest.getNortheast(), searchRequest.getSouthwest());
        return PagingUtil.afterPaging(result, searchRequest.getPage(), searchRequest.getDataNum());
    }

}
