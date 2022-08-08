package net.maku.auth.controller;

import net.maku.auth.bo.ApiResult;
import net.maku.auth.bo.TestInfo;
import net.maku.auth.utils.CaptchaCodeUtils;
import net.maku.auth.utils.RedisUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Random;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("/anon")
    public ApiResult test01() {
        return ApiResult.ok("匿名访问成功");
    }

    @GetMapping("/user")
    public ApiResult test02() {
        return ApiResult.ok("USER用户访问成功");
    }

    @GetMapping("/admin")
    public ApiResult test03() {
        return ApiResult.ok("管理员用户访问成功");
    }

    @PostMapping("/test/test")
    public ApiResult test04(@Valid @RequestBody TestInfo testInfo) {
        return ApiResult.ok(testInfo);
//        return ApiResult.ok("TEST用户访问成功");
    }

    // 生成验证码图片
    @GetMapping("/captcha-code")
    public void generateCaptchaCode(HttpServletResponse response) throws IOException {
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("image/jpeg");

        // 生成6位字母+数字的验证码， 验证码放入缓存
        String alphabet = "ABCEDFGHIJKLMNOPQRSTUVWXYZ1234567890";
        // 唯一id，用来进行在缓存中的标识
        StringBuilder randomNum = new StringBuilder();
        Random random = new Random();
        int length = 6;
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            randomNum.append(randomChar);
        }
//        String captchaId = idGenerator.next("CAP");
        CaptchaCodeUtils.CapchaCodeResult captcha = CaptchaCodeUtils.generateRandomCode(40, 6, 150);
        if (captcha == null) {
            response.sendError(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        redisUtils.set(randomNum.toString(),captcha.getCode());
        response.setHeader("Content-Disposition","attachment;fileName=" + URLEncoder.encode(captcha.getCode() + "." + captcha.getExtension(),"UTF-8"));
        response.setContentLengthLong(captcha.getData().length);
        OutputStream out = response.getOutputStream();
        out.write(captcha.getData());
        out.flush();
    }
}
