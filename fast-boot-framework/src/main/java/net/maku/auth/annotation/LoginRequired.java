package net.maku.auth.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 该注解用来过滤某个方法是否需要登录之后才能访问，如果某个方法加上了这个注解，那么在自定义拦截器LoginRequiredInterceptor拦截器会被进一步做处理。LoginRequiredInterceptor是专门用来处理带有我们自定义注解的过滤器
@Target({ElementType.METHOD}) //表示该自定义注解可以用在方法上
@Retention(RetentionPolicy.RUNTIME) //表示该注解在代码运行时起作用
public @interface LoginRequired {
}
