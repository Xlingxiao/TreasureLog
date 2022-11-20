package com.lx.treasure.module.login.vo;

import com.lx.treasure.module.login.bo.UserInfo;
import lombok.Data;

/**
 * 页面展示值对象
 */

@Data
public class UserInfoVo extends UserInfo {

    /** 登录后发给前端的key */
    private String key;

    /** token */
    private String token;

    /** public key */
    private String publicKey;


}
