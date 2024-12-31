package com.lx.treasure.module.treasure.service.detail;

import com.lx.treasure.module.treasure.mapper.Channel;
import com.lx.treasure.module.treasure.mapper.Expend;
import com.lx.treasure.module.treasure.mapper.Info;
import com.lx.treasure.module.treasure.mapper.Invest;
import com.lx.treasure.module.treasure.repository.ChannelRepository;
import com.lx.treasure.module.treasure.repository.ExpendRepository;
import com.lx.treasure.module.treasure.repository.InfoRepository;
import com.lx.treasure.module.treasure.repository.InvestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 操作相关业务表数据
 */
@Slf4j
@Service
public class DetailServiceImpl {

    @Autowired
    ChannelRepository channelRepository;
    @Autowired
    ExpendRepository expendRepository;
    @Autowired
    InfoRepository infoRepository;
    @Autowired
    InvestRepository investRepository;

    public void channelInsert(Channel channel) {
        channelRepository.save(channel);
    }

    public void channelUpdate(Channel channel) {
        channelRepository.delete(channel);
        channelRepository.save(channel);
    }

    public void channelDelete(long id) {
        channelRepository.deleteById(id);
    }

    public List<Channel> channelQuery(long userId) {
        return channelRepository.queryChannels(userId);
    }


    public void infoInsert(Info info) {
        infoRepository.save(info);
    }

    public void infoUpdate(Info info) {
        infoRepository.delete(info);
        infoRepository.save(info);
    }

    public void infoDelete(long id) {
        infoRepository.deleteById(id);
    }

    public List<Info> infoQuery(long userId) {
        return infoRepository.queryInfos(userId);
    }



    public void expendInsert(Expend expend) {
        expendRepository.save(expend);
    }

    public void expendUpdate(Expend expend) {
        expendRepository.delete(expend);
        expendRepository.save(expend);
    }

    public void expendDelete(long id) {
        expendRepository.deleteById(id);
    }

    public List<Expend> expendQuery(long userId) {
        return expendRepository.queryExpends(userId);
    }


    public void investInsert(Invest invest) {
        investRepository.save(invest);
    }

    public void investUpdate(Invest invest) {
        investRepository.delete(invest);
        investRepository.save(invest);
    }

    public void investDelete(long id) {
        investRepository.deleteById(id);
    }

    public List<Invest> investQuery(long userId) {
        return investRepository.queryInvests(userId);
    }
}
