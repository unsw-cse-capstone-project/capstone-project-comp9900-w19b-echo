package com.echo.backend.dto;

public class SignInResponse extends BaseResponse {
    public SignInResponse(int code, String msg, Object data) {
        super(code, msg, data);
    }
}
