package com.oj.onlinejudge.service.impl;

import com.oj.onlinejudge.domain.po.Users;
import com.oj.onlinejudge.mapper.UsersMapper;
import com.oj.onlinejudge.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author flower
 * @since 2024-11-14
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

}
