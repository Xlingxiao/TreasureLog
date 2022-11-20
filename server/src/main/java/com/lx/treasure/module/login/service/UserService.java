package com.lx.treasure.module.login.service;

import com.alibaba.fastjson.JSONObject;
import com.lx.treasure.bean.common.CommonException;
import com.lx.treasure.bean.common.ContentText;
import com.lx.treasure.common.utils.AESUtils;
import com.lx.treasure.common.utils.RSAUtils;
import com.lx.treasure.module.common.bo.AesInfo;
import com.lx.treasure.module.common.service.AESKeyService;
import com.lx.treasure.module.login.bo.UserInfo;
import com.lx.treasure.common.utils.JWTUtils;
import com.lx.treasure.module.login.repository.UserRepository;
import com.lx.treasure.module.login.vo.UserInfoVo;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * @Auther: Lx
 * @Date: 2020/6/25 16:23
 * @Description: 用户服务
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AESKeyService aesKeyService;

    /**
     * 保存用户的AES信息
     * 1. 解密密钥信息
     * 2. 存入信息
     */
    public String saveUserAesInfo(String userAccount, String key, String iv) {
        try {
            String decodeIv = RSAUtils.decrypt(iv);
            String decodeKey = RSAUtils.decrypt(key);
            AesInfo aesInfo = new AesInfo(decodeIv, decodeKey);
            aesKeyService.saveAesInfo(userAccount, aesInfo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("密钥进行Rsa解密失败");
        }
        return userAccount;
    }

    /**
     * 处理用户登录，
     * 1. 校验
     * 2. 获取用户信息
     * 3. 生成token
     *
     * @param userInfo 用户信息
     * @return 用户信息+token
     */
    public UserInfoVo loginService(UserInfo userInfo) throws CommonException {
        UserInfo userInfoByDB = userRepository.findUserByAccount(userInfo.getUserAccount());
        checkPassword(userInfo, userInfoByDB);

        JSONObject result = JSONObject.parseObject(JSONObject.toJSONString(userInfoByDB));
        String token = JWTUtils.paramsToToken("" + userInfoByDB.getUserType(), result);
        result.put("Token", token);
        log.info("token:{}", token);
        String publicKey = RSAUtils.getPublicKey();
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfoByDB, userInfoVo);
        userInfoVo.setPassword(null);
        userInfoVo.setToken(token);
        userInfoVo.setPublicKey(publicKey);
        return userInfoVo;
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
