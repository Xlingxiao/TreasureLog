package com.lx.treasure.repository;

import com.lx.treasure.bean.repositoryBean.Expend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Auther: Lx
 * @Date: 2020/3/9 20:23
 * @Description: 用于操作数据库表Expend
 */
public interface ExpendRepository extends JpaRepository<Expend, Long> {


    @Query(value = "select * from expend a where user_account = ?1 ORDER BY a.insert_time",nativeQuery = true)
    List<Expend> findByUserAccount(long userAccount);

}
