package com.echo.backend.dto;

import com.echo.backend.domain.Property;

public class UpdatePropertyRequest {

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    private Property property;
}
