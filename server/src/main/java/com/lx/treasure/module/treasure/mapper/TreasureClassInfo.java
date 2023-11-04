package com.lx.treasure.module.treasure.mapper;

import lombok.Data;



@Data
public class TreasureClassInfo {

    /** info id */
    long infoId;
    /** 账号 */
    String userAccount;
    /** 记录日期 */
    String date;
    /** 薪水 */
    String pay;
    /** 支出 */
    String expenditure;
    /** 现金 */
    String money;
    /** 理财 */
    String invest;
    /** 负债 */
    String debt;
    /** 总资产 */
    String total;
}
