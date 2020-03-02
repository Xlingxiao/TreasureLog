package com.lx.treasure.controller;


import com.lx.treasure.bean.common.CommonException;
import com.lx.treasure.bean.common.SuccessResponse;
import com.lx.treasure.bean.ioBean.*;
import com.lx.treasure.bean.repositoryBean.Channel;
import com.lx.treasure.bean.repositoryBean.Info;
import com.lx.treasure.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author LX2
 */
@RestController
public class SimpleController {

    @Autowired
    private ChannelService channelService;

    /**
     * 接收前端发过来的数据
     * @param infoInvo 接收到的参数
     * @return 成功或失败的提示
     */
    @PostMapping(value = "/insert/ordinary")
    public SuccessResponse insertInfo(@RequestBody InfoInvo infoInvo) throws CommonException {
        if (StringUtils.isEmpty(infoInvo.getPay())) {
            throw  new CommonException("2", "pay不能为空");
        }
        if (StringUtils.isEmpty(infoInvo.getExpenditure())) {
            throw  new CommonException("2", "expenditure不能为空");
        }
        if (StringUtils.isEmpty(infoInvo.getInfo())) {
            throw  new CommonException("2", "info不能为空");
        }
        if (StringUtils.isEmpty(infoInvo.getChannels())) {
            throw  new CommonException("2", "channels不能为空");
        }
        return channelService.getInfoHandler(infoInvo);
    }

    /**
     *
     * @param data 用户传过来的信息
     * @return 资产信息
     * @throws CommonException 常用报错
     */
    @PostMapping(value = "/getChannels")
    public ChannelsInfo getUserLatestChannels(@RequestBody Map<String,String> data) throws CommonException {
        long userAccount = Long.parseLong(data.get("userAccount"));
        return channelService.getUserLatestChannels(userAccount);
    }

    /**
     * 基金信息获取
     * @param invo 个人账号和查询时间范围
     * @return 基金信息
     */
    @PostMapping(value = "/getFundInfoByUserAccount")
    public InvestInfo getFundInfoByUserAccount(@RequestBody G002Invo invo) {
        return channelService.getFundInfo(invo);
    }

    /**
     * 获取主要支出
     * @param request 用户信息
     * @return 主要支出
     * @throws CommonException 普通报错
     */
    @PostMapping(value = "/getMainExpend")
    public List<KVBean> getMainExpend(@RequestBody Map<String, Long> request) throws CommonException {
        long userAccount = request.get("userAccount");
        if (StringUtils.isEmpty(userAccount)) {
            throw new CommonException("400", "个人账号为空");
        }
        return channelService.getMainExpendInfo(userAccount);
    }
    /*-------------------TEST------------------*/

    @GetMapping(value = "/test/getChannel")
    public Channel findChannelByID(@RequestParam(name = "name", defaultValue = "1") long id) {
        return channelService.findUserById(id);
    }

    @GetMapping(value = "/test/getInfo")
    public Info findInfoByID(@RequestParam(name = "name", defaultValue = "1") long id) {
        return channelService.findInfoById(id);
    }
}
