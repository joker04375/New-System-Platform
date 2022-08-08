package net.maku.auth.config;


import net.maku.auth.interceptor.LoginRequiredInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    @Bean
    public LoginRequiredInterceptor loginRequiredInterceptor() {
        return new LoginRequiredInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginRequiredInterceptor())
                .addPathPatterns("/**") //拦截所有路径
                .excludePathPatterns("/login", "/", "/exit", "/get_cpacha")//排除路径
                .excludePathPatterns("/xadmin/**");//排除静态资源拦截
    }
}
