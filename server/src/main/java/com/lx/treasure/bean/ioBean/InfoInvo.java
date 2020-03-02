package com.lx.treasure.bean.ioBean;

import lombok.Data;

import java.util.Map;

/**
 * 前端输入的一条记录
 */

@Data
public class InfoInvo {
    // 个人账号
    private long userAccount;
    // 薪水
    private double pay;
    // 花销
    private double expenditure;
    // 描述
    private String info;
    // 渠道
    private Map<String, Map<String, Double>> channels;
}
