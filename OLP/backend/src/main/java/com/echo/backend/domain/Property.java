package com.echo.backend.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel
public class Property {

    @ApiModelProperty(value = "Generate by Mysql",  required = false)
    private int pid;

    @ApiModelProperty(value = "Uid of owner, fetch by server",  required = false)
    private int owner;

    @ApiModelProperty(value = "State of property: NSW or New South Wales",  required = true)
    private String state;

    @ApiModelProperty(value = "City of property: Sydney ",  required = false)
    private String city;

    @ApiModelProperty(value = "District of property: Kingsford or Little bay",  required = true)
    private String suburb;

    @ApiModelProperty(value = "Postal code  of property: 2032",  required = true)
    private String postcode;

    @ApiModelProperty(value = "Street of property: Barker St or Fifth Avenue",  required = true)
    private String streetName;

    @ApiModelProperty(value = "House number of property: 32 or 44b",  required = true)
    private String streetNumber;

    @ApiModelProperty(value = "Longitude of property, fetch by sever",  required = false)
    private double longitude;

    @ApiModelProperty(value = "Latitude of property, fetch by sever",  required = false)
    private double latitude;

    @ApiModelProperty(value = "Address of property, fetch by sever",  required = false)
    private String address;

    @ApiModelProperty(value = "Status of property",  required = false)
    private int status;

    @ApiModelProperty(value = "Create time of property, generate by sever",  required = false)
    private Date createTime;

    @ApiModelProperty(value = "Area of property: 104.22",  required = true)
    private double area;

    @ApiModelProperty(value = "Number of bedroom: 6",  required = true)
    private int bedroom;

    @ApiModelProperty(value = "Number of bathroom: 3",  required = true)
    private int bathroom;

    @ApiModelProperty(value = "Number of bedroom: 2",  required = true)
    private int carport;

    @ApiModelProperty(value = "Type of property: 0-house, 1-apartment, 2-store",  required = true)
    private int propertyType;

    @ApiModelProperty(value = "Whether verified",  required = false)
    private int verification;

    @ApiModelProperty(value = "Description by owner",  required = true)
    private String description;

    @ApiModelProperty(value = "Description by officer", required = false)
    private String remark;

    private List<String> picUrl;

    public List<String> getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(List<String> picUrl) {
        this.picUrl = picUrl;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
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

    public int getVerification() {
        return verification;
    }

    public void setVerification(int verification) {
        this.verification = verification;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
