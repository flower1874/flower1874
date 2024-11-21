package com.oj.onlinejudge.controller;


import com.oj.onlinejudge.common.ApiResponse;
import com.oj.onlinejudge.domain.po.Users;
import com.oj.onlinejudge.service.IUsersService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author flower
 * @since 2024-11-14
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

    private final IUsersService usersService;
    @PostMapping("/register")
    public ApiResponse<?> register(@RequestBody Users users) {
        boolean result = usersService.register(users);
        if (result) {
            return ApiResponse.success(Map.of("message", "注册成功"));
        } else {
            return ApiResponse.failure(400, "注册失败");
        }
    }

    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody Users users) {
        Users user = usersService.login(users.getUsername(), users.getPassword());
        if (user != null) {
            return ApiResponse.success(Map.of("message", "登录成功", "user", user));
        } else {
            return ApiResponse.failure(400, "用户名或密码错误");
        }
    }
}
