package com.echo.backend.dto;

public class SendEmailResponse extends BaseResponse{
    public SendEmailResponse(int code, String msg, Object data) {
        super(code, msg, data);
    }
}
