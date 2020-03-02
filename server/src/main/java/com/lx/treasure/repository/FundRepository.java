package com.lx.treasure.repository;

import com.lx.treasure.bean.ioBean.G002Invo;
import com.lx.treasure.bean.repositoryBean.Channel;
import com.lx.treasure.bean.repositoryBean.Fund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: Lx
 * @Date: 2020/2/27 22:31
 * @Description: 获取基金相关
 */
@Repository
public interface FundRepository extends JpaRepository<Fund,Long> {

    /**
     * 获取基金信息
     */
    @Modifying
    @Transactional
    @Query(value = "SELECT * FROM fund_info f " +
            "where f.user_account = :#{#invo.userAccount} " +
            "and f.insert_time < :#{#invo.endDate} " +
            "and f.insert_time > :#{#invo.startDate} ",nativeQuery = true)
    List<Fund> findFundByUserAccount(@Param("invo") G002Invo invo);
}
