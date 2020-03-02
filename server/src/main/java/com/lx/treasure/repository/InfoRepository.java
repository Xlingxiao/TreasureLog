package com.lx.treasure.repository;

import com.lx.treasure.bean.repositoryBean.Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 相关消费信息持久化
 */
@Repository
public interface InfoRepository extends JpaRepository<Info, Long> {

    // 获取用户最新插入的一条数据
    @Query(value = "SELECT * FROM info a where a.user_account = ?1 ORDER BY a.insert_time DESC LIMIT 1",nativeQuery = true)
    Info findUserLatest(long userAccount);

    // 测试使用 通过id获取数据
    Info findById(long id);
}
