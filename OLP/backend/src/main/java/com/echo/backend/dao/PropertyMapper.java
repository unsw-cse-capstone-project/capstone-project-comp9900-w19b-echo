package com.echo.backend.dao;


import com.echo.backend.domain.Property;

import java.util.List;

public interface PropertyMapper {

    void createProperty(Property property);

    void updateUser(Property property);

    List<Property> selectPropertyByOwner(int uid);

    List<Property> getAllProperty();
}
