package com.echo.backend.dto;

import com.echo.backend.domain.Property;

public class SearchPropertyRequest extends BaseRequest{

    private Property property;

    private String northeast;

    private String southwest;

    private String keyword;

    private int uid;

    public int getUid() {
        return uid;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

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
