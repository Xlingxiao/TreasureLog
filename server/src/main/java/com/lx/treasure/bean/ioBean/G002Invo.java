package com.lx.treasure.bean.ioBean;

import lombok.Data;

import java.util.Date;

/**
 * @Auther: Lx
 * @Date: 2020/2/27 20:12
 * @Description: 前端用户传过来的信息
 */

@Data
public class G002Invo extends BaseInvo {
    // 查询渠道 股票/基金
    String channel;
}
