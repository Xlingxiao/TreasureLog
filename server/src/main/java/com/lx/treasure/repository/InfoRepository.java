package com.lx.treasure.repository;

import com.lx.treasure.bean.repositoryBean.Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 相关消费信息持久化
 */
@Repository
public interface InfoRepository extends JpaRepository<Info, Long> {
//    // 插入一条数据
//    int insertInfo(Info info);

    // 测试使用 通过id获取数据
    Info findById(long id);
}
