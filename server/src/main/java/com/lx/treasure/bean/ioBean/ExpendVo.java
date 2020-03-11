package com.lx.treasure.bean.ioBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Auther: Lx
 * @Date: 2020/2/28 13:31
 * @Description: 一条支出信息
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class ExpendVo extends BaseInvo{

    public ExpendVo() {
    }

    public ExpendVo(String info, double amount) {
        this.info = info;
        this.amount = amount;
    }

    // 购买信息
    private String info;

    // 花费的钱
    private double amount;

    // 是否必要 0 必要 1 不必要 别的字段留着
    private int essential;

    // 备注
    private String detail;
}
