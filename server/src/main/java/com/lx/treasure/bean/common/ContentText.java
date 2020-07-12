package com.lx.treasure.bean.common;

/**
 * @Auther: Lx
 * @Date: 2020/6/27 17:52
 * @Description: 保存常用文字信息
 */
public class ContentText {

    public static final int TOKEN_ERROR_CODE = 401;
    public static final String TOKEN_ERROR = "Token校验不通过！";
    public static final String TOKEN_EMPTY_ERROR = "用户未登录！";
    public static final String TOKEN_INVALID_ERROR = "登录已失效请重新登录！";

    public static final int USER_INPUT_ERROR_CODE = 402;

    public static final int USER_OR_PASSWORD_ERR_CODE = USER_INPUT_ERROR_CODE;
    public static final String USER_OR_PASSWORD_ERR = "用户名或密码错误！";

    public static final int NO_REGISTER_CODE = USER_INPUT_ERROR_CODE;
    public static final String NO_REGISTER = "用户名或密码错误！";

    public static final int USER_ACCOUNT_ERROR_CODE = USER_INPUT_ERROR_CODE;
    public static final String USER_ACCOUNT_ERROR = "个人账号错误！";

    public static final int INPUT_ERROR_CODE = 403;
    public static final String INPUT_ERROR = "用户输入错误！";

    public static final int SERVER_ERROR_CODE = 500;
    public static final String SERVER_ERROR = "服务器内部错误！";


}
