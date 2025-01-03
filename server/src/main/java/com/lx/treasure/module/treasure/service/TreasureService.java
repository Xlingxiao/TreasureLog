package com.lx.treasure.module.treasure.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lx.treasure.bean.common.CommonException;
import com.lx.treasure.bean.common.CommonResponse;
import com.lx.treasure.bean.common.ContentText;
import com.lx.treasure.bean.common.SuccessResponse;
import com.lx.treasure.bean.ioBean.*;
import com.lx.treasure.common.utils.DateUtils;
import com.lx.treasure.mapper.TreasureClassMapper;
import com.lx.treasure.module.treasure.constant.TreasureConstant;
import com.lx.treasure.module.treasure.mapper.Channel;
import com.lx.treasure.module.treasure.mapper.Info;
import com.lx.treasure.common.utils.IdUtils;
import com.lx.treasure.module.treasure.mapper.Expend;
import com.lx.treasure.module.treasure.mapper.TreasureClassInfo;
import com.lx.treasure.module.treasure.repository.ChannelRepository;
import com.lx.treasure.module.treasure.repository.ExpendRepository;
import com.lx.treasure.module.treasure.repository.InfoRepository;
import com.lx.treasure.module.treasure.repository.InvestRepository;
import com.lx.treasure.module.treasure.vo.TreasureClassInfoVo;
import com.lx.treasure.module.treasure.vo.TreasureStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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
    private TreasureClassMapper treasureClassMapper;

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
        return JSONArray.toJSONString(infoList);
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
            return null;
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
     * 获取用户的收支情况
     * @param userAccount 用户信息
     * @return 用户收支情况
     */
    public List<Info> getUserIncomeExpend(long userAccount) {
        return infoRepository.findInfoByUserAccount(userAccount, null, null);
    }


    /**
     * 获取用户最新的渠道信息
     *
     * @return 成功
     */
    public TreasureStatus getUserChannels(long userAccount, long infoId) {
        Info info = infoRepository.findById(infoId);
        List<Channel> channelList = channelRepository.queryChannels(userAccount, infoId);
        if (channelList == null || channelList.isEmpty()) {
            return null;
        }
        log.info(channelList.toString());
        ChannelsInfo channelsInfo = packageChannelsInfo(channelList);
        TreasureStatus treasureStatus = new TreasureStatus();
        treasureStatus.setPay(info.getPay());
        treasureStatus.setPassiveIncome(info.getPassiveIncome());
        treasureStatus.setExpenditure(info.getExpenditure());
        treasureStatus.setDate(info.getInsertTime());
        treasureStatus.setChannelStatus(channelsInfo);
        return treasureStatus;
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
        List<Expend> expendList = expendRepository.findByUserAccount(userAccount,lastLogDate);
        List<ExpendVo> expendVos = stringToExpendVoList(info.getInfo());
        for (Expend expend : expendList) {
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
            throw new CommonException(ContentText.SERVER_ERROR_CODE, ContentText.SERVER_ERROR);
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
        if (!CollectionUtils.isEmpty(completeLog.getChannel())) {
            List<Channel> channels = channelsInVoToChannelList(completeLog.getChannel(), completeLog.getUserAccount(), info.getId());
            channelRepository.saveAll(channels);
        }
        List<ExpendVo> expendVos = completeLog.getExpendList();
        if (!CollectionUtils.isEmpty(expendVos)) {
            for (ExpendVo expendVo : expendVos) {
                expendVo.setUserAccount(completeLog.getUserAccount());
            }
            expendService.addExpend(completeLog.getExpendList());
        }
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

        // 总资产
        int totalAsset = 0;
        // 投资总值
        int totalInvest = 0;
        List<ChannelInVo> channelList = completeLog.getChannel();
        for (ChannelInVo channel : channelList) {
            totalAsset += channel.getValue();
            // 是否投资渠道
            if (isInvestChannel(channel.getChannel1()) || isInvestChannel(channel.getChannel2())) {
                totalInvest += channel.getValue();
            }
        }
        // 获取上月info信息
        Info userLatest = infoRepository.findUserLatest(info.getUserAccount());
        // 资产变更总量 = 本月资产总数 - 上月总数
        double assetChange = totalAsset - userLatest.getTotalAsset();
        // 被动收入 = 本月投资资产 - 上月投资资产 - 本月新增投入
        double passiveIncome = totalInvest - userLatest.getTotalInvest() - completeLog.getInvestChangeAsset();
        // 消费 = 当前总资产 - （上月总资产 + 收入 + 被动收入）
        double expend = totalAsset - (userLatest.getTotalAsset() + info.getPay() + passiveIncome);

        info.setTotalAsset(totalAsset);
        info.setTotalInvest(totalInvest);
        info.setAssetChange(assetChange);
        info.setPassiveIncome(passiveIncome);
        info.setExpenditure(expend);

        long id = idUtils.generateId();
        info.setId(id);
        Date now = new Date();
        info.setDate(DateUtils.getSdfDate(now));
        info.setInsertTime(now);
        info.setUpdateTime(now);
        info.setUserAccount(completeLog.getUserAccount());
        return info;
    }


    /**
     * 判断渠道是否为理财渠道
     * @param channel 渠道
     * @return 是否
     */
    private boolean isInvestChannel(String channel){
        List<String> investChannel = TreasureConstant.INVEST_CHANNEL_LIST;
        for (String c : investChannel) {
            if (channel.contains(c)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取汇总的状态信息
     * 垃圾代码，无法将父类强转子类
     * @param userAccount 用户信息
     * @return 汇总的资产信息
     */
    public List<TreasureClassInfoVo> getTreasureClassInfo(long userAccount) {
        List<TreasureClassInfo> treasureClassInfo = treasureClassMapper.getTreasureClassInfo(userAccount);
        ArrayList<TreasureClassInfoVo> result = new ArrayList<>(treasureClassInfo.size());
        for (TreasureClassInfo classInfo : treasureClassInfo) {
            TreasureClassInfoVo item = new TreasureClassInfoVo();
            BeanUtils.copyProperties(classInfo, item);
            result.add(item);
        }
        return result;
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

    /**
     * 初始化部分新增字段
     * 获取所有channel信息
     */
    public void initField(long userAccount) {
        final List<Channel> channels = channelRepository.queryChannels(userAccount);
        final Map<Long, List<Channel>> collect = channels.stream().collect(Collectors.groupingBy(Channel::getInfoId));
        for (Map.Entry<Long, List<Channel>> entry : collect.entrySet()) {
            final long infoId = entry.getKey();
            final List<Channel> channelList = entry.getValue();
            Info info = new Info();
            info.setId(infoId);

            // 总资产
            int totalAsset = 0;
            // 投资总值
            int totalInvest = 0;
            for (Channel channel : channelList) {
                totalAsset += channel.getMoney();
                // 是否投资渠道
                if (isInvestChannel(channel.getChannel1()) || isInvestChannel(channel.getChannel2())) {
                    totalInvest += channel.getMoney();
                }
            }
            info.setTotalAsset(totalAsset);
            info.setTotalInvest(totalInvest);

            // 获取上月info信息
            Info currentInfo = infoRepository.findById(infoId);
            if (currentInfo == null) {
                continue;
            }
            Info userLatest = infoRepository.findUserLatestByDate(userAccount, currentInfo.getDate());
            if (userLatest != null) {
                // 资产变更总量 = 本月资产总数 - 上月总数
                double assetChange = totalAsset - userLatest.getTotalAsset();
                // 被动收入 = 本月投资资产 - 上月投资资产 - 本月新增投入
                double passiveIncome = 0;
                // 消费 = 资产变更总量 - 被动收入
                double expend = assetChange - passiveIncome;
                info.setAssetChange(assetChange);
                info.setPassiveIncome(passiveIncome);
                info.setExpenditure(expend);
            }
            treasureClassMapper.updateInfo(info);

        }
    }
}
