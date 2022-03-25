package cn.njupt.votingsystem.service.impl;

import cn.njupt.votingsystem.model.UserDetail;
import cn.njupt.votingsystem.pojo.User;
import cn.njupt.votingsystem.repository.UserRepository;
import cn.njupt.votingsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/10/25
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.error("userName = " + userName);
        User u = userRepository.findByUserName(userName);
        if (u == null) throw new BadCredentialsException("用户名不存在或者密码错误！");
        log.error(u.toString());
        return new UserDetail(u);
    }

    @Override
    public boolean vote() {
        return true;
    }
}
