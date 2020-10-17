package com.echo.backend.dto;

import com.echo.backend.domain.Property;

public class SearchPropertyRequest extends BaseRequest{

    private Property property;

    private String northeast;

    private String southwest;

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public String getNortheast() {
        return northeast;
    }

    public void setNortheast(String northeast) {
        this.northeast = northeast;
    }

    public String getSouthwest() {
        return southwest;
    }

    public void setSouthwest(String southwest) {
        this.southwest = southwest;
    }
}