package com.lx.treasure.controller;


import com.lx.treasure.bean.common.CommonException;
import com.lx.treasure.bean.common.CommonResponse;
import com.lx.treasure.bean.ioBean.InfoInvo;
import com.lx.treasure.bean.repositoryBean.Channel;
import com.lx.treasure.bean.repositoryBean.Info;
import com.lx.treasure.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    public CommonResponse getInfo(@RequestBody InfoInvo infoInvo) {
        if (StringUtils.isEmpty(infoInvo.getPay())) {
            return new CommonException("2", "pay不能为空");
        }
        if (StringUtils.isEmpty(infoInvo.getExpenditure())) {
            return new CommonException("2", "expenditure不能为空");
        }
        if (StringUtils.isEmpty(infoInvo.getInfo())) {
            return new CommonException("2", "info不能为空");
        }
        if (StringUtils.isEmpty(infoInvo.getChannels())) {
            return new CommonException("2", "channels不能为空");
        }
        return channelService.getInfoHandler(infoInvo);
    }

    @GetMapping(value = "/test/getChannel")
    public Channel findChannelByID(@RequestParam(name = "name", defaultValue = "1") long id) {
        return channelService.findUserById(id);
    }

    @GetMapping(value = "/test/getInfo")
    public Info findInfoByID(@RequestParam(name = "name", defaultValue = "1") long id) {
        return channelService.findInfoById(id);
    }
}
