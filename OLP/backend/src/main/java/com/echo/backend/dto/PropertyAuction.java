package com.echo.backend.dto;

import com.echo.backend.domain.Auction;
import com.echo.backend.domain.Property;

public class PropertyAuction {
    private Property property;
    private Auction auction;

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }
}
