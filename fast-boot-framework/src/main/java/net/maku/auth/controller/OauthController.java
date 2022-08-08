package net.maku.auth.controller;

import cn.hutool.core.lang.UUID;
import net.maku.auth.bo.ApiResult;
import net.maku.auth.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/captcha")
public class OauthController {

    @Autowired
    private CaptchaService captchaService;

    @GetMapping("getcap")
    public ApiResult captcha() {
        // 生成key
        String key = UUID.randomUUID().toString();
        // 生成base64验证码
        String image = captchaService.generate(key);

        // 封装返回数据
        Map<String, Object> data = new HashMap<>();
        data.put("key", key);
        data.put("image", image);

        return ApiResult.ok(data);
    }
}
