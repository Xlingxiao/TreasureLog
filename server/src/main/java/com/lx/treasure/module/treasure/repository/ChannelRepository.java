package com.lx.treasure.module.treasure.repository;

import com.lx.treasure.module.treasure.mapper.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用于渠道持久化
 */
@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {



    /**
     * 获取用户最新插入的渠道信息
     * 1. info表用userAccount为输入的userAccount，用insert_time排序取出第一个就是用户最新插入的数据id
     * 2. 根据id获取渠道数据
     * @param userAccount 用户个人账号
     * @return 用户最新插入的渠道信息
     */
    @Query(value = "select * from channel a where a.info_id IN(\n" +
            "\tSELECT b.id from (\n" +
            "\t\tSELECT c.id FROM info c where c.user_account = ?1 ORDER BY c.insert_time DESC LIMIT 1\n" +
            "\t) b\n" +
            ")",nativeQuery = true)
    List<Channel> findLatestByUserAccount(long userAccount);

    // 测试用，通过id找数据
    Channel findById(long id);

}
