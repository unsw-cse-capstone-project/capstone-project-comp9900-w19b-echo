package com.echo.backend.controller;

import com.echo.backend.domain.Property;
import com.echo.backend.dto.AddPropertyRequest;
import com.echo.backend.dto.AddPropertyResponse;
import com.echo.backend.service.PropertyService;
import com.echo.backend.service.UserService;
import com.echo.backend.utils.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
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
    public List<Property> createProperty() {

        return propertyService.getAllProperty();
    }
}
