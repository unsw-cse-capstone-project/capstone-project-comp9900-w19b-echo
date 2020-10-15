package com.echo.backend.dto;

public class PlaceBidRequest {

    private double uid;

    private int aid;

    private int pid;

    public double getUid() {
        return uid;
    }

    public void setUid(double uid) {
        this.uid = uid;
    }

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
