package com.lx.treasure.module.treasure.repository;

import com.lx.treasure.module.treasure.mapper.Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 相关消费信息持久化
 */
@Repository
public interface InfoRepository extends JpaRepository<Info, Long> {

    // 根据userAccount、时间获取数据
    @Query(value = "SELECT * from info a " +
            "where a.user_account = ?1 " +
            "and if(IFNULL(?2,'') != '', a.insert_time > ?2 , 1=1) " +
            "and if(IFNULL(?3,'') !='', a.insert_time < ?3 ,1=1)" +
            "ORDER BY a.insert_time DESC",nativeQuery = true)
    List<Info> findInfoByUserAccount(long userAccount, Date startDate, Date endDate);

    // 获取用户最新插入的一条数据
    @Query(value = "SELECT * FROM info a where a.user_account = ?1 ORDER BY a.insert_time DESC LIMIT 1",nativeQuery = true)
    Info findUserLatest(long userAccount);

    // 获取用户最新插入的n条数据
    @Query(value = "SELECT * FROM info a where a.user_account = ?1 ORDER BY a.insert_time DESC LIMIT ?2",nativeQuery = true)
    List<Info> findUserLatest(long userAccount, int limit);

    // 测试使用 通过id获取数据
    Info findById(long id);
}
