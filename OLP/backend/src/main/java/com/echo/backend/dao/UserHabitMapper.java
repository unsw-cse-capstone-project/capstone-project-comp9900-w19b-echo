package com.echo.backend.dao;

import com.echo.backend.domain.UserHabit;

import java.util.List;

public interface UserHabitMapper {
    void updateUserHabit(UserHabit habit);

    List<UserHabit> listUserHabit(int uid);
}
