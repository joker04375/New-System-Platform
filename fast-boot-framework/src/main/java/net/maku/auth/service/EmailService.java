package net.maku.auth.service;


import net.maku.auth.bo.ApiResult;

public interface EmailService {
    /**
     * 发送验证码到指定邮箱
     * @param sender 发送地址
     * @param mailSender spring自带
     * @param receiver 接受地址
     */
//    ApiResult getCode(String sender, JavaMailSenderImpl mailSender,String receiver);

    ApiResult getCode(String receiver);

    ApiResult queryCode(String account);

}
