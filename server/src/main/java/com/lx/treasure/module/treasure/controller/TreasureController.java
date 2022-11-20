package com.lx.treasure.module.treasure.controller;


import com.lx.treasure.bean.common.CommonException;
import com.lx.treasure.bean.common.CommonResponse;
import com.lx.treasure.bean.common.SuccessResponse;
import com.lx.treasure.bean.ioBean.*;
import com.lx.treasure.module.treasure.mapper.Channel;
import com.lx.treasure.module.treasure.mapper.Info;
import com.lx.treasure.module.treasure.service.TreasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LX2
 */
@RestController
public class TreasureController {

    @Autowired
    private TreasureService treasureService;

    /**
     * 获取指定范围内的资产数据
     * @param baseInvo baseInvo
     * @return 指定范围内的资产数据
     */
    @PostMapping(value = "/getWealthCurve")
    public String getWealthCurve(@RequestBody BaseInvo baseInvo) {
        return treasureService.getWealthCurve(baseInvo);
    }

    /**
     * 接收前端发过来的数据
     * @param infoInvo 接收到的参数
     * @return 成功或失败的提示
     */
    @PostMapping(value = "/insert/ordinary")
    public SuccessResponse insertInfo(@RequestBody InfoInvo infoInvo) throws CommonException {
        if (StringUtils.isEmpty(infoInvo.getPay())) {
            throw  new CommonException(402, "pay不能为空");
        }
        if (StringUtils.isEmpty(infoInvo.getInfo())) {
            throw  new CommonException(402, "info不能为空");
        }
        if (StringUtils.isEmpty(infoInvo.getChannels())) {
            throw  new CommonException(402, "channels不能为空");
        }
        return treasureService.getInfoHandler(infoInvo);
    }

    /**
     *
     * @param invo 用户传过来的信息
     * @return 资产信息
     * @throws CommonException 常用报错
     */
    @PostMapping(value = "/getChannels")
    public ChannelsInfo getUserLatestChannels(@RequestBody BaseInvo invo) throws CommonException {
        long userAccount = invo.getUserAccount();
        return treasureService.getUserLatestChannels(userAccount);
    }


    /**
     * 获取主要支出
     * @param invo 用户信息
     * @return 主要支出
     * @throws CommonException 普通报错
     */
    @PostMapping(value = "/getMainExpend")
    public List<ExpendVo> getMainExpend(@RequestBody BaseInvo invo) throws CommonException {
        long userAccount = invo.getUserAccount();
        return treasureService.getMainExpendInfo(userAccount);
    }



    @PostMapping(value = "/insert/complete")
    public CommonResponse addCompleteLog(@RequestBody CompleteLog log){
        return treasureService.addCompleteLog(log);
    }
    /*-------------------TEST------------------*/

    @GetMapping(value = "/test/getChannel")
    public Channel findChannelByID(@RequestParam(name = "name", defaultValue = "1") long id) {
        return treasureService.findUserById(id);
    }

    @GetMapping(value = "/test/getInfo")
    public Info findInfoByID(@RequestParam(name = "name", defaultValue = "1") long id) {
        return treasureService.findInfoById(id);
    }
}
