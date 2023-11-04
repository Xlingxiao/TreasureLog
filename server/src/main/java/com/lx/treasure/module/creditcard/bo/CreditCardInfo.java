package com.lx.treasure.module.creditcard.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 信用卡信息表
 */
@Data
@Entity()
@Table(name = "credit_info")
public class CreditCardInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /** 账户 */
    private long userAccount;
    /** 年 */
    private String year;
    /** 月 */
    private String month;
    /** 交易日 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date tradeDate;
    /** 记账日 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date accountingDate;
    /** 卡号后四位 */
    private String accountNum;
    /** 交易描述 */
    private String tradeDetail;
    /** 交易币种 */
    private String tradeCurrency;
    /** 交易金额 */
    private double tradeAmount;
    /** 结算币种 */
    private String clearCurrency;
    /** 结算金额 */
    private double clearAmount;

}
