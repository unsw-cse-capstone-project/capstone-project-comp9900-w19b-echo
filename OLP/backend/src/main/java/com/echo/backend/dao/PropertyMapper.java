package com.echo.backend.dao;


import com.echo.backend.domain.Property;

import java.util.Collection;
import java.util.List;

public interface PropertyMapper {

    void createProperty(Property property);

    void updateProperty(Property property);

    List<Property> selectPropertyByOwner(int uid);

    List<Property> getAllProperty();

    List<Property> getPropertyByUid(int uid);

    List<Property> searchByStreet(Property property);

    List<Property> searchByDistrict(Property property);

    List<Property> searchByCode(Property property);

    List<Property> searchByCity(Property property);

    List<Property> searchByPosition(double northeastLat, double northeastLng, double southwestLat, double southwestLng);

    List<Property> getPropertyByPid(int pid);

    List<Property> searchAddress(String keyword);

    void deleteProperty(int pid);

    List<Property> getMyFavorite(int uid);

    void startAuction(Integer key);

    void updateAuctionSuccess(Integer key);

    void updateAuctionFail(Integer key);
}
