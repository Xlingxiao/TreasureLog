package com.lx.treasure.bean.ioBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Auther: Lx
 * @Date: 2020/3/6 19:42
 * @Description: 输入的一笔投资信息
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class InvestInVo extends BaseInvo {

    // 投资渠道
    private String channel;

    // 投入额
    private double invest;

    // 投资额度
    private double gross;
}

