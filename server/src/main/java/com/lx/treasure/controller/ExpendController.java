package com.lx.treasure.controller;

import com.lx.treasure.bean.common.CommonResponse;
import com.lx.treasure.bean.ioBean.BaseInvo;
import com.lx.treasure.bean.ioBean.ExpendVo;
import com.lx.treasure.bean.repositoryBean.Expend;
import com.lx.treasure.service.ExpendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<Expend> getExpendInfo(BaseInvo invo) {
        long userAccount = invo.getUserAccount();
        return expendService.getExpendInfo(userAccount);
    }

    @PostMapping("/log")
    public CommonResponse addExpend(@RequestBody List<ExpendVo> expendList) {
        return expendService.addExpend(expendList);
    }


}
