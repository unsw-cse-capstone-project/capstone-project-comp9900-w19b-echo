package com.echo.backend.dao;

import com.echo.backend.domain.UserMessage;

import java.util.List;

public interface UserMessageMapper {

    void sendMessage(UserMessage userMessage);

    void deleteMessage(int serial);

    List<UserMessage> viewMyMessage(int uid);
}
