package com.oj.onlinejudge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oj.onlinejudge.domain.po.Users;
import com.oj.onlinejudge.mapper.UsersMapper;
import com.oj.onlinejudge.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author flower
 * @since 2024-11-14
 */
@Service
@RequiredArgsConstructor
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

    private final PasswordEncoder passwordEncoder;
    @Override
    public boolean register(Users users) {
        // 验证输入参数
        if (users.getUsername() == null || users.getPassword() == null || users.getRole() == null) {
            return false;
        }

        // 密码加密
        String encodedPassword = passwordEncoder.encode(users.getPassword());
        users.setPassword(encodedPassword);

        // 设置默认值
        users.setLevel(1);
        users.setLastLogin(null);
        users.setCreatedAt(LocalDateTime.now());
        users.setUpdatedAt(LocalDateTime.now());

        // 保存用户信息
        return save(users);
    }
    @Override
    public Users login(String username, String password) {
        // 查询用户信息
        Users user = getOne(new QueryWrapper<Users>().eq("username", username));
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // 更新最后登录时间
            user.setLastLogin(LocalDateTime.now());
            updateById(user);
            return user;
        }
        return null;
    }
}
