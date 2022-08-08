package net.maku.auth.controller;

import net.maku.auth.annotation.LoginRequired;
import net.maku.auth.bo.ApiResult;
import net.maku.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test2")
public class Test2Conrtroller {
    @Autowired
    private UserService userService;

    @GetMapping("/test3")
    @Secured("ROLE_TEACHER")
    public ApiResult test() {
        return ApiResult.ok("访问/test2/test成功");
    }

    @GetMapping("/test2")
    @Secured("ROLE_USER")
    public ApiResult test2() {
        return ApiResult.ok("访问/test2/test2成功");
    }

    @GetMapping("/test")
    public ApiResult test05() {
        return ApiResult.ok("/test/test访问成功");
    }

    @GetMapping("/testAnnotationInterceptor")
    @LoginRequired
    public ApiResult testAnnotationInterceptor() {
        return ApiResult.ok("success");
    }
}
