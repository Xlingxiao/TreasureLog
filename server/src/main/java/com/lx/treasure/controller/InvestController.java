package com.lx.treasure.controller;

import com.lx.treasure.bean.common.CommonResponse;
import com.lx.treasure.bean.ioBean.G002Invo;
import com.lx.treasure.bean.ioBean.InvestInfo;
import com.lx.treasure.bean.repositoryBean.Invest;
import com.lx.treasure.service.InvestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: Lx
 * @Date: 2020/3/6 19:48
 * @Description: 投资相关入口
 */

@RestController
@RequestMapping("/invest")
public class InvestController {

    @Autowired
    InvestService investService;

    /**
     * 记录一笔投资状态
     *
     * @param investList 投资输入
     * @return 普通返回
     */
    @PostMapping("/onceLog")
    public CommonResponse logInvest(@RequestBody List<Invest> investList) {
        return investService.logOnceInvest(investList);
    }

    /**
     * 基金信息获取
     * @param invo 个人账号和查询时间范围
     * @return 基金信息
     */
    @PostMapping(value = "/index")
    public InvestInfo getFundInfoByUserAccount(@RequestBody G002Invo invo) {
        return investService.getFundInfo(invo);
    }
}
