package com.echo.backend.dto;

public class SearchPaymentDetailRequest extends BaseRequest{

    private int uid;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
