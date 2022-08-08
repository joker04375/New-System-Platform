package net.maku.auth.config;

import net.maku.auth.filter.JwtAuthenticationTokenFilter;
import net.maku.auth.filter.ValidateCodeFilter;
import net.maku.auth.handler.JwtAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 放行所有OPTIONS请求
                .antMatchers(HttpMethod.OPTIONS).permitAll()
//                .and()
//                .authorizeHttpRequests(authorize ->               //http.authorizeHttpRequests()->用以指定哪些请求需要什么样的认证或授权，这里使用 anyRequest() 和 authenticated() 表示所有的请求均需要认证。
//                        authorize
//                                .anyRequest()
//                                .authenticated())
                // 放行登录方法
//                .antMatchers("/test/*").hasRole("STU")
                .antMatchers("/test/**").permitAll()
                .antMatchers("/test2/test").permitAll()
//                .antMatchers("/test2/*").hasRole("TEACHER")
                .antMatchers("/api/oauth/*").permitAll()
                .antMatchers("/api/oauth/login").permitAll()
                .antMatchers("/api/oauth/refresh").permitAll()
                .antMatchers("/api/anon").permitAll()
                .antMatchers("/api/captcha/getcap").permitAll()
//                .antMatchers("/sys/college").hasRole("TEACHER")
//                .antMatchers("/sys/stu").hasRole("STU")
//                .antMatchers("/sys/enterprise").hasRole("ENTERPRISE")
                // 其他请求都需要认证后才能访问
                .anyRequest().authenticated()
                .and()
//                // 添加未登录与权限不足异常处理器
                .exceptionHandling()
//                .accessDeniedHandler(restfulAccessDeniedHandler())     // 权限问题 鉴权失败异常处理器
                .authenticationEntryPoint(restAuthenticationEntryPoint())   // token问题  认证失败异常处理器
                .and()
                // 将自定义的JWT过滤器放到过滤链中
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(validateCodeFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/login") // 规定未认证跳转的页面
                .loginProcessingUrl("/login")
                .and()
                // 打开Spring Security的跨域
                // 关闭CSRF
                .csrf().disable()
                // 关闭Session机制
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public JwtAuthenticationEntryPoint restAuthenticationEntryPoint(){
        return new JwtAuthenticationEntryPoint();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public ValidateCodeFilter validateCodeFilter() {
        return new ValidateCodeFilter();
    }

    // 在内存介质中增加用户（这是不基于数据库的用户存储实现）
//    @Bean
//    public UserDetailsManager users() {
//        UserDetails user = User.builder().username("userA").password("{bcrypt}$2a$10$CrPsv1X3hM.giwVZyNsrKuaRvpJZyGQycJg78xT7Dm68K4DWN/lxS").roles("USER").build();
//        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
//        inMemoryUserDetailsManager.createUser(user);
//        return inMemoryUserDetailsManager;
//    }

    // 基于数据库的用户增加
//    @Autowired
//    private DataSource dataSource;
//
//    @Bean
//    public UserDetailsManager users() {
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{bcrypt}$2a$10$CrPsv1X3hM" +
//                        ".giwVZyNsrKuaRvpJZyGQycJg78xT7Dm68K4DWN/lxS")
//                .roles("USER")
//                .build();
//
//        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
//        manager.createUser(user);
//
//        return manager;
//    }
}
