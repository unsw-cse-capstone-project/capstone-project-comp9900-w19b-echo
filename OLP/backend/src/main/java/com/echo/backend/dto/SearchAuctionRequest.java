package com.echo.backend.dto;

public class SearchAuctionRequest extends BaseRequest{

    private int aid;

    private int pid;

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
