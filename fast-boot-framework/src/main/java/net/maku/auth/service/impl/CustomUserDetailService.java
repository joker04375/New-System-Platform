package net.maku.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.maku.auth.entity.UserDetail;
import net.maku.auth.entity.UserInfo;
import net.maku.auth.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service("userDetailsService")
public class CustomUserDetailService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailService.class);

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.debug("开始登陆验证，用户名为: {}",username);

        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserInfo::getUsername,username);
        UserInfo userinfo = userService.getOne(wrapper);
        if (userinfo == null) {
            throw new UsernameNotFoundException("用户名不存在，登陆失败。");
        }

        UserDetail userDetail = new UserDetail();
        userDetail.setUserInfo(userinfo);
        return userDetail;
    }
}
