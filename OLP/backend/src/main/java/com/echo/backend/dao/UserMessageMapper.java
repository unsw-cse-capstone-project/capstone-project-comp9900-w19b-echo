package com.echo.backend.dao;

import com.echo.backend.domain.UserMessage;
import com.echo.backend.dto.ReadMessageRequest;

import java.util.List;

public interface UserMessageMapper {

    void sendMessage(UserMessage userMessage);

    void deleteMessage(int serial);

    List<UserMessage> viewMyMessage(int uid);

    void readMessage(ReadMessageRequest request);
}
