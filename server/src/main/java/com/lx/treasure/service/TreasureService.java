package com.lx.treasure.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lx.treasure.bean.common.CommonException;
import com.lx.treasure.bean.common.CommonResponse;
import com.lx.treasure.bean.common.SuccessResponse;
import com.lx.treasure.bean.ioBean.*;
import com.lx.treasure.bean.repositoryBean.Channel;
import com.lx.treasure.bean.repositoryBean.Info;
import com.lx.treasure.bean.repositoryBean.Invest;
import com.lx.treasure.common.utils.IdUtils;
import com.lx.treasure.repository.ChannelRepository;
import com.lx.treasure.repository.ExpendRepository;
import com.lx.treasure.repository.InfoRepository;
import com.lx.treasure.repository.InvestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service("ChannelService")
@Slf4j
public class TreasureService {

    @Resource
    private ChannelRepository channelRepository;

    @Resource
    private InfoRepository infoRepository;

    @Resource
    private ExpendService expendService;

    @Resource
    private ExpendRepository expendRepository;

    @Resource
    private InvestRepository investRepository;

    @Autowired
    private IdUtils idUtils;

    @Autowired
    private SuccessResponse successResponse;

    // 处理前端发过来的一条数据

    /**
     * 获取指定范围内的资产数据
     * @param baseInvo baseInvo
     * @return 指定范围内的资产数据
     */
    public String getWealthCurve(BaseInvo baseInvo) {
        if (StringUtils.isEmpty(baseInvo.getUserAccount())) {
            return null;
        }
        List<Info> infoList = infoRepository.findInfoByUserAccount(baseInvo.getUserAccount(), baseInvo.getStartDate(), baseInvo.getEndDate());
//        double profit1,profit2;
//        List<Invest> investList = investRepository.findFundByUserAccount(baseInvo);
//        if(infoList.size() > 2) {
//            profit1 = investList.get(0).getGross() - investList.get(0).getInvest();
//            profit2 = investList.get(1).getGross() - investList.get(1).getInvest();
//        }
        JSONArray result = new JSONArray();
        for (Info info : infoList) {
            JSONObject item = new JSONObject();
            item.put("pay", info.getPay());
            item.put("expend", info.getExpenditure());
            item.put("date", info.getInsertTime());
            result.add(item);
        }
        return result.toJSONString();
    }

    /**
     * 1. 获取数据
     * 2. 解析数据
     * 3. 分别将数据放入pojo对象
     * 4. 使用repository存入数据
     * 5. return
     *
     * @param infoInvo 前端输入的对象
     * @return 通用成功/失败对象
     */
    public SuccessResponse getInfoHandler(InfoInvo infoInvo) throws CommonException {
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
        expendService.addExpend(stringToExpendVoList(info.getInfo()));
        insertChannels(channels, id, userAccount);
        SuccessResponse response = new SuccessResponse();
        log.info("返回结果：" + response.toString());
        return response;
    }

    /**
     * @param channelMap map对象
     * @param infoId     infoId
     * @Distribute: 将channelMap转为List插入数据库
     */
    private void insertChannels(Map<String, Map<String, Double>> channelMap, long infoId, long userAccount) {
        List<Channel> channels = channelMapToChannel(channelMap, infoId, userAccount);
        channelRepository.saveAll(channels);
    }

    /**
     * 遍历channelMap
     * 组装Channel对象
     */
    private List<Channel> channelMapToChannel(Map<String, Map<String, Double>> channelMap, long foreignKey, long userAccount) {
        LinkedList<Channel> channelList = new LinkedList<>();
        channelMap.forEach((channel1, channel1Map) -> channel1Map.forEach((channel2, value) -> {
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
     *
     * @return 成功
     */
    public ChannelsInfo getUserLatestChannels(long userAccount) throws CommonException {
        List<Channel> channelList = channelRepository.findLatestByUserAccount(userAccount);
        if (channelList == null) {
            throw new CommonException("400", "个人账号错误");
        }
        log.info(channelList.toString());
        return packageChannelsInfo(channelList);
    }

    /**
     * 遍历查出的渠道、
     * 为其创建树形结构
     *
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
                children.add(new ChannelsInfo(name, value, null));
                ChannelsInfo channelsInfo = new ChannelsInfo(parent, value, children);
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
        parentMap.forEach((key, value) -> root.getChildren().add(value));
        return root;
    }



    /**
     * 获取主要的支出信息
     *
     * @param userAccount 个人账号
     * @return 个人支出详细信息
     */
    public List<ExpendVo> getMainExpendInfo(long userAccount) throws CommonException {
        Info info = infoRepository.findUserLatest(userAccount);
        Date lastLogDate = info.getInsertTime();
        List<com.lx.treasure.bean.repositoryBean.Expend> expendList = expendRepository.findByUserAccount(userAccount,lastLogDate);
        List<ExpendVo> expendVos = stringToExpendVoList(info.getInfo());
        for (com.lx.treasure.bean.repositoryBean.Expend expend : expendList) {
            ExpendVo expendVo = new ExpendVo();
            BeanUtils.copyProperties(expend, expendVo);
            expendVos.add(expendVo);
        }
        return expendVos;
    }

    /**
     * 将String 解析为支出信息
     * @param mainExpendStr 包含支出信息的String
     * @return 支出信息
     * @throws CommonException 普通
     */
    private List<ExpendVo> stringToExpendVoList(String mainExpendStr) throws CommonException {
        if (StringUtils.isEmpty(mainExpendStr)) {
            return null;
        }
        List<ExpendVo> expendInfo = new LinkedList<>();
        try {
            String[] mainExpends = mainExpendStr.split("_,_");
            for (String mainExpend : mainExpends) {
                String[] kv = mainExpend.split(":");
                String key = kv[0];
                String value = kv[1];
                expendInfo.add(new ExpendVo(key, Double.parseDouble(value)));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CommonException("500", "服务器内部错误");
        }
        return expendInfo;
    }

    /**
     * 插入一条完整记录到数据库
     *
     * @param completeLog 记录对象
     * @return 成功标志
     */
    public CommonResponse addCompleteLog(CompleteLog completeLog) {
        log.info("ChannelService 收到:" + completeLog.toString());
        Info info = buildInfo(completeLog);
        infoRepository.save(info);
        List<Channel> channels = channelsInVoToChannelList(completeLog.getChannel(), completeLog.getUserAccount(), info.getId());
        channelRepository.saveAll(channels);
        List<ExpendVo> expendVos = completeLog.getExpendList();
        for (ExpendVo expendVo : expendVos) {
            expendVo.setUserAccount(completeLog.getUserAccount());
        }
        expendService.addExpend(completeLog.getExpendList());
        SuccessResponse response = new SuccessResponse();
        log.info("返回结果：" + response.toString());
        return response;
    }

    /**
     * 装载info对象
     *
     * @param completeLog 前端输入的请求对象
     * @return 数据库info bean
     */
    private Info buildInfo(CompleteLog completeLog) {
        Info info = new Info();
        BeanUtils.copyProperties(completeLog, info);
        long id = idUtils.generateId();
        info.setId(id);
        info.setInsertTime(new Date());
        info.setUpdateTime(new Date());
        info.setUserAccount(completeLog.getUserAccount());
        info.setExpenditure(getExpend(completeLog.getUserAccount()));
        return info;
    }

    /**
     * 获取用户上次记录到本次记录中途花费
     */
    private double getExpend(long user) {
        double value = 0;
        List<Channel> channelList = channelRepository.findLatestByUserAccount(user);
        for (Channel channel : channelList) {
            value += channel.getMoney();
        }
        return value;
    }

    /**
     * 将channelInVos转为数据库对应的channel对象
     *
     * @param channelInVos 前端输入的对象
     * @param userAccount  用户账号
     * @param infoId       infoId
     * @return channel对象的列表
     */
    private List<Channel> channelsInVoToChannelList(List<ChannelInVo> channelInVos, long userAccount, long infoId) {
        List<Channel> channels = new LinkedList<>();
        for (ChannelInVo channelInVo : channelInVos) {
            channels.add(new Channel(infoId, userAccount, channelInVo.getChannel1(), channelInVo.getChannel2(), channelInVo.getValue(), new Date()));
        }
        return channels;
    }

    /*---------------测试使用--------------------------*/
    public Info findInfoById(long id) {
        return infoRepository.findById(id);
    }

    public Channel findUserById(long id) {
        return channelRepository.findById(id);
    }
}
