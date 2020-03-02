package com.lx.treasure.service;

import com.lx.treasure.bean.common.CommonException;
import com.lx.treasure.bean.common.SuccessResponse;
import com.lx.treasure.bean.ioBean.*;
import com.lx.treasure.bean.repositoryBean.Channel;
import com.lx.treasure.bean.repositoryBean.Fund;
import com.lx.treasure.bean.repositoryBean.Info;
import com.lx.treasure.common.utils.DateUtils;
import com.lx.treasure.common.utils.IdUtils;
import com.lx.treasure.repository.ChannelRepository;
import com.lx.treasure.repository.FundRepository;
import com.lx.treasure.repository.InfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service("ChannelService")
@Slf4j
public class ChannelService {

    @Resource
    private ChannelRepository channelRepository;

    @Resource
    private InfoRepository infoRepository;

    @Resource
    private FundRepository fundRepository;

    @Autowired
    private IdUtils idUtils;

    @Autowired
    private SuccessResponse successResponse;

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
        info.setInsertTime(new Date());
        info.setUpdateTime(new Date());
        long userAccount = StringUtils.isEmpty(infoInvo.getUserAccount()) ? 0 : infoInvo.getUserAccount();
        info.setUserAccount(userAccount);
        infoRepository.save(info);
        insertChannels(channels, id,userAccount);
        SuccessResponse response = new SuccessResponse();
        log.info("返回结果：" + response.toString());
        return response;
    }

    /**
     * @Distribute: 将channelMap转为List插入数据库
     * @param channelMap map对象
     * @param infoId infoId
     */
    private void insertChannels(Map<String, Map<String, Double>> channelMap, long infoId,long userAccount) {
        List<Channel> channels = channelMapToChannel(channelMap, infoId,userAccount);
        channelRepository.saveAll(channels);
    }

    /**
     * 遍历channelMap
     * 组装Channel对象
     */
    private List<Channel> channelMapToChannel(Map<String, Map<String, Double>> channelMap, long foreignKey,long userAccount) {
        LinkedList<Channel> channelList = new LinkedList<>();
        channelMap.forEach((channel1,channel1Map) -> channel1Map.forEach((channel2, value) -> {
            Channel channel = new Channel();
            channel.setUserAccount(userAccount);
            channel.setInfoId(foreignKey);
            channel.setChannel1(channel1);
            channel.setChannel2(channel2);
            channel.setMoney(value);
            channelList.add(channel);
        }));
        return channelList;
    }

    /**
     * 获取用户最新的渠道信息
     * @return 成功
     */
    public ChannelsInfo getUserLatestChannels(long userAccount) throws CommonException {
        List<Channel> channelList = channelRepository.findLatestByUserAccount(userAccount);
        if (channelList == null) {
            throw new CommonException("400","个人账号错误");
        }
        log.info(channelList.toString());
        return packageChannelsInfo(channelList);
    }

    /**
     * 遍历查出的渠道、
     * 为其创建树形结构
     * @param channelList 查出的渠道
     * @return 组装为json String 的渠道结构
     */
    private ChannelsInfo packageChannelsInfo(List<Channel> channelList) {
        Map<String, ChannelsInfo> parentMap = new HashMap<>();
        Map<String, List<ChannelsInfo>> childrenMap = new HashMap<>();
        for (Channel channel : channelList) {
            String parent = channel.getChannel1();
            String name = channel.getChannel2();
            double value = channel.getMoney();
            ChannelsInfo parentChannels = parentMap.get(parent);
            if (parentChannels == null) {
                List<ChannelsInfo> children = new LinkedList<>();
                children.add(new ChannelsInfo(name, value,null));
                ChannelsInfo channelsInfo = new ChannelsInfo(parent,value,children);
                childrenMap.put(parent, children);
                parentMap.put(parent, channelsInfo);
            } else {
                double values = parentChannels.getValue() + value;
                parentChannels.setValue(values);
                ChannelsInfo item = new ChannelsInfo(name, value, null);
                childrenMap.get(parent).add(item);
            }
        }
        ChannelsInfo root = new ChannelsInfo("root", 0, new LinkedList<>());
        parentMap.forEach((key,value) -> root.getChildren().add(value));
        return root;
    }

    /**
     * 根据userAccount和时间范围获取基金信息
     *
     * @param invo 个人账号和请求时间
     * @return 基金信息
     */
    public InvestInfo getFundInfo(G002Invo invo) {
        if (invo.getEndDate() == null) invo.setEndDate(new Date());
        if(invo.getStartDate() == null) invo.setStartDate(new Date(0));
        List<Fund> fundList = fundRepository.findFundByUserAccount(invo);
        List<Double> grossList = new LinkedList<>();
        List<Double> investList = new LinkedList<>();
        List<Double> gainList = new LinkedList<>();
        List<String> insertTimeList = new LinkedList<>();
        fundList.forEach(fund -> {
            double gross = fund.getGross();
            double invest = fund.getInvest();
            double gain = gross - invest;
            grossList.add(gross);
            investList.add(invest);
            gainList.add(gain);
            insertTimeList.add(DateUtils.dateToString(fund.getInsert_time()));
        });
        return new InvestInfo(invo.getUserAccount(), grossList, investList, gainList, insertTimeList);
    }

    /**
     * 获取主要的支出信息
     * @param userAccount 个人账号
     * @return 个人支出详细信息
     */
    public List<KVBean> getMainExpendInfo(long userAccount) throws CommonException {
        List<KVBean> expendInfo = new LinkedList<>();
        Info info = infoRepository.findUserLatest(userAccount);
        String mainExpendStr = info.getInfo();
        try {
            String[] mainExpends = mainExpendStr.split("\\+");
            for (String mainExpend : mainExpends) {
                String[] kv = mainExpend.split(" ");
                String key = kv[0];
                String value = kv[1];
                expendInfo.add(new KVBean(key, value));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CommonException("500", "服务器内部错误");
        }
        return expendInfo;
    }

    /*---------------测试使用--------------------------*/
    public Info findInfoById(long id) {
        return infoRepository.findById(id);
    }

    public Channel findUserById(long id) {
        return channelRepository.findById(id);
    }
}
