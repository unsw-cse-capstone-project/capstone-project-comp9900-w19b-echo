package com.echo.backend.dto;

import com.echo.backend.domain.User;

public class SignUpResponse extends BaseResponse {

    public SignUpResponse(int code, String msg, Object data) {
        super(code, msg, data);
    }
}
