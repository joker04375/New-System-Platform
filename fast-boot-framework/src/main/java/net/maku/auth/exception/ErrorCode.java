package net.maku.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNAUTHORIZED(401, "还未授权，不能访问"),
    FORBIDDEN(403, "没有权限，禁止访问"),
    INTERNAL_SERVER_ERROR(500, "服务器异常，请稍后再试"),
    ACCOUNT_PASSWORD_ERROR(1001, "账号或密码错误"),
    REFRESH_TOKEN_ERROR(1002,"刷新令牌失效"),
    ACCESS_TOKEN_ERROR(1003,"令牌失效"),

    EMAIL_EXPIRED_ERROR(1004,"验证码过期");

    private final int code;
    private final String msg;
}
