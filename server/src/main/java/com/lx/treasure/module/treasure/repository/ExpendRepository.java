package com.lx.treasure.module.treasure.repository;

import com.lx.treasure.module.treasure.mapper.Expend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @Auther: Lx
 * @Date: 2020/3/9 20:23
 * @Description: 用于操作数据库表Expend
 */
public interface ExpendRepository extends JpaRepository<Expend, Long> {


    @Query(value = "select * from expend a where user_account = ?1 ORDER BY a.insert_time",nativeQuery = true)
    List<Expend> findByUserAccount(long userAccount);

    @Query(value = "select * from expend a where user_account = ?1 and a.insert_time > ?2 ORDER BY a.insert_time",nativeQuery = true)
    List<Expend> findByUserAccount(long userAccount, Date date);

    @Query(value = "select * from expend a " +
            "where user_account = ?1 " +
            "and if(IFNULL(?2,'') != '', a.insert_time > ?2, 1=1) " +
            "and if(IFNULL(?3,'') != '', a.insert_time < ?3, 1=1) " +
            "ORDER BY a.insert_time", nativeQuery = true)
    List<Expend> findByUserAccount(long userAccount, Date startDate, Date endDate);
}
