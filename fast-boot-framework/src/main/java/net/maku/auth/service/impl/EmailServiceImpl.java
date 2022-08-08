package net.maku.auth.service.impl;

import net.maku.auth.bo.ApiResult;
import net.maku.auth.exception.ErrorCode;
import net.maku.auth.properties.EmailSettings;
import net.maku.auth.service.EmailService;
import net.maku.auth.utils.RedisKeys;
import net.maku.auth.utils.RedisUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);


    @Value("${email.expired-time}")
    private Long expiredTime;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private EmailSettings emailSettings;

    @Autowired
    private RedisUtils redisUtils;

    //@Async表示异步，可以在邮件未发送完成时就返回，而不必等待太长时间，必须在总配置类上加@EnableAsync注解才可以生效
    /**
     * 发送验证码到指定邮箱
     * @param receiver 接受地址
     */
    @Override
    @Async
    public ApiResult getCode(String receiver) {
        if (StringUtils.isEmpty(receiver)) {
            LOGGER.error("邮箱不能为空");
            return ApiResult.error("邮箱不能为空");
        }
        try {
            final Properties props = new Properties();
            /**注意发送邮件的方法中，发送给谁的，发送给对应的app，※
             *要改成对应的app。扣扣的改成qq的，网易的要改成网易的。※
             *props.put("mail.smtp.host", "smtp.qq.com");
             */
            props.put("mail.smtp.host", emailSettings.getHost());
    //        props.put("mail.smtp.port", emailSettings.getPort());
            props.setProperty("mail.smtp.auth", "true");//使用smtp身份验证
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailSettings.getFrom(), emailSettings.getPassword());
                }
            };
            Session mailSession = Session.getInstance(props, authenticator);
            MimeMessage message = new MimeMessage(mailSession);
            // 设置发件人
            String nickName = "leo";
            try {
                nickName = MimeUtility.encodeText(emailSettings.getUsername());//自定义发件人名称
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("自定义发件人名称编码失败" + e);
            }
            InternetAddress form = new InternetAddress(nickName + "<" + emailSettings.getFrom() + ">");
            message.setFrom(form);
            InternetAddress toAddress = new InternetAddress(receiver);// 设置收件人
            message.setRecipient(Message.RecipientType.TO, toAddress);
            message.setSubject("找回密码");// 设置邮件标题
//            message.setSubject(verCodeInfo.getTitle());// 设置邮件标题

            //生成6位随机验证码
            String code = RandomStringUtils.randomNumeric(6);
            //放置缓存
            redisUtils.set(RedisKeys.getEmailKey(receiver),code,expiredTime);

            Context context = new Context();
            context.setVariable("verifyCode", code);
            String emailContent = templateEngine.process("restoreTemplate", context);
            message.setContent(emailContent, "text/html;charset=UTF-8");// 设置邮件的内容体
            Transport.send(message);// 发送邮件
            return ApiResult.ok();
        } catch (Exception e) {
            LOGGER.error("failed to send email, receiver:{}" + e, receiver);
        }
        return ApiResult.fail("发送邮件失败");
    }

    @Override
    public ApiResult queryCode(String account) {
        if (StringUtils.isEmpty(account)) {
            LOGGER.error("邮箱不能为空");
            return ApiResult.error("邮箱不能为空");
        }
        String code = (String) redisUtils.get(RedisKeys.getEmailKey(account));
        if (code == null) {
            LOGGER.error("验证码不存在");
            return ApiResult.error(ErrorCode.EMAIL_EXPIRED_ERROR);
        }
        return ApiResult.ok(code);
    }
}
