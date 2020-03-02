package com.lx.treasure.bean.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通用异常处理
 * @author LX2
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommonException extends Exception {

    String resultCode;
    String message;

    public CommonException(String resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }
}
