package com.lx.treasure.controller;

import com.lx.treasure.bean.common.CommonException;
import com.lx.treasure.bean.user.UserInfo;
import com.lx.treasure.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Auther: Lx
 * @Date: 2020/6/25 16:19
 * @Description: 用户相关
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/login")
    public String login(@RequestBody UserInfo userInfo) throws CommonException {
        return userService.loginService(userInfo);
    }
}
