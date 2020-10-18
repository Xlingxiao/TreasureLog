package com.lx.treasure.bean.repositoryBean;

import lombok.Data;

/**
 * @Auther: Lx
 * @Date: 2020/10/18 14:23
 * @Description: 支出信息
 */

@Data
public class SpendInfo {

    // 当月日均支出
    private double dailySpend;

    // 月支出总额
    private double monthSpend;

    // 年支出总额
    private double yearSpend;

    // 年收入
    private double annualIncome;

    // 月收入
    private double monthlyIncome;

    // 指定时间内的收入
    private double spanIncome;

    // 指定时间内的支出
    private double spanSpend;
}
