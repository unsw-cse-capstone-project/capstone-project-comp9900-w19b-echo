package com.echo.backend.service;

import com.echo.backend.dao.PropertyMapper;
import com.echo.backend.domain.Property;
import com.echo.backend.utils.GoogleMapUtil;
import org.apache.commons.lang.StringUtils;
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

    public void updateProperty(Property property) {

        Property exist = propertyMapper.getPropertyByPid(property.getPid()).get(0);

        if (StringUtils.isNotEmpty(property.getCity())) {
            exist.setCity(property.getCity());
        }

        if (StringUtils.isNotEmpty(property.getDescription())) {
            exist.setDescription(property.getDescription());
        }

        if (StringUtils.isNotEmpty(property.getPostcode())) {
            exist.setPostcode(property.getPostcode());
        }

        if (StringUtils.isNotEmpty(property.getState())) {
            exist.setState(property.getState());
        }

        if (StringUtils.isNotEmpty(property.getStreetName())) {
            exist.setStreetName(property.getStreetName());
        }

        if (StringUtils.isNotEmpty(property.getStreetNumber())) {
            exist.setStreetNumber(property.getStreetNumber());
        }

        if (StringUtils.isNotEmpty(property.getSuburb())) {
            exist.setSuburb(property.getSuburb());
        }

        if (property.getArea()>0) {
            exist.setArea(property.getArea());
        }

        if (property.getBathroom()>0) {
            exist.setBathroom(property.getBathroom());
        }

        if (property.getBedroom()>0) {
            exist.setBedroom(property.getBedroom());
        }

        if (property.getCarport()>0) {
            exist.setCarport(property.getCarport());
        }

        propertyMapper.updateProperty(exist);
    }

    public List<Property> searchPropertyVague(String keyword) {
        return propertyMapper.searchAddress(keyword);
    }

    public List<Property> getPropertyByPid(int pid) {
        return propertyMapper.getPropertyByPid(pid);
    }
}
