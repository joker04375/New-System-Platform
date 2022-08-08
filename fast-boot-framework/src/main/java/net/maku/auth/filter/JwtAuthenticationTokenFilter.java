package net.maku.auth.filter;

import cn.hutool.core.util.StrUtil;
import net.maku.auth.bo.ApiResult;
import net.maku.auth.cache.Cache;
import net.maku.auth.constant.CacheName;
import net.maku.auth.entity.UserDetail;
import net.maku.auth.exception.ErrorCode;
import net.maku.auth.properties.JwtProperties;
import net.maku.auth.provider.JwtProvider;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private Cache caffeineCache;

    @Autowired
    private CacheManager caffeine;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        LOGGER.info("JWT过滤器通过校验请求头token进行自动登录...");

        // 拿到Authorization请求头内的信息
        String authToken = jwtProvider.getToken(request);

        // 判断一下内容是否为空
        if (StrUtil.isNotEmpty(authToken) && authToken.startsWith(jwtProperties.getTokenPrefix())) {
            // 去掉token前缀(Bearer )，拿到真实token
            String newAuthToken = authToken.substring(jwtProperties.getTokenPrefix().length());

            // 拿到token里面的登录账号
            String loginAccount = jwtProvider.getSubjectFromToken(newAuthToken);
            if (StringUtils.isEmpty(loginAccount)) {
                LOGGER.error("TOKEN已经过期，请重新登录！");
                response.getWriter().println(ApiResult.error(ErrorCode.ACCESS_TOKEN_ERROR));
                throw new AccessDeniedException("TOKEN已经过期，请使用刷新令牌或者重新登录！");
            }

            // 可以的话将token中的信息存放到SecurityContextHolder上下文中（相当于认证）
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                // 查询用户
//                Collection<String> cacheNames = caffeine.getCacheNames();
                org.springframework.cache.Cache cache = caffeine.getCache(CacheName.USER.toString());
                UserDetail userDetails = caffeineCache.get(CacheName.USER, loginAccount + "-" + authToken, UserDetail.class);
                // 拿到用户信息后验证用户信息与token
                if (userDetails != null && jwtProvider.validateToken(newAuthToken, userDetails)) {

                    // 组装authentication对象，构造参数是Principal Credentials 与 Authorities
                    // 后面的拦截器里面会用到 grantedAuthorities 方法
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

                    // 将authentication信息放入到上下文对象中，全局注入角色权限信息和登录用户基本信息
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    LOGGER.info("JWT过滤器通过校验请求头token自动登录成功, user : {}", userDetails.getUsername());
                }
            }
        }
        chain.doFilter(request, response);
    }
}