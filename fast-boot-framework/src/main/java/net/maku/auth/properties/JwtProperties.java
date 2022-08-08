package net.maku.auth.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    /**
     * 密匙
     */
    @Value("${jwt.secret}")
    private String apiSecretKey = "JWT_SECRET_KEY";

    /**
     * token过期时间-默认半个小时
     */
    @Value("${jwt.expiration.accessTime}")
    private Long expirationTime = 1800L;


    /**
     * RefreshToken过期时间-默认为七天
     */
    @Value("${jwt.expiration.refreshTime}")
    private Long expirationRefreshTime = 604800L;

    /**
     * 默认存放token的请求头
     */
    private String requestHeader = "Authorization";

    /**
     * 默认token前缀
     */
    private String tokenPrefix = "Bearer ";
}
