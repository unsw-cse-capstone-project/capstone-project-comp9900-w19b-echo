package com.echo.backend.dao;

import com.echo.backend.domain.User;

import java.util.List;

public interface UserMapper {

    void createUser(User user);

    void updateUser(User user);

    void updateUserPassword(User user);

    void verifyUser(User user);

    List<User> selectUserById(int id);
    List<User> selectUserByName(String userName);
    List<User> selectUserByEmail(String email);

    List<User> getAllUser();
}
