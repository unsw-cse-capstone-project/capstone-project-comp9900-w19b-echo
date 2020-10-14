package com.echo.backend.dto;

import com.echo.backend.domain.User;

public class UpdatePasswordRequest{
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
