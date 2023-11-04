package com.lx.treasure.module.creditcard.service;

import com.lx.treasure.bean.ioBean.BaseInvo;
import com.lx.treasure.common.utils.DateUtils;
import com.lx.treasure.module.creditcard.bo.CreditCardInfo;
import com.lx.treasure.module.creditcard.repository.CreditCardRepository;
import com.lx.treasure.module.creditcard.vo.CreditCardInfoVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * 信用卡信息 service
 */
@Log4j2
@Service
public class CreditCardService {

    @Autowired
    CreditCardRepository mapper;

    /**
     * 获取信用卡信息
     *
     * @param invo 查询范围
     * @return 信用卡消费信息
     */
    public List<CreditCardInfoVo> getCreditCardInfo(BaseInvo invo) {
        List<CreditCardInfo> creditCardInfos = mapper.queryCreditCardInfo(invo.getUserAccount(), invo.getStartDate(), invo.getEndDate());
        List<CreditCardInfoVo> result = new LinkedList<>();
        for (CreditCardInfo creditCardInfo : creditCardInfos) {
            String detail = creditCardInfo.getTradeDetail();
            String channel = detail.split("-")[0];
            CreditCardInfoVo vo = new CreditCardInfoVo(creditCardInfo);
            vo.setChannel(channel);
            result.add(vo);
        }
        return result;
    }

    /**
     * 插入信用卡信息
     *
     * @param creditCardInfoList 信用卡消费信息
     * @return 插入后的信息
     */
    public List<CreditCardInfo> addCreditCardInfo(List<CreditCardInfo> creditCardInfoList) {
        for (CreditCardInfo creditCardInfo : creditCardInfoList) {
            String year = DateUtils.getYear(creditCardInfo.getTradeDate());
            String month = DateUtils.getMonth(creditCardInfo.getTradeDate());
            creditCardInfo.setMonth(month);
            creditCardInfo.setYear(year);
        }
        return mapper.saveAll(creditCardInfoList);
    }

    /**
     * @param textCreditCardInfo 信用卡消费信息 从邮件复制出来
     * @param userAccount 用户id
     * @return 数据库中保存好的信用卡信息
     */
    public List<CreditCardInfo> addCreditCardInfo(long userAccount, String textCreditCardInfo) {
        List<CreditCardInfo> creditCardInfoList = new LinkedList<>();
        List<CreditCardInfo> result = new LinkedList<>();
        try {
            String[] tradeInfoArray = textCreditCardInfo.split("\n");
            for (String tradeInfo : tradeInfoArray) {
                String[] tradeInfoTextArray = tradeInfo.split("\t");
                if (tradeInfoTextArray.length < 8) {
                    continue;
                }
                CreditCardInfo creditCardInfo = new CreditCardInfo();
                creditCardInfo.setTradeDate(DateUtils.stringToDate(tradeInfoTextArray[0].trim()));
                creditCardInfo.setAccountingDate(DateUtils.stringToDate(tradeInfoTextArray[1].trim()));
                creditCardInfo.setAccountNum(tradeInfoTextArray[2].trim());
                creditCardInfo.setTradeDetail(tradeInfoTextArray[3].trim());
                creditCardInfo.setTradeCurrency(tradeInfoTextArray[4].trim());
                String amount = tradeInfoTextArray[5].trim().replaceAll(",", "");
                creditCardInfo.setTradeAmount(Double.parseDouble(amount));
                creditCardInfo.setClearCurrency(tradeInfoTextArray[6].trim());
                amount = tradeInfoTextArray[7].trim().replaceAll(",", "");
                creditCardInfo.setClearAmount(Double.parseDouble(amount));
                creditCardInfo.setUserAccount(userAccount);
                creditCardInfoList.add(creditCardInfo);
            }
            result = addCreditCardInfo(creditCardInfoList);
        } catch (Exception e) {
            log.error("解析信用卡文字出错", e);
        }
        return result;
    }

}
