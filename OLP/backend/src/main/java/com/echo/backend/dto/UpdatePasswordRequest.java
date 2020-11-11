package com.echo.backend.dto;

import com.echo.backend.domain.User;

public class UpdatePasswordRequest{
    private User user;
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
