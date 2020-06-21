package com.lx.treasure.repository;

import com.lx.treasure.bean.ioBean.BaseInvo;
import com.lx.treasure.bean.ioBean.G002Invo;
import com.lx.treasure.bean.repositoryBean.Invest;
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
public interface InvestRepository extends JpaRepository<Invest,Long> {

    /**
     * 获取基金信息
     */
   /* @Modifying
    @Transactional
    @Query(value = "SELECT * FROM invest f " +
            "where f.user_account = :#{#invo.userAccount} " +
            "and f.insert_time < :#{#invo.endDate} " +
            "and f.insert_time > :#{#invo.startDate} ",nativeQuery = true)
    List<Invest> findFundByUserAccount(@Param("invo") G002Invo invo);*/

    /**
     * 获取基金信息
     *  账号相同
     *  有选择渠道就要查渠道
     *  有开始时间就查开始时间
     *  有结束时间就查结束时间
     *  根据插入时间进行升序排列
     *  限制查询条数
     */
    @Modifying
    @Transactional
    @Query(value = "SELECT * FROM invest table1 " +
            "where table1.user_account = :#{#invo.userAccount} " +
            "and if(IFNULL(:#{#invo.channel},'') != '', table1.channel = :#{#invo.channel} , 1=1) " +
            "and if(IFNULL(:#{#invo.startDate},'') != '', table1.insert_time > :#{#invo.startDate} , 1=1) " +
            "and if(IFNULL(:#{#invo.endDate},'') !='', table1.insert_time < :#{#invo.endDate} ,1=1)" +
            "ORDER BY table1.insert_time " +
            "LIMIT :#{#invo.findLimit}", nativeQuery = true)
    List<Invest> findFundByUserAccount(@Param("invo") G002Invo invo);
}
