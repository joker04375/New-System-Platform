package net.maku.auth.handler;

import net.maku.auth.bo.ApiResult;
import net.maku.auth.exception.ErrorCode;
import net.maku.auth.utils.JacksonUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println(JacksonUtil.toJsonString(ApiResult.error(ErrorCode.ACCESS_TOKEN_ERROR)));
//        response.getWriter().println(JacksonUtil.toJsonString(ApiResult.fail(authException.getMessage())));
        response.getWriter().flush();
    }
}
