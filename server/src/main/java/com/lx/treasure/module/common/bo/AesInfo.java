package com.lx.treasure.module.common.bo;

import lombok.Data;

@Data
public class AesInfo {

    public AesInfo(String iv, String key) {
        this.iv = iv;
        this.key = key;
    }

    public AesInfo() {
    }

    private String iv;

    private String key;
}
