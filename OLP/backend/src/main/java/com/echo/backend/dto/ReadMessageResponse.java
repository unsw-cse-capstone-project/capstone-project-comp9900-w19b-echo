package com.echo.backend.dto;

public class ReadMessageResponse extends BaseResponse{
    public ReadMessageResponse(int code, String msg, Object data) {
        super(code, msg, data);
    }
}
