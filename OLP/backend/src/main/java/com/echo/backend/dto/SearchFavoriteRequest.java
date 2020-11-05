package com.echo.backend.dto;

public class SearchFavoriteRequest extends BaseRequest{

    private int uid;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
