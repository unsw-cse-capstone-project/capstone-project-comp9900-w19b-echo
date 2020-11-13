package com.echo.backend.dto;

public class SendMessageResponse extends BaseResponse{
    public SendMessageResponse(int code, String msg, Object data) {
        super(code, msg, data);
    }
}
