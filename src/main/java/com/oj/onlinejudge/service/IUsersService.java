package com.oj.onlinejudge.service;

import com.oj.onlinejudge.domain.po.Users;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author flower
 * @since 2024-11-14
 */
public interface IUsersService extends IService<Users> {
    boolean register(Users users);
    Users login(String username, String password);

}
