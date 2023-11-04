package com.lx.treasure.module.creditcard.controller;

import com.alibaba.fastjson.JSONObject;
import com.lx.treasure.bean.ioBean.BaseInvo;
import com.lx.treasure.module.creditcard.bo.CreditCardInfo;
import com.lx.treasure.module.creditcard.service.CreditCardService;
import com.lx.treasure.module.creditcard.vo.CreditCardInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 信用卡信息相关操作
 */
@RestController
@RequestMapping("/creditCard")
public class CreditCardInfoController {

    @Autowired
    CreditCardService service;

    /**
     * 获取信用卡消费信息
     * @param invo 查询条件
     * @return 信用卡消费信息
     */
    @PostMapping("/getInfo")
    public List<CreditCardInfoVo> getInfo(@RequestBody BaseInvo invo) {
        List<CreditCardInfoVo> result = service.getCreditCardInfo(invo);
        return result;
    }

    /**
     * 添加信用卡消费信息
     * @param creditCardInfoList 信用卡消费信息
     * @return 已经存储的信用卡消费信息
     */
    @PostMapping("addCredit")
    public List<CreditCardInfo> addTradeInfo(@RequestBody List<CreditCardInfo> creditCardInfoList) {
        return service.addCreditCardInfo(creditCardInfoList);
    }

    /**
     * 添加信用卡消费信息
     * @param creditCardInfoListText 信用卡消费信息 邮件直接复制版
     * @return 已经存储的信用卡消费信息
     */
    @PostMapping("addCreditText")
    public List<CreditCardInfo> addTradeInfo(@RequestBody String request) {
        JSONObject jsonObject = JSONObject.parseObject(request);
        String creditCardInfoListText = jsonObject.getString("requestContent");
        long userAccount = jsonObject.getLong("userAccount");
        return service.addCreditCardInfo(userAccount, creditCardInfoListText);
    }

}
