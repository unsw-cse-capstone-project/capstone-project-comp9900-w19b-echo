package com.echo.backend.service;

import com.echo.backend.dao.PropertyMapper;
import com.echo.backend.domain.Property;
import com.echo.backend.utils.GoogleMapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PropertyService {

    Logger logger = LoggerFactory.getLogger(PropertyService.class);

    private final PropertyMapper propertyMapper;

    @Autowired
    public PropertyService(PropertyMapper userMapper) {
        this.propertyMapper = userMapper;
    }

    public void addNewProperty(Property property){

        property.setCreateTime(new Date());
        String addr = property.getHouseNumber().trim().replaceAll(" ", "+") + "+" +
                property.getStreet().trim().replaceAll(" ", "+") + ",+" +
                property.getDistrict().trim().replaceAll(" ", "+") + ",+" +
                property.getState().trim().replaceAll(" ", "+") + "+" +
                property.getCode().trim();
        String location = GoogleMapUtil.getLocation(addr);
        property.setAddress(location.split(":")[0]);
        property.setLongitude(Double.valueOf(location.split(":")[1].split(",")[0]));
        property.setLatitude(Double.valueOf(location.split(":")[1].split(",")[1]));
        propertyMapper.createProperty(property);

    }

    public List<Property> getAllProperty() {
        return propertyMapper.getAllProperty();
    }
}
