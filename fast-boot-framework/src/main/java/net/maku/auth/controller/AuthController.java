package net.maku.auth.controller;

import net.maku.auth.bo.ApiResult;
import net.maku.auth.bo.LoginInfo;
import net.maku.auth.provider.JwtProvider;
import net.maku.auth.service.AuthService;
import net.maku.auth.service.CaptchaService;
import net.maku.auth.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/oauth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private EmailService emailService;

    /**
     * 登录方法
     * <p>
     * loginAccount：user
     * password：123456
     *
     * @param loginInfo
     * @return ApiResult
     */
    @PostMapping("/login")
    public ApiResult login(@Valid @RequestBody LoginInfo loginInfo) {
        return authService.login(loginInfo.getLoginAccount(), loginInfo.getPassword());
    }

    @PostMapping("/logout")
    public ApiResult logout() {
        return authService.logout();
    }

    @PostMapping("/refresh")
    public ApiResult refreshToken(HttpServletRequest request) {
        return authService.refreshToken(jwtProvider.getToken(request));
    }

    @PostMapping("/generate-code")
    public ApiResult generateCode(@RequestParam(value = "email",required = true) String email) {
        return emailService.getCode(email);
    }

    @PostMapping("/query-code")
    public ApiResult queryCode(@RequestParam(value = "email",required = true) String email) {
        return emailService.queryCode(email);
    }



}
