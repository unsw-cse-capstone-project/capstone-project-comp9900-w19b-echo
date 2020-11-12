package com.echo.backend.dto;

public class MessageResponse extends BaseResponse{
    public MessageResponse(int code, String msg, Object data) {
        super(code, msg, data);
    }
}
