package com.lx.treasure.module.login.controller;

import com.alibaba.fastjson.JSONObject;
import com.lx.treasure.bean.common.CommonException;
import com.lx.treasure.module.login.bo.UserInfo;
import com.lx.treasure.module.login.service.UserService;
import com.lx.treasure.module.login.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public UserInfoVo login(@RequestBody String request) throws CommonException {
        JSONObject userInfoJson = JSONObject.parseObject(request);
        final String userAccount = userInfoJson.getString("userAccount");
        final String key = userInfoJson.getString("key");
        final String iv = userInfoJson.getString("iv");
        final String s = userService.saveUserAesInfo(userAccount, key, iv);
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        UserInfo userInfo = userInfoJson.toJavaObject(UserInfo.class);
        return userService.loginService(userInfo);
    }
}
