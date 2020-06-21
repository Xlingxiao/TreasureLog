package com.lx.treasure.controller;

import com.lx.treasure.bean.common.CommonResponse;
import com.lx.treasure.bean.ioBean.BaseInvo;
import com.lx.treasure.bean.ioBean.G002Invo;
import com.lx.treasure.bean.ioBean.InvestInfo;
import com.lx.treasure.bean.repositoryBean.Invest;
import com.lx.treasure.service.InvestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
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
     * 获取投资信息
     * @param invo 个人账号和查询时间范围
     * @return 基金信息
     */
    @PostMapping(value = "/getInfo")
    public InvestInfo getFundInfoByUserAccount(@RequestBody G002Invo invo) {
        handleBaseInvo(invo);
        return investService.getFundInfo(invo);
    }

    /**
     * 处理baseInvo
     * 当没有指定开始时间/结束时间时设置为当前时间
     * 当没有指定查询条数时设置查询条数为最大值
     * @param invo baseInvo
     */
    private void handleBaseInvo(BaseInvo invo) {
        if (invo.getFindLimit() == 0) {
            invo.setFindLimit(Integer.MAX_VALUE);
        }
    }
}
