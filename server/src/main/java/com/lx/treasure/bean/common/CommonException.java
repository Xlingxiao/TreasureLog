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

    int resultCode;
    String message;

    public CommonException(int resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }
}
