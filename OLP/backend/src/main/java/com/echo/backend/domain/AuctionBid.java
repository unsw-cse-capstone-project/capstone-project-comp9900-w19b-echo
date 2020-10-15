package com.echo.backend.domain;

import com.alibaba.fastjson.JSON;

import java.util.Date;

public class AuctionBid {

    private int aid;

    private int uid;

    private int pid;

    private double bidValue;

    private Date bidTime;

    private int serial;

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public double getBidValue() {
        return bidValue;
    }

    public void setBidValue(double bidValue) {
        this.bidValue = bidValue;
    }

    public Date getBidTime() {
        return bidTime;
    }

    public void setBidTime(Date bidTime) {
        this.bidTime = bidTime;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }
}
