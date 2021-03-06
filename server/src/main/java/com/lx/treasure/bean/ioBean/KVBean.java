package com.lx.treasure.bean.ioBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Auther: Lx
 * @Date: 2020/2/29 14:51
 * @Description: 存储最简单的K-V对象
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class KVBean extends BaseInvo{

    public KVBean(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public KVBean() {
    }

    private String key;

    private String value;
}

