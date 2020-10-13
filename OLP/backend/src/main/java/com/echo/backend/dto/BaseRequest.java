package com.echo.backend.dto;

public abstract class BaseRequest {

    private Integer page = 0;

    private Integer dataNum = 10;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getDataNum() {
        return dataNum;
    }

    public void setDataNum(Integer dataNum) {
        this.dataNum = dataNum;
    }
}
