package com.lx.treasure.module.treasure.controller;

import com.lx.treasure.bean.common.CommonResponse;
import com.lx.treasure.bean.ioBean.BaseInvo;
import com.lx.treasure.bean.ioBean.ExpendVo;
import com.lx.treasure.module.treasure.mapper.SpendInfo;
import com.lx.treasure.module.treasure.mapper.Expend;
import com.lx.treasure.module.treasure.service.ExpendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: Lx
 * @Date: 2020/3/9 20:36
 * @Description: 消费相关Controller
 */
@RestController
@RequestMapping("/expend")
public class ExpendController {

    @Autowired
    ExpendService expendService;

    @PostMapping("/getInfo")
    public List<Expend> getExpendInfo(@RequestBody BaseInvo invo) {
        long userAccount = invo.getUserAccount();
        return expendService.getExpendInfo(userAccount, invo.getStartDate(), invo.getEndDate());
    }

    @PostMapping("/log")
    public CommonResponse addExpend(@RequestBody List<ExpendVo> expendList) {
        return expendService.addExpend(expendList);
    }

    @PostMapping("/getSpendInfo")
    public SpendInfo getSpendInfo(@RequestBody BaseInvo invo) {
        return expendService.getSpendInfo(invo);
    }

}
