package net.maku.auth.utils;

import org.springframework.stereotype.Component;

@Component
public class RedisKeys {
    /**
     * 验证码Key
     */
    public static String getCaptchaKey(String key) {
        return "sys:captcha:" + key;
    }

    public static String getTokenKey(String key) {
        return "sys:refreshToken:" + key;
    }

    public static String getEmailKey(String key) {
        return "sys:email:" + key;
    }

    /**
     * 授权码Key
     */
    public static String getOauthCode(String code) {
        return "oauth:code:" + code;
    }

    /**
     * 微信小程序授权key
     *
     * @param key
     * @return
     */
    public static String getWxMpAuthKey(String key) {
        return "wx:mp:auth:" + key;
    }

    /**
     * 微信小程序授权类型key
     *
     * @param key
     * @return
     */
    public static String getWxMpAuthTypeKey(String key) {
        return "wx:mp:auth:type:" + key;
    }

}
