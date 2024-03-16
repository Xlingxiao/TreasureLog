package com.lx.treasure.module.treasure.controller;

import com.alibaba.fastjson.JSONObject;
import com.lx.treasure.bean.common.CommonResponse;
import com.lx.treasure.bean.common.SuccessResponse;
import com.lx.treasure.bean.ioBean.BaseInvo;
import com.lx.treasure.module.treasure.mapper.Channel;
import com.lx.treasure.module.treasure.mapper.Expend;
import com.lx.treasure.module.treasure.mapper.Info;
import com.lx.treasure.module.treasure.mapper.Invest;
import com.lx.treasure.module.treasure.service.detail.DetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用作手动编辑原始数据
 */
@RestController
@RequestMapping("/detail_opt")
public class DetailOptController {

    @Autowired
    DetailServiceImpl detailService;

    /* ----------- Channel ---------------*/
    @PostMapping(value = "/channel/query")
    public String channelQuery(@RequestBody BaseInvo invo) {
        List<Channel> channels = detailService.channelQuery(invo.getUserAccount());
        return JSONObject.toJSONString(channels);
    }

    @PostMapping(value = "/channel/update")
    public CommonResponse channelUpdate(@RequestBody Channel channel) {
        detailService.channelUpdate(channel);
        return new SuccessResponse();
    }

    @PostMapping(value = "/channel/insert")
    public CommonResponse channelInsert(@RequestBody Channel channel) {
        detailService.channelInsert(channel);
        return new SuccessResponse();
    }

    @PostMapping(value = "/channel/delete")
    public CommonResponse channelDelete(@RequestBody Channel invo) {
        detailService.channelDelete(invo.getId());
        return new SuccessResponse();
    }

    /* ----------- Info ---------------*/

    @PostMapping(value = "/info/query")
    public String infoQuery(@RequestBody BaseInvo invo) {
        List<Info> result = detailService.infoQuery(invo.getUserAccount());
        return JSONObject.toJSONString(result);
    }

    @PostMapping(value = "/info/update")
    public CommonResponse infoUpdate(@RequestBody Info info) {
        detailService.infoUpdate(info);
        return new SuccessResponse();
    }

    @PostMapping(value = "/info/insert")
    public CommonResponse infoInsert(@RequestBody Info info) {
        detailService.infoInsert(info);
        return new SuccessResponse();
    }

    @PostMapping(value = "/info/delete")
    public CommonResponse infoDelete(@RequestBody Info invo) {
        detailService.infoDelete(invo.getId());
        return new SuccessResponse();
    }

    /* ----------- Expend ---------------*/

    @PostMapping(value = "/expend/query")
    public String expendQuery(@RequestBody BaseInvo invo) {
        List<Expend> result = detailService.expendQuery(invo.getUserAccount());
        return JSONObject.toJSONString(result);
    }

    @PostMapping(value = "/expend/update")
    public CommonResponse expendUpdate(@RequestBody Expend expend) {
        detailService.expendUpdate(expend);
        return new SuccessResponse();
    }

    @PostMapping(value = "/expend/insert")
    public CommonResponse expendInsert(@RequestBody Expend expend) {
        detailService.expendInsert(expend);
        return new SuccessResponse();
    }

    @PostMapping(value = "/expend/delete")
    public CommonResponse expendDelete(@RequestBody Expend invo) {
        detailService.expendDelete(invo.getId());
        return new SuccessResponse();
    }


    /* ----------- Invest ---------------*/

    @PostMapping(value = "/invest/query")
    public String investQuery(@RequestBody BaseInvo invo) {
        List<Invest> invests = detailService.investQuery(invo.getUserAccount());
        return JSONObject.toJSONString(invests);
    }

    @PostMapping(value = "/invest/update")
    public CommonResponse investUpdate(@RequestBody Invest invest) {
        detailService.investUpdate(invest);
        return new SuccessResponse();
    }

    @PostMapping(value = "/invest/insert")
    public CommonResponse investInsert(@RequestBody Invest invest) {
        detailService.investInsert(invest);
        return new SuccessResponse();
    }

    @PostMapping(value = "/invest/delete")
    public CommonResponse investDelete(@RequestBody Invest invo) {
        detailService.investDelete(invo.getId());
        return new SuccessResponse();
    }

}
