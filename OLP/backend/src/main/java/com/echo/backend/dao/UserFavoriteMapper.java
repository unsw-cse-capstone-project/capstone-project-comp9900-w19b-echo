package com.echo.backend.dao;

import com.echo.backend.domain.UserFavorite;

import java.util.List;

public interface UserFavoriteMapper {

    void addFavorite(UserFavorite userFavorite);

    void removeFavorite(UserFavorite userFavorite);

    List<UserFavorite> viewMyFavorite(int uid);
}
