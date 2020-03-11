package com.lx.treasure.bean.ioBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Auther: Lx
 * @Date: 2020/2/27 11:28
 * @Description: 投资信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InvestInfo extends BaseInvo{

    public InvestInfo() {
    }

    public InvestInfo(long userAccount, List<Double> fundGross, List<Double> fundInvest, List<Double> fundGain, List<String> dates) {
        this.setUserAccount(userAccount);
        this.fundGross = fundGross;
        this.fundInvest = fundInvest;
        this.fundGain = fundGain;
        this.dates = dates;
    }

    // 总额
    List<Double> fundGross;
    // 投资额
    List<Double> fundInvest;
    // 盈利
    List<Double> fundGain;
    // 时间
    List<String> dates;
}
