package net.maku.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.maku.auth.entity.UserInfo;
import net.maku.auth.mapper.UserMapper;
import net.maku.auth.service.UserService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserInfo> implements UserService {
}
