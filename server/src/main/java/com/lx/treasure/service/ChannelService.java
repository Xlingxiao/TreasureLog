package com.lx.treasure.service;

import com.lx.treasure.bean.common.SuccessResponse;
import com.lx.treasure.bean.ioBean.InfoInvo;
import com.lx.treasure.bean.repositoryBean.Channel;
import com.lx.treasure.bean.repositoryBean.Info;
import com.lx.treasure.common.utils.IdUtils;
import com.lx.treasure.repository.ChannelRepository;
import com.lx.treasure.repository.InfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@Service("ChannelService")
@Slf4j
public class ChannelService {

    @Resource
    private ChannelRepository channelRepository;

    @Resource
    private InfoRepository infoRepository;

    @Autowired
    private IdUtils idUtils;

    // 处理前端发过来的一条数据

    /**
     * 1. 获取数据
     * 2. 解析数据
     * 3. 分别将数据放入pojo对象
     * 4. 使用repository存入数据
     * 5. return
     * @param infoInvo 前端输入的对象
     * @return 通用成功/失败对象
     */
    public SuccessResponse getInfoHandler(InfoInvo infoInvo) {
        log.info("ChannelService 收到:" + infoInvo.toString());
        Map<String, Map<String, Double>> channels = infoInvo.getChannels();
        Info info = new Info();
        BeanUtils.copyProperties(infoInvo, info);
        long id = idUtils.generateId();
        info.setId(id);
        info.setInsert_time(new Date());
        info.setUpdate_time(new Date());
        infoRepository.save(info);
        insertChannels(channels, id);
        SuccessResponse response = new SuccessResponse();
        log.info("返回结果：" + response.toString());
        return response;
    }

    /**
     * @Distribute: 将channelMap转为List插入数据库
     * @param channelMap map对象
     * @param infoId infoId
     */
    private void insertChannels(Map<String, Map<String, Double>> channelMap, long infoId) {
        List<Channel> channels = channelMapToChannel(channelMap, infoId);
        channelRepository.saveAll(channels);
    }

    /**
     * 遍历channelMap
     * 组装Channel对象
     */
    private List<Channel> channelMapToChannel(Map<String, Map<String, Double>> channelMap, long foreignKey) {
        LinkedList<Channel> channelList = new LinkedList<>();
        channelMap.forEach((channel1,channel1Map) -> {
            channel1Map.forEach((channel2, value) -> {
                Channel channel = new Channel();
                channel.setInfoId(foreignKey);
                channel.setChannel1(channel1);
                channel.setChannel2(channel2);
                channel.setMoney(value);
                channelList.add(channel);
            });
        });
        return channelList;
    }

    /*---------------测试使用--------------------------*/
    public Info findInfoById(long id) {
        return infoRepository.findById(id);
    }

    public Channel findUserById(long id) {
        return channelRepository.findById(id);
    }
}
