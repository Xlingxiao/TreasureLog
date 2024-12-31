package com.lx.treasure.module.treasure.controller;


import com.alibaba.fastjson.JSONObject;
import com.lx.treasure.bean.common.CommonException;
import com.lx.treasure.bean.common.CommonResponse;
import com.lx.treasure.bean.common.SuccessResponse;
import com.lx.treasure.bean.ioBean.*;
import com.lx.treasure.module.treasure.mapper.Channel;
import com.lx.treasure.module.treasure.mapper.Info;
import com.lx.treasure.module.treasure.service.TreasureService;
import com.lx.treasure.module.treasure.vo.InfoVo;
import com.lx.treasure.module.treasure.vo.TreasureClassInfoVo;
import com.lx.treasure.module.treasure.vo.TreasureStatus;
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


    /**
     * 获取账户所有收支记录
     * @param invo 个人账号
     * @return 账户所有收支记录
     */
    @PostMapping("/getIncomeInfoList")
    public List<Info> getInfoList(@RequestBody BaseInvo invo) {
        long userAccount = invo.getUserAccount();
        return treasureService.getUserIncomeExpend(userAccount);
    }

    /**
     * 获取个人指定时间的所有财富渠道状态
     * @param invo 个人账号 info id
     * @return 个人指定时间的所有财富渠道状态
     */
    @PostMapping("/getTreasureStatus")
    public TreasureStatus getTreasureStatus(@RequestBody InfoVo invo) {
        long userAccount = invo.getUserAccount();
        long infoId = invo.getInfoId();
        return treasureService.getUserChannels(userAccount, infoId);
    }


    @PostMapping("/getTreasureClassInfo")
    public List<TreasureClassInfoVo> getTreasureClassInfo(@RequestBody InfoVo infoVo) {
        long userAccount = infoVo.getUserAccount();
        return treasureService.getTreasureClassInfo(userAccount);
    }

    @PostMapping(value = "/insert/complete")
    public CommonResponse addCompleteLog(@RequestBody CompleteLog log){
        return treasureService.addCompleteLog(log);
    }

    @PostMapping(value = "/init")
    public CommonResponse init(@RequestBody String request) {
        JSONObject json = JSONObject.parseObject(request);
        treasureService.initField(json.getLong("userAccount"));
        return new SuccessResponse();
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
