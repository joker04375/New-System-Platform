package net.maku.auth.interceptor;

import net.maku.auth.annotation.LoginRequired;
import net.maku.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

// 对方法进行过滤，如果方法带有@LoginRequired注解，则需要进行是由有token判断，不是的话直接放行
public class LoginRequiredInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

//        Class<?> aClass = handlerMethod.getBean().getClass();
//        RestController annotation = handlerMethod.getBean().getClass().getAnnotation(RestController.class);
//        Annotation[] annotations = handlerMethod.getBean().getClass().getAnnotations();
        // 判断接口api是否需要登录，即判断接口api是否有@LoginRequired注解
        LoginRequired loginRequired = method.getAnnotation(LoginRequired.class);
        // 有 @LoginRequired 注解，需要认证
        if (loginRequired != null) {
            // 执行认证，如果有认证，则放行，没有认证，则返回错误消息
            String token = request.getHeader("token"); // 从 http 请求头中取出 token
            if (token == null) {
                throw new RuntimeException("没有token，请登录");
            }
            int userId;
            try {
//                userId = Integer.parseInt(JWT.decode(token).getAudience().get(0));  // 获取 token 中的 user id
            } catch (Exception e) {
                throw new RuntimeException("token无效，请重新登录");
            }
//            UserInfo user = userService.findUserInfo(userId);
//            if (user == null) {
//                throw new RuntimeException("用户不存在，请重新登录");
//            }
            // 验证 token
//            try {
//                JWTVerifier verifier =  JWT.require(Algorithm.HMAC256(user.getPassword())).build();
//                try {
//                    verifier.verify(token);
//                } catch (JWTVerificationException e) {
//                    throw new RuntimeException("token无效，请重新登录");
//                }
//            } catch (UnsupportedEncodingException ignore) {}
//            request.setAttribute("currentUser", user);
            return true;
        }
        return true;
    }
}
