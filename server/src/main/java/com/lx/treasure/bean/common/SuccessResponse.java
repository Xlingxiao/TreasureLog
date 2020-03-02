package com.lx.treasure.bean.common;

import org.springframework.stereotype.Component;

@Component
public class SuccessResponse extends CommonResponse{
    public SuccessResponse() {
        this.resultCode = "0";
        this.message = "SUCCESS";
    }
}
