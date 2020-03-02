package com.lx.treasure.bean.ioBean;

import lombok.Data;

import java.util.Date;

/**
 * @Auther: Lx
 * @Date: 2020/2/27 20:12
 * @Description: 前端用户传过来的信息
 */

@Data
public class G002Invo {
    // 个人账号
    long userAccount;
    // 开始时间
    Date startDate;
    // 结束时间
    Date endDate;
}
