package com.echo.backend.dto;

public class PlaceBidResponse extends BaseResponse{

    private double price;

    private boolean success;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public PlaceBidResponse(int code, String msg, Object data, double price, boolean success) {
        super(code, msg, data);
        this.price = price;
        this.success = success;
    }
}
