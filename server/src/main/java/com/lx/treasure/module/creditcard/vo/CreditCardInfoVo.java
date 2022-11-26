package com.lx.treasure.module.creditcard.vo;

import com.lx.treasure.module.creditcard.bo.CreditCardInfo;
import lombok.Data;

@Data
public class CreditCardInfoVo extends CreditCardInfo {
    /** 渠道 */
    private String channel;

    public CreditCardInfoVo() {
    }

    public CreditCardInfoVo(CreditCardInfo info) {
        this.setUserAccount(info.getUserAccount());
        this.setMonth(info.getMonth());
        this.setYear(info.getYear());
        this.setAccountingDate(info.getAccountingDate());
        this.setTradeDate(info.getTradeDate());
        this.setAccountNum(info.getAccountNum());
        this.setTradeDetail(info.getTradeDetail());
        this.setTradeAmount(info.getTradeAmount());
        this.setTradeCurrency(info.getTradeCurrency());
        this.setClearAmount(info.getClearAmount());
        this.setClearCurrency(info.getClearCurrency());


    }
}
