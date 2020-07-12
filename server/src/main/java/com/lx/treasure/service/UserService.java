package com.lx.treasure.service;

import com.alibaba.fastjson.JSONObject;
import com.lx.treasure.bean.common.CommonException;
import com.lx.treasure.bean.common.ContentText;
import com.lx.treasure.bean.user.UserInfo;
import com.lx.treasure.common.utils.JWTUtils;
import com.lx.treasure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Auther: Lx
 * @Date: 2020/6/25 16:23
 * @Description: 用户服务
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    /**
     * 处理用户登录，
     * 1. 校验
     * 2. 获取用户信息
     * 3. 生成token
     *
     * @param userInfo 用户信息
     * @return 用户信息+token
     */
    public String loginService(UserInfo userInfo) throws CommonException {
        UserInfo userInfoByDB = userRepository.findUserByAccount(userInfo.getUserAccount());
        checkPassword(userInfo, userInfoByDB);
        userInfoByDB.setPassword(null);
        JSONObject result = JSONObject.parseObject(JSONObject.toJSONString(userInfoByDB));
        String token = JWTUtils.paramsToToken("" + userInfoByDB.getUserType(), result);
        result.put("Token", token);
        System.out.println("token:" + token);
        return result.toJSONString();
    }

    /**
     * 校验用户是否有权限进行登录
     * @param userInfoByDB 数据库中查到的用户信息
     * @param userInfo 用户输入的用户信息
     * @throws CommonException 校验不通过。
     */
    private void checkPassword(UserInfo userInfoByDB, UserInfo userInfo) throws CommonException {
        if (userInfo == null || userInfoByDB == null) {
            throw new CommonException(ContentText.NO_REGISTER_CODE, ContentText.NO_REGISTER);
        }
        String userPassword = userInfo.getPassword();
        String dbPassword = userInfoByDB.getPassword();
        if (!userPassword.equals(dbPassword)) {
            throw new CommonException(ContentText.USER_OR_PASSWORD_ERR_CODE, ContentText.USER_OR_PASSWORD_ERR);
        }
    }
}
