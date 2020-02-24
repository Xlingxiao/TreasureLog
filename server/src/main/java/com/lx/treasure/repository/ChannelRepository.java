package com.lx.treasure.repository;

import com.lx.treasure.bean.repositoryBean.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用于渠道持久化
 */
@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {
//    // 插入一条channel
//    int insertOneChannel(Channel channel);
//
//    // 插入多条channel
//    int insertChannels(List<Channel> channels);

    // 测试用，通过id找数据
    Channel findById(long id);

}
