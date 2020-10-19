package com.echo.backend.service;

import com.echo.backend.dao.PropertyMapper;
import com.echo.backend.domain.Property;
import com.echo.backend.utils.GoogleMapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PropertyService {

    private Logger logger = LoggerFactory.getLogger(PropertyService.class);

    private final PropertyMapper propertyMapper;

    @Autowired
    public PropertyService(PropertyMapper userMapper) {
        this.propertyMapper = userMapper;
    }

    public void addNewProperty(Property property){

        property.setCreateTime(new Date());
        String addr = property.getStreetNumber().trim().replaceAll(" ", "+") + "+" +
                property.getStreetName().trim().replaceAll(" ", "+") + ",+" +
                property.getSuburb().trim().replaceAll(" ", "+") + ",+" +
                property.getState().trim().replaceAll(" ", "+") + "+" +
                property.getPostcode().trim();
        try {
            String location = GoogleMapUtil.getLocation(addr);
            property.setAddress(location.split(":")[0]);
            property.setLongitude(Double.valueOf(location.split(":")[1].split(",")[0]));
            property.setLatitude(Double.valueOf(location.split(":")[1].split(",")[1]));
        }
        catch (NullPointerException e){
            logger.error("--- google map search error, check network connection---\n"+addr);
            property.setAddress("empty");
        }
        propertyMapper.createProperty(property);

    }

    public List<Property> getAllProperty() {
        return propertyMapper.getAllProperty();
    }

    public List<Property> getPropertyByUid(int uid) {
        return propertyMapper.getPropertyByUid(uid);
    }


    public List<Property> searchPropertyFilter(Property property) {

        List<Property> result = new ArrayList<>();

        if (null != property.getStreetName()){
            return propertyMapper.searchByStreet(property);
        }

        if (null != property.getSuburb()){
            result.addAll(propertyMapper.searchByDistrict(property));
        }

        if (null != property.getPostcode()){
            result.addAll(propertyMapper.searchByCode(property));
        }

        if (null != property.getSuburb() || null != property.getPostcode()){
            return result;
        }

        if (null != property.getCity()){
            return propertyMapper.searchByCity(property);
        }

        return null;
    }

    public List<Property> searchPropertyPosition(String northeast, String southwest) {

        // "northeast" : {
        //                  "lat" : -33.919813,
        //                  "lng" : 151.2323356
        //               },
        //               "southwest" : {
        //                  "lat" : -33.9200718,
        //                  "lng" : 151.2322071
        //               }

        double northeastLat = Double.valueOf(northeast.split(",")[0]);
        double northeastLng = Double.valueOf(northeast.split(",")[1]);
        double southwestLat = Double.valueOf(southwest.split(",")[0]);
        double southwestLng = Double.valueOf(southwest.split(",")[0]);
        return propertyMapper.searchByPosition(northeastLat, northeastLng, southwestLat, southwestLng);
    }
}
