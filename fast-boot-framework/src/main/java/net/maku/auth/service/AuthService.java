package net.maku.auth.service;


import net.maku.auth.bo.ApiResult;

public interface AuthService {
    ApiResult login(String loginAccount, String password);

    ApiResult logout();

    ApiResult refreshToken(String token);
}
