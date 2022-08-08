package net.maku.auth.service.impl;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.maku.auth.bo.AccessToken;
import net.maku.auth.bo.ApiResult;
import net.maku.auth.cache.Cache;
import net.maku.auth.constant.CacheName;
import net.maku.auth.entity.UserDetail;
import net.maku.auth.exception.ErrorCode;
import net.maku.auth.properties.JwtProperties;
import net.maku.auth.provider.JwtProvider;
import net.maku.auth.service.AuthService;
import net.maku.auth.utils.RedisKeys;
import net.maku.auth.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CacheManager caffeine;

    @Autowired
    private Cache caffeineCache;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public ApiResult login(String loginAccount, String password) {
        log.debug("进入login方法");
        // 1 创建UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken usernameAuthentication = new UsernamePasswordAuthenticationToken(loginAccount, password);
        // 2 认证（自定义缓存）
        Authentication authentication = this.authenticationManager.authenticate(usernameAuthentication);
        // 3 保存认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 4 生成自定义token 和 refreshToken
        AccessToken accessToken = jwtProvider.createToken((UserDetails) authentication.getPrincipal(), jwtProperties.getExpirationTime());
        AccessToken refreshToken = jwtProvider.createToken((UserDetails) authentication.getPrincipal(), jwtProperties.getExpirationRefreshTime());
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        // 5 放入本地缓存
        caffeineCache.put(CacheName.USER, userDetail.getUsername()+"-"+accessToken.getToken(), userDetail);
        // 6 将refreshToken放入redis缓存
        redisUtils.set(RedisKeys.getTokenKey(refreshToken.getToken()), userDetail.getUsername(), jwtProperties.getExpirationRefreshTime());

        JSONObject json = new JSONObject().putOnce("access_token", accessToken.getToken()).putOnce("refresh_token", refreshToken.getToken());
        return ApiResult.ok(json);
    }

    @Override
    public ApiResult logout() {
        String loginAccount = ((UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        caffeineCache.remove(CacheName.USER, loginAccount);
        SecurityContextHolder.clearContext();
        return ApiResult.ok();
    }

    @Override
    public ApiResult refreshToken(String token) {
        List<AccessToken> accessToken = jwtProvider.refreshToken(token);
        // 刷新令牌失效,返回错误
        if (ObjectUtils.isEmpty(accessToken)) {
            return ApiResult.error(ErrorCode.REFRESH_TOKEN_ERROR);
        }

        // 查看本地缓存数据
        org.springframework.cache.Cache cache = caffeine.getCache(CacheName.USER.toString());
        UserDetail userDetail = caffeineCache.get(CacheName.USER, accessToken.get(0).getLoginAccount(), UserDetail.class);
        // 缓存token
        caffeineCache.put(CacheName.USER, accessToken.get(0).getLoginAccount(), userDetail);
        redisUtils.set(RedisKeys.getTokenKey(accessToken.get(1).getToken()), userDetail.getUsername(), jwtProperties.getExpirationRefreshTime());

        JSONObject json = new JSONObject().putOnce("access_token", accessToken.get(0).getToken()).putOnce("refresh_token", accessToken.get(1).getToken());
        return ApiResult.ok(json);
    }
}
