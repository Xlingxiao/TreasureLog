package com.lx.treasure.module.treasure.service;

import com.lx.treasure.bean.common.CommonResponse;
import com.lx.treasure.bean.common.SuccessResponse;
import com.lx.treasure.bean.ioBean.G002Invo;
import com.lx.treasure.bean.ioBean.InvestInfo;
import com.lx.treasure.module.treasure.mapper.Invest;
import com.lx.treasure.common.utils.DateUtils;
import com.lx.treasure.common.utils.IdUtils;
import com.lx.treasure.module.treasure.repository.ChannelRepository;
import com.lx.treasure.module.treasure.repository.InfoRepository;
import com.lx.treasure.module.treasure.repository.InvestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @Auther: Lx
 * @Date: 2020/3/6 19:47
 * @Description: 投资相关的Service
 */
@Service("InvestService")
@Slf4j
public class InvestService {

    @Resource
    private ChannelRepository channelRepository;

    @Resource
    private InfoRepository infoRepository;

    @Resource
    private InvestRepository investRepository;

    @Autowired
    private IdUtils idUtils;

    @Autowired
    private SuccessResponse successResponse;

    /**
     * 插入一条投资信息
     *
     * @param invest 投资信息
     * @return 成功标志
     */
    public CommonResponse logOnceInvest(List<Invest> investList) {
        loadInvest(investList);
        investRepository.saveAll(investList);
        return successResponse;
    }

    /**
     * 加载投资数据的时间
     *
     * @param invests 需要存入的对象
     */
    private void loadInvest(List<Invest> invests) {
        Date date = new Date();
        for (Invest invest : invests) {
            invest.setId(idUtils.generateId());
            if (invest.getInsertTime() == null) {
                invest.setInsertTime(date);
            }
        }
    }


    /**
     * 根据userAccount和时间范围获取投资信息
     *
     * @param invo 个人账号和请求时间
     * @return 基金信息
     */
    public InvestInfo getFundInfo(G002Invo invo) {
        List<Invest> fundList = investRepository.findFundByUserAccount(invo);
//        fundList.sort(Comparator.comparing(Invest::getInsert_time));
        List<Double> grossList = new LinkedList<>();
        List<Double> investList = new LinkedList<>();
        List<Double> gainList = new LinkedList<>();
        List<String> insertTimeList = new LinkedList<>();
        fundList.forEach(fund -> {
            double gross = fund.getGross();
            int i = investList.size() - 1;
            double invest = i >= 0 ? fund.getInvest() + investList.get(i) : fund.getInvest();
            invest = invest > 0 ? invest : 0;
            double gain = i >= 0 ?  gross - (grossList.get(i) + fund.getInvest()) : gross - invest;
            grossList.add(gross);
            investList.add(invest);
            gainList.add(gain);
            insertTimeList.add(DateUtils.dateToString(fund.getInsertTime()));
        });
        return new InvestInfo(invo.getUserAccount(), grossList, investList, gainList, insertTimeList);
    }


}
