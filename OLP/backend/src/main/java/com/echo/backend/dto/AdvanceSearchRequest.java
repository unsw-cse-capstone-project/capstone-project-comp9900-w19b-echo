package com.echo.backend.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class AdvanceSearchRequest extends BaseRequest{

    private String state;
    private String suburb;
    private int bedroom;
    private int bathroom;
    private int carport;
    private int propertyType;
    private String text;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public int getBedroom() {
        return bedroom;
    }

    public void setBedroom(int bedroom) {
        this.bedroom = bedroom;
    }

    public int getBathroom() {
        return bathroom;
    }

    public void setBathroom(int bathroom) {
        this.bathroom = bathroom;
    }

    public int getCarport() {
        return carport;
    }

    public void setCarport(int carport) {
        this.carport = carport;
    }

    public int getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(int propertyType) {
        this.propertyType = propertyType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
