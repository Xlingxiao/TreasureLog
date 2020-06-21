package com.lx.treasure.bean.ioBean;


import lombok.Data;

import java.util.Date;

/**
 * @Auther: Lx
 * @Date: 2020/3/6 19:37
 * @Description: 输入参数中基本的参数
 */

@Data
public class BaseInvo {

    public BaseInvo() {
    }

    // 个人账号
    private long userAccount;
    // 开始时间
    private Date startDate;
    // 结束时间
    private Date endDate;
    // 限制个数
    private int findLimit;
}
